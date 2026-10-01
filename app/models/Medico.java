package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import play.data.binding.As;
import play.db.jpa.Model;

@Entity
public class Medico extends Model{

	public String nome;
	public String email;
	@As("yyyy-MM-dd")
	public Date nascimento;
	public String crm;
	
	@ManyToOne
	public Especialidade especialidade;
	
	@Enumerated(EnumType.STRING)
	public Status status;
	
	public Medico() {
		this.status = Status.ATIVO;
	}
}
