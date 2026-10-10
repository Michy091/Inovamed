package models;

import java.security.MessageDigest;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Usuario extends Model {

	@Required(message = "Informe o nome")
	public String nome;

	@Required(message = "Informe o login")
	@Column(unique = true)
	public String login;

	public String senha;
	public String salt;

	@Enumerated(EnumType.STRING)
	public Perfil perfil;

	@Enumerated(EnumType.STRING)
	public Status status;

	public Usuario() {
		this.status = Status.ATIVO;
	}

	public Usuario(String nome, String login, String senhaPura, Perfil perfil) {
		this();
		this.nome = nome;
		this.login = login;
		this.perfil = perfil;
		definirSenha(senhaPura);
	}

	public void definirSenha(String senhaPura) {
		this.salt = UUID.randomUUID().toString();
		this.senha = hash(senhaPura, this.salt);
	}

	public static Usuario autenticar(String login, String senhaPura) {
		if (login == null || senhaPura == null) {
			return null;
		}
		Usuario u = Usuario.find("login = ?1 and status = ?2", login.trim(), Status.ATIVO).first();
		if (u == null) {
			return null;
		}
		boolean confere = MessageDigest.isEqual(
			u.senha.getBytes(), hash(senhaPura, u.salt).getBytes());
		return confere ? u : null;
	}

	public static String hash(String senhaPura, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest((salt + senhaPura).getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}