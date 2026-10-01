package models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Especialidade extends Model{

	@Required
	public String nome;
	public Integer ramal;

	@Enumerated(EnumType.STRING)
	public Status status;

	public Especialidade() {
		this.status = Status.ATIVO;
	}
}