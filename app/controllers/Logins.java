package controllers;

import models.Usuario;
import play.mvc.Controller;

public class Logins extends Controller{

	public static void form() {
		render();
	}

	public static void logar(String login, String senha) {
		Usuario usuario = Usuario.autenticar(login, senha);

		if (usuario == null) {
			flash.error("Usuário ou senha inválido. Tente novamente!");
			flash.put("login", login);
			form();
		}

		// A sessão do Play só armazena String
		session.put("usuarioLogado", usuario.login);
		session.put("usuarioNome", usuario.nome);
		session.put("perfilUsuario", usuario.perfil.name());

		flash.success("Login realizado com sucesso");
		Application.index();
	}

	public static void sair() {
		session.clear();
		flash.success("Sessão encerrada com sucesso.");
		form();
	}

	
}
