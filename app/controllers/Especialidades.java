package controllers;

import java.util.List;

import models.Especialidade;
import models.Status;
import play.mvc.Controller;

public class Especialidades extends Controller {

		public static void form() {
			Especialidade d = new Especialidade();
			render(d);
		}

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

		public static void salvar(Especialidade especialidades) {
			especialidades.nome = especialidades.nome.toUpperCase();
			especialidades.save();
			flash.success("Especialidade cadastrada com sucesso!");
			listar(null);
		}


		public static void remover(Long id) {
			Especialidade d = Especialidade.findById(id);
			d.status = Status.INATIVO;
			d.save();
			flash.success("Especialidade removida com sucesso!");
			listar(null);
			return;
		}
	}

