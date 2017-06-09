package agenda;

import java.io.Serializable;

public class AgendaSemanal implements Serializable {
    private CadaDia[] dias;

    private int mes;
    private int semana;

    public AgendaSemanal(int mes, int semana) {
        dias = new CadaDia[5];
        this.mes = mes;
        this.semana = semana;
    }

    public int getMes() {
        return mes;
    }

    public int getSemana() {
        return semana;
    }

    public CadaDia[] getDias() {
        return dias;
    }

    public static String nomeDia(int dia) {
        switch (dia) {
		case 0:
			return "Segunda";
		case 1:
			return "Terça";
		case 2:
			return "Quarta";
		case 3:
			return "Quinta";
		case 4:
			return "Sexta";
		default:
			return "";
		}
    }
}
