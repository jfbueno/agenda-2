package agenda;

import java.util.Scanner;

public class Program {
	static Scanner scanner = new Scanner(System.in);
	static AgendaSemanal agenda;
	static RepositorioAgenda repo;

	static final String strMenuPrincipal = "Escolha uma das opções\n1 - Criar agenda\n2 - Abrir agenda\n0 - Sair";
	static final String strMenu2 = "Escolha uma das opções\n" + "1 - Exibir toda a agenda\n" + "2 - Marcar consulta\n"
			+ "3 - Desmarcar consulta\n" + "4 - Alterar dia e/ou horário da consulta\n"
			+ "5 - Exibir as consultas marcadas em intervalos de dias e horários\n"
			+ "6 - Exibir as consultas em fila de espera para o dia\n" + "7 - Verificar consulta\n"
			+ "8 - Exibir os horários livres para o dia\n" + "9 - Criar matriz\n" + "10 - Salvar Agenda\n"
			+ "11 - Sair";
	
	public static void main(String[] args) {
		System.out.println("Bem-vindo à agenda");

		menu1();
	}

	public static void menu1() {		
		boolean mainLoop = true;
		while (mainLoop) {
			int opcao = pedirInteiro(strMenuPrincipal, 0, 2);
			
			switch (opcao) {
				case 1:
					int mes = pedirInteiro("Escolha o mês da nova agenda: ", 1, 12);
					int semana = pedirInteiro("Escolha a semana da nova agenda: ", 1, 52);
					
					agenda = new AgendaSemanal(mes, semana);
					repo = new RepositorioAgenda(agenda);

					System.out.println("Agenda criada com sucesso!");
					
					menu2();
					
					break;
				case 2:
					int mes_ = pedirInteiro("Escolha o mês: ", 1, 12);
					int semana_ = pedirInteiro("Escolha a semana: ", 1, 52);
					
					repo = new RepositorioAgenda(mes_, semana_);
					agenda = repo.getAgenda();

					System.out.println("Agenda carregada com sucesso!");					

					menu2();
					
					break;
				case 0:
					mainLoop = false;
					System.out.println("Bye");
					break;
			}
		}
	}
	
	public static void menu2() {
		
		boolean mainLoop = true;
		while (mainLoop) {			
			int opcao = pedirInteiro(strMenu2, 1, 11);
			
			switch (opcao) {
				case 1: {
					exibirTodaAgenda();
					break;
				}
				case 2: {
					marcarConsulta();
					break;
				}
				case 3: {
					desmarcarConsulta();
					break;
				}
				case 4: {
					break;
				}
				case 5: {
					exibirNoIntervalo();
					break;
				}
				case 6: {
					exibirFilaEspera();
					break;
				}
				case 7: {
					System.out.println("Digite o nome do cliente: ");
					scanner.nextLine();
					String nome = scanner.nextLine();
					verificarConsulta(nome);
					break;
				}
				case 8: {
					exibirHorariosLivres();
					break;
				}
				case 9: {
					criarExibirMatriz();
					break;
				}
				case 10: {
					salvarAgenda();
					break;
				}
				case 11: {
					boolean salvar = pedirInteiro("Deseja salvar antes de sair?\n1- Sim\n2- Não", 1, 2) == 1;
					
					if(salvar){
						salvarAgenda();
					}
					
					System.out.println("\n---- Fechando agenda ----");
					
					menu1();
					
					break;
				}
			}
		}
	}

	private static void exibirNoIntervalo(){
		int[] opcoesInts = new int[4];
		boolean valido = false;
		while(!valido){
			System.out.println("Qual o intervalo que deseja ver?");
			String input = scanner.nextLine();
			
			String[] opcoes = input.split(",");					
			if(opcoes != null && opcoes.length == 4){
				for(int i = 0; i < opcoes.length; i++){
					valido = IntegerExtension.isSafeToParse(opcoes[i]);
					if(valido){
						opcoesInts[i] = Integer.parseInt(opcoes[i]);
					}
				}
			}
		}
		
		int diaInicial = opcoesInts[0] - 2;
		int diaFinal = opcoesInts[1] - 2;
		int horaInicial = opcoesInts[2];
		int horaFinal = opcoesInts[3];
		
		CadaDia[] dias = agenda.getDias();
		
		for(int i = 0; (i <= diaFinal && diaInicial <= i); i++){
			if(dias[i] == null){
				continue;
			}
			
			List<Consulta> lista = dias[i].getConsultas();
			String nomeDia = AgendaSemanal.nomeDia(i);
			for(int c = 0; c < lista.numElements(); c++){
				Consulta consulta = lista.get(c);
				if(consulta.getHorarioConsulta() >= horaInicial && consulta.getHorarioConsulta() <= horaFinal){
					System.out.println(String.format("Consulta de %s, em %s às %s", consulta.getNomeCliente(), nomeDia, consulta.getHorario()));
					System.out.println(String.format("Consulta do tipo", consulta.getTipoConsultaStr()));
					System.out.println("\n");
				}
			}
		}
	}

