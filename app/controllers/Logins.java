package controllers;

import models.Medico;
import models.Usuario;
import play.mvc.Controller;

public class Logins extends Controller{

	public static void form() {
		render();
	}

	public static void logar(String login, String senha) {
		// 1) admin e atendente (tabela Usuario)
		Usuario usuario = Usuario.autenticar(login, senha);
		if (usuario != null) {
			session.put("usuarioLogado", usuario.login);
			session.put("usuarioNome", usuario.nome);
			session.put("perfilUsuario", usuario.perfil.name());
			flash.success("Login realizado com sucesso");
			Consultas.listar(null);
		}

		// 2) médico (login dentro do próprio Medico, como Pessoa na base)
		Medico medico = Medico.obterUsuario(login, senha);
		if (medico == null) {
			flash.error("Usuário ou senha inválido. Tente novamente!");
			flash.put("login", login);
			form();
		}

		session.put("usuarioLogado", medico.login);
		session.put("usuarioNome", medico.nome);
		session.put("perfilUsuario", medico.perfil.name());
		session.put("medicoId", medico.id.toString());
		flash.success("Login realizado com sucesso");
		Application.index();
	}

	public static void sair() {
		session.clear();
		flash.success("Sessão encerrada com sucesso.");
		form();
	}

	
}
