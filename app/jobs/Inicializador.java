package jobs;

import play.jobs.*;
import models.*;

@OnApplicationStart
public class Inicializador extends Job {
	@Override
    public void doJob() {
        if (Especialidade.count() == 0) {
            Especialidade d1 = new Especialidade();
            d1.nome = "Cardiologia";
            d1.ramal = 101;
            d1.save();

            Especialidade d2 = new Especialidade();
            d2.nome = "Pediatria";
            d2.ramal = 102;
            d2.save();

            Especialidade d3 = new Especialidade();
            d3.nome = "Ortopedia";
            d3.ramal = 103;
            d3.save();

            Especialidade d4 = new Especialidade();
            d4.nome = "Clínica Geral";
            d4.ramal = 104;
            d4.save();
        }
    }
}