	private static void salvarAgenda() {
		repo.salvar();

		System.out.println("Agenda salva");
	}

	private static void criarExibirMatriz() {
		int[][] matriz = new int[2][5];

		CadaDia[] dias = agenda.getDias();

		for (int i = 0; i < dias.length; i++) {
			if (dias[i] == null) {
				matriz[0][i] = 0;
				matriz[1][i] = 0;
				continue;
			}

			ConsultaPorTipo qtds = dias[i].qtdConsultas();

			matriz[0][i] = qtds.CONVENIO;
			matriz[1][i] = qtds.PARTICULAR;
		}

		printMatriz(matriz);
	}

	private static void printMatriz(int[][] matriz) {
		for (int l = 0; l < matriz.length; l++) {
			for (int c = 0; c < matriz[l].length; c++) {
				System.out.print(matriz[l][c] + "   ");
			}

			System.out.print("\n");
		}
	}

	private static void exibirHorariosLivres() {
		int dia = pedirInteiro(
				"Verificar horários livres de qual dia?\n1. Segunda\n2. Terça\n3. Quarta\n4. Quinta\n5. Sexta", 1, 5);
		dia--;

		CadaDia diaAgenda = agenda.getDias()[dia];

		if (diaAgenda == null || diaAgenda.getConsultas().numElements == 0) {
			System.out.println("Todos os horários disponíveis para " + AgendaSemanal.nomeDia(dia));
			return;
		}

		System.out.println("\n");

		for (int i = 0; i < CadaDia.horarios.length; i++) {
			int horario = CadaDia.horarios[i];

			if (diaAgenda.verificaHorarioLivre(horario)) {
				System.out.println(HorarioHelper.formataHorario(horario) + "\n");
			}
		}
	}

	private static void verificarConsulta(String nomeCliente) {
		for (int indexDia = 0; indexDia < agenda.getDias().length; indexDia++) {
			CadaDia dia = agenda.getDias()[indexDia];

			if (dia == null) {
				continue;
			}

			String nomeDia = AgendaSemanal.nomeDia(indexDia);
			List<Consulta> listaConsulta = dia.getConsultas();
			for (int i = 0; i < listaConsulta.numElements(); i++) {
				Consulta consulta = listaConsulta.get(i);
				if (consulta.getNomeCliente().equals(nomeCliente)) {
					exibirConsulta(nomeDia, consulta, false);
				}
			}

			listaConsulta = dia.getFilaEspera();
			for (int i = 0; i < listaConsulta.numElements(); i++) {
				Consulta consulta = listaConsulta.get(i);
				if (consulta.getNomeCliente().equals(nomeCliente)) {
					exibirConsulta(nomeDia, consulta, true);
				}
			}
		}
	}

	private static void exibirConsulta(String nomeDia, Consulta consulta, boolean daFilaEspera) {
		System.out.println(
				String.format("Consulta de %s, %s às %s", consulta.getNomeCliente(), nomeDia, consulta.getHorario()));
		if (daFilaEspera)
			System.out.println("Esta consulta está na fila de espera");

	}

	private static void exibirFilaEspera() {
		int dia = pedirInteiro(
				"Exibir fila de espera de qual dia?\n1. Segunda\n2. Terça\n3. Quarta\n4. Quinta\n5. Sexta", 1, 5);
		dia--;

		CadaDia diaSemana = agenda.getDias()[dia];
		if (diaSemana == null) {
			System.out.println("Não há nada marcado para " + AgendaSemanal.nomeDia(dia));
		} else {
			List<Consulta> filaEspera = diaSemana.getFilaEspera();
			String nomeDia = AgendaSemanal.nomeDia(dia);

			if (filaEspera.isEmpty()) {
				System.out.println(String.format("Fila de espera vazia para o dia %s\n", nomeDia));
				return;
			}

			exibirListaConsulta(filaEspera);
		}
	}

