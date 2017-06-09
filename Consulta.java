package agenda;

import java.io.Serializable;

public class Consulta implements Serializable {
    private String nomeCliente;
    private int horarioConsulta;
    private int tolerancia;
    private int tipoConsulta;

    public Consulta(String cliente, int horario, int tol, int tipo) {
        this.nomeCliente = cliente;
        this.horarioConsulta = horario;
        this.tolerancia = tol;
        this.tipoConsulta = tipo;
    }

    public int getTipoConsulta(){
    	return tipoConsulta;
    }
    
    public String getHorario() {
    	return HorarioHelper.formataHorario(horarioConsulta);
    }

    public boolean equals(Object other){
    	if(other == null)
    		return false;
    	
    	if(other.getClass() != this.getClass())
    		return false;
    	
    	Consulta outra = (Consulta)other;
    	
    	return outra.getNomeCliente().equals(this.nomeCliente) && outra.getHorarioConsulta() == this.horarioConsulta;
    }
    
    public void setHorarioConsulta(int novoHorario) {
    	this.horarioConsulta = novoHorario;
    } 
    
    public int getHorarioConsulta() {
        return horarioConsulta;
    }

    public int getTolerancia() {
        return tolerancia;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }
    
    public String getTipoConsultaStr() {
        return tipoConsulta == 1 ? "Convênio" : "Particular";
    }

    public String toString() {
        return "Nome cliente: " + nomeCliente +
                "Horário: " + horarioConsulta + 
                "Tolerancia: " + tolerancia + 
                "Tipo: " + tipoConsulta + 
                "getHorario(): " + getHorario();
    }
}