package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.data.binding.As;
import play.db.jpa.Model;
@Entity
public class Paciente extends Model {
	public String nome;
	public String email;
	@As("yyyy-MM-dd")
	public Date nascimento;
	public String cpf;
	public String telefone;

	@Enumerated(EnumType.STRING)
	public Status status;

	public Paciente() {
		this.status = Status.ATIVO;
	}
}