	private static void desmarcarConsulta() {
		int dia = pedirInteiro("Desmarcar consulta de qual dia?\n1. Segunda\n2. Terça\n3. Quarta\n4. Quinta\n5. Sexta",
				1, 5);
		dia--;

		System.out.println("\nQual o nome do cliente?");
		String nomeCliente = scanner.next();

		int horario = pedirHorarioConsulta("\nQual o horário da consulta a ser desmarcada?");

		boolean daFila = pedirInteiro("Qual o tipo da consulta?\n1. Consulta marcada\n2. Fila de espera", 1, 2) == 2;
		CadaDia diaConsulta = agenda.getDias()[dia];

		Consulta consultaOca = new Consulta(nomeCliente, horario, 0, 0);

		LinkedQueue<Consulta> filaEspera = diaConsulta.getFilaEspera();
		if (daFila) {
			int indexRemover = filaEspera.search(consultaOca);

			if (indexRemover != -1)
				filaEspera.remove(indexRemover);
		} else {
			SinglyLinkedList<Consulta> listaConsultas = diaConsulta.getConsultas();
			int indexRemover = listaConsultas.search(consultaOca);

			if (indexRemover != -1) {
				listaConsultas.remove(indexRemover);
			}

			for (int i = 0; i < filaEspera.numElements(); i++) {
				if (filaEspera.get(i).getHorarioConsulta() == horario) {
					Consulta nova = filaEspera.remove(i);
					diaConsulta.adicionarConsulta(nova);
				}
			}
		}
	}

	private static void exibirTodaAgenda() {
		CadaDia[] dias = agenda.getDias();

		int indexDia = -1;
		for (CadaDia dia : dias) {
			indexDia++;
			String nomeDia = AgendaSemanal.nomeDia(indexDia);

			if (dia == null || dia.getConsultas().isEmpty()) {
				System.out.println(String.format("Não há consultas agendadas para %s\n", nomeDia));
				continue;
			}

			System.out.println(String.format("Consultas agendadas para %s\n", nomeDia));
			exibirListaConsulta(dia.getConsultas());

		}
	}

	private static void exibirListaConsulta(List<Consulta> lista) {
		for (int i = 0; i < lista.numElements(); i++) {
			Consulta consulta = lista.get(i);

			System.out.println(String.format("Horário consulta: %s", consulta.getHorario()));
			System.out.println(String.format("Cliente: %s", consulta.getNomeCliente()));
			System.out.println(String.format("Tipo consulta: %s", consulta.getTipoConsultaStr()));
			System.out.println("");
		}
	}

	private static void marcarConsulta() {
		int dia = pedirInteiro("Marcar consulta para qual dia?\n1. Segunda\n2. Terça\n3. Quarta\n4. Quinta\n5. Sexta",
				1, 5);
		dia--;

		int tolerancia = pedirInteiro("\nDefina a tolerância da consulta de 0 a 2", 0, 2);
		int tipoConsulta = pedirInteiro("\nQual o tipo da consulta?\n1. Convênio\n2. Particular", 1, 2);
		int horario = pedirHorarioConsulta("\nQual o horário da consulta a ser marcada?");

		System.out.println("\nQual o nome do cliente?");
		String nomeCliente = scanner.next();

		Consulta consulta = new Consulta(nomeCliente, horario, tolerancia, tipoConsulta);

		CadaDia diaConsulta;
		if (agenda.getDias()[dia] == null) {
			agenda.getDias()[dia] = new CadaDia();
		}

		diaConsulta = agenda.getDias()[dia];
		diaConsulta.adicionarConsulta(consulta);
	}

	private static int pedirHorarioConsulta(String mensagem) {
		return pedirInteiro(mensagem, 0, 2230);
	}

	private static int pedirInteiro(String mensagem, int menor, int maior) {
		int result = menor - 1;

		while (true) {
			System.out.println(mensagem);
			String input = scanner.next();

			if (IntegerExtension.isSafeToParse(input)) {
				result = Integer.parseInt(input);
			}

			if (result < menor || result > maior) {
				System.out.println("Escolha uma opção válida\n");
				continue;
			}

			break;
		}

		return result;
	}


	private static MesSemana pedirMesSemana() {
        int mes = 0;
        while(mes < 1 || mes > 12) {
            System.out.println("Escolha o mês:");

            String input = scanner.next();
            if(IntegerExtension.isSafeToParse(input)){
                mes = Integer.parseInt(input);
            }
        }

        int semana = 0;
        while(semana < 1 || semana > 52) {
            System.out.println("Escolha a semana:");

            String input = scanner.next();
            if(IntegerExtension.isSafeToParse(input)){
                semana = Integer.parseInt(input);
            }
        }

        return new MesSemana(mes, semana);
    }
}

class MesSemana {
	public int mes;
	public int semana;

	public MesSemana(int mes, int semana) {
		this.mes = mes;
		this.semana = semana;
	}
}