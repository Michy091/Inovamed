package controllers;

import java.util.List;

import models.Especialidade;
import models.Status;
import play.data.validation.Valid;
import play.mvc.Controller;
import security.Administrador;

public class Especialidades extends Controller {

	@Administrador
	public static void form() {
		Especialidade d = new Especialidade();
		render(d);
	}
@Administrador
	public static void editar(Long id) {
		Especialidade d = Especialidade.findById(id);
		renderTemplate("Especialidades/form.html", d);
	}

	public static void listar(String termo) {
		List<Especialidade> especialidades = null;
		if (termo == null) {
			especialidades = Especialidade.find("1=1 order by nome").fetch();
		} else {
			especialidades = Especialidade.find("lower(nome) like ?1", "%"+termo.toLowerCase()+"%").fetch();
		}
		render(especialidades, termo);
	}
	@Administrador
	public static void salvar(@Valid Especialidade especialidade) {
		if (validation.hasErrors()) {
			Especialidade d = especialidade;
			renderTemplate("Especialidades/form.html", d);
		}
		especialidade.nome = especialidade.nome.toUpperCase();
		especialidade.save();
		flash.success("Especialidade salva com sucesso!");
		listar(null);
	}

	@Administrador
	public static void remover(Long id) {
		Especialidade d = Especialidade.findById(id);
		d.status = Status.INATIVO;
		d.save();
		flash.success("Especialidade removida com sucesso!");
		listar(null);
		return;
	}
	}

