package agenda;

public class HorarioHelper {
	public static String formataHorario(int horario){
		String h = String.format("%04d", horario);
        String p1 = h.substring(0, 2);
        String p2 = h.substring(2);
        return p1 + ":" + p2;
	}
}
