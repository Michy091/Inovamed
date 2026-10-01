package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Consulta extends Model {

	@ManyToOne
	public Paciente paciente;

	@ManyToOne
	public Medico medico;

	public Date data;
	public String horario;

	@Enumerated(EnumType.STRING)
	public Status status;

	public Consulta() {
		this.status = Status.ATIVO;
	}
}