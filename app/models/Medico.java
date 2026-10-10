package models;

import java.util.Date;
import java.security.MessageDigest;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import play.data.binding.As;
import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Medico extends Model{

	public String nome;
	public String email;
	@As("yyyy-MM-dd")
	public Date nascimento;
	@Required(message = "Informe o CRM")
	@Match(value = "^\\d{4,6}/[A-Z]{2}$", message = "CRM inválido. Use o formato 123456/RN")
	@MaxSize(value = 9, message = "O CRM deve ter no máximo 9 caracteres")
	public String crm;
	
	@ManyToOne
	public Especialidade especialidade;
	
	@Enumerated(EnumType.STRING)
	public Status status;
	
	@Column(unique = true)
	public String login;
	public String senha;
	public String salt;

	@Enumerated(EnumType.STRING)
	public Perfil perfil;
	
	public Medico() {
		this.status = Status.ATIVO;
		this.perfil = Perfil.MEDICO;
	}
	public void definirSenha(String senhaPura) {
		this.salt = UUID.randomUUID().toString();
		this.senha = Usuario.hash(senhaPura, this.salt);
	}

	public static Medico obterUsuario(String login, String senhaPura) {
		if (login == null || senhaPura == null) {
			return null;
		}
		Medico m = Medico.find("login = ?1 and status = ?2", login.trim(), Status.ATIVO).first();
		if (m == null || m.senha == null) {
			return null;
		}
		boolean confere = MessageDigest.isEqual(
			m.senha.getBytes(), Usuario.hash(senhaPura, m.salt).getBytes());
		return confere ? m : null;
	}
}
