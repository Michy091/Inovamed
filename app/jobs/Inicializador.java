package jobs;

import play.jobs.*;

import java.util.Calendar;
import java.util.Date;

import models.*;

@OnApplicationStart
public class Inicializador extends Job {
	@Override
	public void doJob() {
		if (Especialidade.count() == 0) {
			criarEspecialidade("Cardiologia", 101);
			criarEspecialidade("Pediatria", 102);
			criarEspecialidade("Ortopedia", 103);
			criarEspecialidade("Clínica Geral", 104);
		}

		if (Usuario.count() == 0) {
			new Usuario("Administrador", "admin", "admin123", Perfil.ADMIN).save();
			new Usuario("Atendente", "atendente", "atend123", Perfil.ATENDENTE).save();
		}

		if (Medico.count() == 0) {
			criarMedico("DR. CARLOS MENDES", "carlos.mendes@inovamed.com", 1980, "CRM-RN 1001", "Cardiologia");
			criarMedico("DRA. ANA PAULA SOUZA", "ana.souza@inovamed.com", 1985, "CRM-RN 1002", "Pediatria");
			criarMedico("DR. ROBERTO LIMA", "roberto.lima@inovamed.com", 1978, "CRM-RN 1003", "Ortopedia");
			criarMedico("DRA. MARIANA COSTA", "mariana.costa@inovamed.com", 1990, "CRM-RN 1004", "Clínica Geral");
		}
	}

	private void criarEspecialidade(String nome, Integer ramal) {
		Especialidade e = new Especialidade();
		e.nome = nome;
		e.ramal = ramal;
		e.save();
	}

	private void criarMedico(String nome, String email, int anoNascimento, String crm, String nomeEspecialidade) {
		Especialidade esp = Especialidade.find("nome = ?1", nomeEspecialidade).first();

		Calendar cal = Calendar.getInstance();
		cal.set(anoNascimento, Calendar.JANUARY, 1, 0, 0, 0);
		Date nascimento = cal.getTime();

		Medico m = new Medico();
		m.nome = nome;
		m.email = email;
		m.nascimento = nascimento;
		m.crm = crm;
		m.especialidade = esp;
		m.save();
	}
}