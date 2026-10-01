package controllers;

import java.util.List;

import models.Especialidade;
import models.Medico;
import models.Status;
import play.mvc.Controller;

public class Medicos extends Controller {

	public static void form() {
		Medico p = new Medico();
		List<Especialidade> especialidades = Especialidade.findAll();
		render(p, especialidades);

	}
	public static void editar(Long id) {
		Medico p = Medico.findById(id);
		List<Especialidade> especialidades = Especialidade.findAll();
		renderTemplate("Medicos/form.html", p, especialidades);
	}
	
	public static void listar(String termo) {
		List<Medico> medicos = null;
		if (termo == null) {
			medicos = Medico.find("status != ?1", Status.INATIVO).fetch();			
		} else {
			medicos = Medico.find("status != ?1 and (lower(nome) like ?2 or lower(email) like ?2)", Status.INATIVO, "%"+termo.toLowerCase()+"%").fetch();
		}
		render(medicos, termo);
	}
	
	public static void detalhar(Long id) {
		Medico medico = Medico.findById(id);
		render(medico);
	}
	
	public static void salvar(Medico medico) {
		medico.nome = medico.nome.toUpperCase();
		medico.email = medico.email.toLowerCase();
		medico.save();
		flash.success("Médico cadastrado com sucesso!");
		listar(null);
	}
	
	public static void remover(Long id) {
		Medico me = Medico.findById(id);
		me.status = Status.INATIVO;
		me.save();
		
		flash.success("Médico removido com sucesso!");
		listar(null);
	}

}

