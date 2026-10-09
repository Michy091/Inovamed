package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

import play.data.validation.Required;
import play.data.validation.Match;
@Entity
public class Consulta extends Model {

	@Required(message = "Selecione o paciente")
	@ManyToOne
	public Paciente paciente;

	@Required(message = "Selecione o médico")
	@ManyToOne
	public Medico medico;

	@Required(message = "Informe a data da consulta (dd/mm/aaaa)")
	public Date data;

	@Required(message = "Informe o horário da consulta")
	@Match(value = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Horário inválido. Use o formato hh:mm (ex.: 14:30)")
	public String horario;
	@Enumerated(EnumType.STRING)
	public Status status;
	
	public Consulta() {
		this.status = Status.ATIVO;
	}
	}
