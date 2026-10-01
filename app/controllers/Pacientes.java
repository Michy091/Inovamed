package controllers;

import java.util.List;

import models.Paciente;
import models.Status;
import play.mvc.Controller;

public class Pacientes extends Controller {

	public static void form() {
		Paciente p = new Paciente();
		render(p);
	}

	public static void editar(Long id) {
		Paciente p = Paciente.findById(id);
		renderTemplate("Pacientes/form.html", p);
	}

	public static void listar(String termo) {
		List<Paciente> pacientes = null;
		if (termo == null) {
			pacientes = Paciente.find("status != ?1", Status.INATIVO).fetch();
		} else {
			pacientes = Paciente.find("status != ?1 and (lower(nome) like ?2 or lower(email) like ?2)", Status.INATIVO, "%"+termo.toLowerCase()+"%").fetch();
		}
		render(pacientes, termo);
	}

	public static void detalhar(Long id) {
		Paciente paciente = Paciente.findById(id);
		render(paciente);
	}

	public static void salvar(Paciente paciente) {
		if (paciente.nome != null) paciente.nome = paciente.nome.toUpperCase();
		if (paciente.email != null) paciente.email = paciente.email.toLowerCase();
		paciente.save();
		flash.success("Paciente cadastrado com sucesso!");
		listar(null);
	}

	public static void remover(Long id) {
		Paciente p = Paciente.findById(id);
		p.status = Status.INATIVO;
		p.save();
		flash.success("Paciente removido com sucesso!");
		listar(null);
	}

}