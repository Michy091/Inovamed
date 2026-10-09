package models;

import java.util.Date;

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
	
	public Medico() {
		this.status = Status.ATIVO;
	}
}
