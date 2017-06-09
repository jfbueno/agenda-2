package agenda;

import java.io.Serializable;

public class CadaDia implements Serializable {
    private int qtdConsultas;
    private SinglyLinkedList<Consulta> listaConsultas;
    private LinkedQueue<Consulta> filaEspera;

    public static final int[] horarios = new int[] {
        730, 800, 830, 900, 930, 1000, 1030, 1100, 1130, 1200, 1230, 1300, 1330, 1400, 1430, 1500, 1530, 1600, 1630, 1700, 1730, 1800,
        1830, 1900, 1930, 2000, 2030, 2100, 2130, 2200, 2230
    };

    public CadaDia() {
        listaConsultas = new SinglyLinkedList<>();
        filaEspera = new LinkedQueue<>();
    }

    public SinglyLinkedList<Consulta> getConsultas() {
        return listaConsultas;
    }
    
    public LinkedQueue<Consulta> getFilaEspera() {
        return filaEspera;
    }

    public ConsultaPorTipo qtdConsultas(){
    	ConsultaPorTipo consultasMarcadas = qtdConsultas(listaConsultas);
    	ConsultaPorTipo filaDeEspera = qtdConsultas(filaEspera);
    	
    	return new ConsultaPorTipo(consultasMarcadas.CONVENIO + filaDeEspera.CONVENIO,
    								consultasMarcadas.PARTICULAR + filaDeEspera.PARTICULAR);
    }
    
    public ConsultaPorTipo qtdConsultas(List<Consulta> lista){
    	int qtdConvenio = 0;
    	int qtdParticular = 0;
    	
    	for(int i = 0; i < lista.numElements(); i++){
    		if(lista.get(i).getTipoConsulta() == 1){
    			qtdConvenio++;
    		}else{
    			qtdParticular++;
    		}
    	}
    	
    	return new ConsultaPorTipo(qtdConvenio, qtdParticular);
    }
    
    public void adicionarConsulta(Consulta consulta){
        if(!verificaHorarioLivre(consulta.getHorarioConsulta())){
            adicionarConsultaRepetida(consulta);
            return;
        }
        
        if(listaConsultas.numElements() == 0){
            listaConsultas.insertFirst(consulta);
            return;
        }

        for(int i = 0; i < listaConsultas.numElements(); i++){
            Consulta c = listaConsultas.get(i);

            if(c.getHorarioConsulta() > consulta.getHorarioConsulta()){
                listaConsultas.insert(consulta, i);
                return;
            }else if(i == listaConsultas.numElements() - 1){
                listaConsultas.insertLast(consulta);
                return;
            }
        }


    }

    // Tenta fazer algo com uma consulta num horario que ja existe marcação
    private void adicionarConsultaRepetida(Consulta consulta) {
        int tolerancia = consulta.getTolerancia();

        switch(tolerancia){
            case 0:
                filaEspera.enqueue(consulta);
                break;
            case 1:
            	if(!tentaAdicionarHorarioProximo(consulta)){
            		filaEspera.enqueue(consulta);
            	}
                break;
            case 2:
            	if(listaConsultas.numElements() != horarios.length){
            		adicionarEmHorarioLivre(consulta);
            	}else{
            		filaEspera.enqueue(consulta);
            	}
                break;

        }
    }
    
    /* Tenta adicionar a consulta em qqr horário que tiver disponível */
    private void adicionarEmHorarioLivre(Consulta consulta){
    	for(int i = 0; i < horarios.length; i++){
    		int horario = horarios[i];
    		if(verificaHorarioLivre(horario)){
    			consulta.setHorarioConsulta(horario);
    	    	adicionarConsulta(consulta);
    			break;
    		}
    	}    	
    }
    
    private boolean tentaAdicionarHorarioProximo(Consulta consulta){    	
    	int indexHorario = indexHorario(consulta.getHorarioConsulta());
    	
    	if(indexHorario != 0){
    		int anterior = horarios[indexHorario -1];
    		if(verificaHorarioLivre(anterior)){
    			consulta.setHorarioConsulta(anterior);
    			adicionarConsulta(consulta);
    			return true;
    		}
    	}
    	
    	// Se chegou até aqui é pq não conseguiu marcar no anterior
    	if(indexHorario < horarios.length - 1){
    		int proximo = horarios[indexHorario +1];    		
    		if(verificaHorarioLivre(proximo)){
    			consulta.setHorarioConsulta(proximo);
    			adicionarConsulta(consulta);
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    private int indexHorario(int horario){
    	for(int i = 0; i < horarios.length; i++){
    		if(horarios[i] == horario)
    			return i;
    	}
    	
    	throw new RuntimeException("Horário não encontrado");
    }

    public boolean verificaHorarioLivre(int horario){
        for(int i = 0; i < listaConsultas.numElements(); i++){
            if(listaConsultas.get(i).getHorarioConsulta() == horario)
                return false;
        }

        return true;
    }
}
