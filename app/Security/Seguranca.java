package Security;

import controllers.Logins;
import models.Perfil;
import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller {

	// AUTENTICAÇÃO
	@Before
	static void auth() {
		if (!session.contains("usuarioLogado")) {
			flash.error("Restrito para usuários autenticados!");
			Logins.form();
		}
	}

	// AUTORIZAÇÃO (administrador)
	@Before
	static void verificarAdministrador() {
		String perfil = session.get("perfilUsuario");
		Administrador possuiAnotacaoAdministrador = getActionAnnotation(Administrador.class);
		if (possuiAnotacaoAdministrador != null && !Perfil.ADMIN.name().equals(perfil)) {
			forbidden("Acesso restrito aos administradores do sistema");
		}
	}

	// AUTORIZAÇÃO (médico só acessa as próprias consultas)
	@Before
	static void verificarMedico() {
		String perfil = session.get("perfilUsuario");
		if (Perfil.MEDICO.name().equals(perfil)) {
			boolean consultas = "Consultas".equals(request.controller);
			String acao = request.actionMethod;
			boolean permitida = consultas
				&& ("listar".equals(acao) || "detalhar".equals(acao) || "concluir".equals(acao));
			if (!permitida) {
				forbidden("Médicos têm acesso apenas às suas consultas");
			}
		}
	}

}