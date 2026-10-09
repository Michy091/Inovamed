package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.data.binding.As;
import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;
@Entity
public class Paciente extends Model {
	public String nome;
	public String email;
	@As("yyyy-MM-dd")
	public Date nascimento;
	@Required(message = "Informe o CPF")
	@Match(value = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido. Use o formato 000.000.000-00")
	@MaxSize(value = 14, message = "O CPF deve ter no máximo 14 caracteres")
	public String cpf;	public String telefone;

	@Enumerated(EnumType.STRING)
	public Status status;

	public Paciente() {
		this.status = Status.ATIVO;
	}
}
