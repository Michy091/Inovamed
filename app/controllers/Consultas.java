package controllers;

import java.util.List;

import Security.Administrador;
import Security.Seguranca;
import models.Consulta;
import models.Medico;
import models.Paciente;
import models.Perfil;
import models.Status;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;
@With(Seguranca.class)
public class Consultas extends Controller {

	public static void form() {
		Consulta c = new Consulta();
		List<Paciente> pacientes = Paciente.find("status != ?1", Status.INATIVO).fetch();
		List<Medico> medicos = Medico.find("status != ?1", Status.INATIVO).fetch();
		render(c, pacientes, medicos);
	}

	public static void editar(Long id) {
	    Consulta c = Consulta.findById(id);
	    if (c == null) {
	        notFound("Consulta não encontrada");
	    }
	    List<Paciente> pacientes = Paciente.find("status != ?1", Status.INATIVO).fetch();
	    List<Medico> medicos = Medico.find("status != ?1", Status.INATIVO).fetch();
	    renderTemplate("Consultas/form.html", c, pacientes, medicos);
	}

	public static void listar(String termo) {
	    List<Consulta> consultas = null;
	    if (termo != null) {
	        termo = termo.trim();
	    }
	    if ("MEDICO".equals(session.get("perfilUsuario"))) {
	        Long medicoId = Long.valueOf(session.get("medicoId"));
	        consultas = Consulta.find(
	            "status != ?1 and medico.id = ?2 order by data, horario",
	            Status.INATIVO, medicoId).fetch();
	    } else if (termo == null || termo.isEmpty()) {
	        consultas = Consulta.find(
	            "status != ?1 order by data, horario", Status.INATIVO).fetch();
	    } else {
	        consultas = Consulta.find(
	            "status != ?1 and (lower(paciente.nome) like ?2 or lower(medico.nome) like ?2) order by data, horario",
	            Status.INATIVO, "%" + termo.toLowerCase() + "%"
	        ).fetch();
	    }
	    render(consultas, termo);
	}
	public static void detalhar(Long id) {
	    Consulta consulta = Consulta.findById(id);
	    if (consulta == null) {
	        notFound("Consulta não encontrada");
	    }
	    if ("MEDICO".equals(session.get("perfilUsuario"))
	            && !consulta.medico.id.toString().equals(session.get("medicoId"))) {
	        forbidden("Você só pode ver as suas próprias consultas");
	    }
	    render(consulta);
	}
	private static boolean ehMedico() {
		return Perfil.MEDICO.name().equals(session.get("perfilUsuario"));
	}

	private static Long meuMedicoId() {
		return Long.valueOf(session.get("medicoId"));
	}
	

		public static void salvar(@Valid Consulta consulta) {

		    boolean conflito = consulta.medico != null
		        && consulta.data != null
		        && consulta.horario != null
		        && Consulta.find(
		            "status != ?1 and medico = ?2 and data = ?3 and horario = ?4 and id != ?5",
		            Status.INATIVO, consulta.medico, consulta.data, consulta.horario,
		            consulta.id == null ? 0L : consulta.id
		        ).fetch().size() > 0;

		    if (conflito) {
		        validation.addError("consulta.horario",
		            "Este médico já tem uma consulta marcada nesse dia e horário.");
		    }

		    if (validation.hasErrors()) {
		        Consulta c = consulta;
		        List<Paciente> pacientes = Paciente.find("status != ?1", Status.INATIVO).fetch();
		        List<Medico> medicos = Medico.find("status != ?1", Status.INATIVO).fetch();
		        renderTemplate("Consultas/form.html", c, pacientes, medicos);   // c, não consulta
		    }

		    consulta.save();
		    flash.success("Consulta salva com sucesso!");
		    listar(null);
		
	}
	@Administrador
	public static void remover(Long id) {
	    Consulta p = Consulta.findById(id);

	    if (p == null) {
	        flash.error("Consulta não encontrada.");
	        listar(null);
	        return;
	    }

	    p.status = Status.INATIVO;
	    p.save();

	    flash.success("Consulta removida com sucesso!");
	    listar(null);
	}
}