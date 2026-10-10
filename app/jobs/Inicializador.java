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
			criarMedico("DR. CARLOS MENDES", "carlos.mendes@inovamed.com", 1980, "1001/RN", "Cardiologia", "carlos", "carlos123");
			criarMedico("DRA. ANA PAULA SOUZA", "ana.souza@inovamed.com", 1985, "1002/RN", "Pediatria", "ana", "ana123");
			criarMedico("DR. ROBERTO LIMA", "roberto.lima@inovamed.com", 1978, "1003/RN", "Ortopedia", "roberto", "roberto123");
			criarMedico("DRA. MARIANA COSTA", "mariana.costa@inovamed.com", 1990, "1004/RN", "Clínica Geral", "mariana", "mariana123");
		}}
	private void criarEspecialidade(String nome, Integer ramal) {
		Especialidade e = new Especialidade();
		e.nome = nome;
		e.ramal = ramal;
		e.save();
	}

	private void criarMedico(String nome, String email, int anoNascimento, String crm,
			String nomeEspecialidade, String login, String senha) {
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
		m.login = login;
		m.definirSenha(senha);
		m.save();
	}}