package controllers;

import java.util.List;

import Security.Administrador;
import Security.Seguranca;
import models.Especialidade;
import models.Medico;
import models.Status;
import models.Usuario;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;
@With(Seguranca.class)
public class Medicos extends Controller {

	@Administrador
	public static void form() {
	    Medico p = new Medico();
	    List<Especialidade> especialidades = Especialidade.find(
	        "status != ?1 order by nome", Status.INATIVO).fetch();
	    render(p, especialidades);
	}

	@Administrador
	public static void editar(Long id) {
	    Medico p = Medico.findById(id);
	    List<Especialidade> especialidades = Especialidade.find(
	        "status != ?1 order by nome", Status.INATIVO).fetch();
	    renderTemplate("Medicos/form.html", p, especialidades);
	}

	public static void listar(String termo) {
	    List<Medico> medicos = null;
	    if (termo != null) {
	        termo = termo.trim();
	    }
	    if (termo == null || termo.isEmpty()) {
	        medicos = Medico.find(
	            "status != ?1 order by nome", Status.INATIVO).fetch();
	    } else {
	        medicos = Medico.find(
	            "status != ?1 and (lower(nome) like ?2 or lower(email) like ?2) order by nome",
	            Status.INATIVO, "%" + termo.toLowerCase() + "%").fetch();
	    }
	    render(medicos, termo);
	}
	
	public static void detalhar(Long id) {
		Medico medico = Medico.findById(id);
		render(medico);
	}
	@Administrador
	public static void salvar(@Valid Medico medico) {
		boolean novo = medico.id == null;

		if (novo) {
			if (medico.login == null || medico.login.trim().isEmpty()) {
				validation.addError("medico.login", "Informe o login");
			} else if (Usuario.count("login = ?1", medico.login.trim()) > 0
					|| Medico.count("login = ?1", medico.login.trim()) > 0) {
				validation.addError("medico.login", "Este login já está em uso");
			}
			if (medico.senha == null || medico.senha.length() < 6) {
				validation.addError("medico.senha", "A senha deve ter ao menos 6 caracteres");
			}
		}

		if (validation.hasErrors()) {
			Medico p = medico;
			List<Especialidade> especialidades = Especialidade.find("status != ?1", Status.INATIVO).fetch();
			renderTemplate("Medicos/form.html", p, especialidades);
		}

		if (medico.nome != null) medico.nome = medico.nome.toUpperCase();
		if (medico.email != null) medico.email = medico.email.toLowerCase();

		if (novo) {
			medico.login = medico.login.trim();
			medico.definirSenha(medico.senha);
		}

		medico.save();
		flash.success("Médico salvo com sucesso!");
		listar(null);
	}
	@Administrador
	public static void remover(Long id) {
		Medico me = Medico.findById(id);
		me.status = Status.INATIVO;
		me.save();
		
		flash.success("Médico removido com sucesso!");
		listar(null);
	}


}

