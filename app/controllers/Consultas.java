package controllers;

import java.util.List;

import models.Consulta;
import models.Medico;
import models.Paciente;
import models.Status;
import play.mvc.Controller;

public class Consultas extends Controller {

	public static void form() {
		Consulta c = new Consulta();
		List<Paciente> pacientes = Paciente.find("status != ?1", Status.INATIVO).fetch();
		List<Medico> medicos = Medico.find("status != ?1", Status.INATIVO).fetch();
		render(c, pacientes, medicos);
	}

	public static void editar(Long id) {
		Consulta c = Consulta.findById(id);
		List<Paciente> pacientes = Paciente.find("status != ?1", Status.INATIVO).fetch();
		List<Medico> medicos = Medico.find("status != ?1", Status.INATIVO).fetch();
		renderTemplate("Consultas/form.html", c, pacientes, medicos);
	}

	public static void listar(String termo) {
		List<Consulta> consultas = null;
		if (termo == null) {
			consultas = Consulta.find("status != ?1 order by data", Status.INATIVO).fetch();
		} else {
			consultas = Consulta.find(
				"status != ?1 and (lower(paciente.nome) like ?2 or lower(medico.nome) like ?2) order by data",
				Status.INATIVO, "%"+termo.toLowerCase()+"%"
			).fetch();
		}
		render(consultas, termo);
	}

	public static void detalhar(Long id) {
		Consulta consulta = Consulta.findById(id);
		render(consulta);
	}

	public static void salvar(Consulta consulta) {

	    boolean conflito = Consulta.find(
	        "status != ?1 and medico = ?2 and data = ?3 and horario = ?4 and id != ?5",
	        Status.INATIVO, consulta.medico, consulta.data, consulta.horario,
	        consulta.id == null ? 0L : consulta.id
	    ).fetch().size() > 0;

	    if (conflito) {
	        validation.addError("consulta.horario", "Este médico já tem uma consulta marcada nesse dia e horário.");
	    }

	    if (validation.hasErrors()) {
	    	Consulta c = consulta;
	        List<Paciente> pacientes = Paciente.find("status != ?1", Status.INATIVO).fetch();
	        List<Medico> medicos = Medico.find("status != ?1", Status.INATIVO).fetch();
	        renderTemplate("Consultas/form.html", consulta, pacientes, medicos);
	    }

	    consulta.save();
	    flash.success("Consulta cadastrada com sucesso!");
	    listar(null);
	}

	public static void remover(Long id) {
		Consulta c = Consulta.findById(id);
		c.status = Status.INATIVO;
		c.save();
		flash.success("Consulta removida com sucesso!");
		listar(null);
	}

}