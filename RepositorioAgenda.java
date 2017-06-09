package agenda;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.EOFException;

import java.io.File;

public class RepositorioAgenda {
    private static final String baseNomeArquivo = "Agenda-";
    private File arquivo;

    private AgendaSemanal agendaRepo;
    
    public AgendaSemanal getAgenda(){
    	return agendaRepo;
    }
    
    public RepositorioAgenda(AgendaSemanal agenda) {
        String nomeArquivo = baseNomeArquivo + agenda.getMes() + "_" + agenda.getSemana() + ".bin";
        arquivo = new File(nomeArquivo);
        
        agendaRepo = agenda;
    }
    
    public RepositorioAgenda(int mes, int semana){
    	String nomeArquivo = baseNomeArquivo + mes + "_" + semana + ".bin";
    	arquivo = new File(nomeArquivo);
    	
    	try {
			ObjectInputStream arq = new ObjectInputStream (new FileInputStream (arquivo.getName()));
			this.agendaRepo = (AgendaSemanal)arq.readObject();
			
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}    	
    }
    
    public void salvar(){
    	try {
			ObjectOutputStream arq = new ObjectOutputStream (new FileOutputStream (arquivo.getName()));
			arq.reset();
			arq.writeObject(agendaRepo);			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}