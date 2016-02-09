package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Batedor;
import model.Goleiro;
import model.Jogador;
import model.Observer;
import model.Parametros;
import model.Ponto;
import model.Time;
import model.Torcida;
import model.Util;
import view.PartidaView;

public class PartidaController{
	
	private final int TIME_JOGADOR = 0;
	private final int TIME_MAQUINA = 1;
	private final int NUMERO_COBRANCAS = 6;
	private ArrayList<Observer> torcedores = new ArrayList<Observer>();
	private boolean jogadorChutando;
	
	public boolean isJogadorChutando() {
		return jogadorChutando;
	}

	public void setJogadorChutando(boolean jogadorChutando) {
		this.jogadorChutando = jogadorChutando;
	}


	private Time[] timesDaPartida = new Time[2];

	public Time[] getTimesDaPartida() {
		return timesDaPartida;
	}

	private void AdicionaTorcedoresAoObserver( Observer torcida){
		torcedores.add(torcida);
	}
	
    public static List<Time> PegaTimes(){
    	String dir = ( "c:/ra88408-ra89354/penaltis/" );
    	List<Time> timesParaSelecao  = new ArrayList<Time>(); 
    	try{ 
    		File diretorio = new File( dir );
    		for(File f : diretorio.listFiles()){
    			if( f.isFile()){
    				List<Jogador> jogadores = new ArrayList<Jogador>();
    				BufferedReader br = new BufferedReader(new FileReader(f));
    				String linha = br.readLine();
    				while( linha != null){
    					if (jogadores.isEmpty())
    						jogadores.add(new Goleiro(linha));
    					else
    						jogadores.add(new Batedor(linha));
    					
    					linha = br.readLine();
    				}
    				String[] nomeTime = f.getName().split(".txt");
    				timesParaSelecao.add( new Time( nomeTime[0], jogadores));
    			}
    		}
    		
    	}catch(Exception e){
    		System.out.println("Diretorio :" + dir + " não encontrado");
    	}	
    	return timesParaSelecao;
    }
	
	public void iniciaPartida(Time timeJogador, Time timeAdversario){
		this.timesDaPartida[TIME_JOGADOR] = timeJogador; // inicia time do jogador
		this.timesDaPartida[TIME_MAQUINA] = timeAdversario; // inicia time da Maquina
		AdicionaTorcedoresAoObserver( this.timesDaPartida[TIME_JOGADOR].getTorcida() ); // adiciona Torcedores do jogador ao observer
		AdicionaTorcedoresAoObserver( this.timesDaPartida[TIME_MAQUINA].getTorcida() ); // adiciona torcedores da maquina ao observer
		
		//diz o nome dos times
		System.out.println( this.timesDaPartida[TIME_JOGADOR].getNome() + " contra " +
							this.timesDaPartida[TIME_MAQUINA].getNome() );
		
		//define que o jogo tera inicio automatico
		Parametros.setInicioAltomatico(true);
		
		//decide quem começa as cobranças
		decideInicio();
		// inicia as cobranças de penaltis

		
		iniciaPartidaView();
		
		//iniciaCobrancas();
//		System.out.println("time de inicio:" + timesDaPartida[0].getNome());
//		
//		for( Observer o : this.torcedores ){
//			o.update();
//		}
	}
	
	private void iniciaPartidaView() {
		try{
			PartidaView partidaView = new PartidaView(this.timesDaPartida[TIME_JOGADOR],
				  								  this.timesDaPartida[TIME_MAQUINA]);
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/partidaView.fxml"));
			fxmlLoader.setController(partidaView);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	private void decideInicio(){
		if (!Parametros.isInicioAltomatico() ){
			setJogadorChutando(true);
			return;
		}
		
		//gera um numero aleatorio até 2
		int timeInicio = Util.random(2);
		
		// se o time que for começar o jogo não for o do jogador, é feita uma alteração no array
		// para que o time da Maquina fique como primeira posição do array
		setJogadorChutando(timeInicio != 1);
		
	}
	
	private void iniciaCobrancas(){
		// para cada cobrança até o numero maximo de cobranças faça:
		for( int i = 0; i < NUMERO_COBRANCAS; i++ ){
			
			//para cada time em cada time da partida faça:
			for(Time time : this.timesDaPartida){
				
				//declara Jogador
				Jogador jogador;
				
				//se parametro proximo da fila esta ativado
				if (Parametros.isChamaProximoFila()){
					// seleciona o proximo jogador
					jogador = time.selecionaBatedor(i);  
				}
				else{
					// se não abre tela de selção de jogador
					jogador = time.selecionaBatedor(i);  //TODO CRIAR TELA DE SELEÇÂO DE JOGADORES
				}
				
				 //pega o time adversario
				Time timeAdversario = retornaTimeAdversario(jogador.getTime());
				
				// jogador sofre pressão da torcida
				pressaoTorcida(jogador, timeAdversario);  
				
				// Goleiro do time adversario sofre pressão da torcida
				pressaoTorcida(timeAdversario.getGoleiro(), time);
				
				// direciona chute do jogador
				Ponto chuteJogador  = jogador.direcionar();
				// direciona defesa do goleiro
				Ponto defesaGoleiro = timeAdversario.getGoleiro().direcionar();
				
				// verifica se foi gol
				boolean foiGol = isGol(chuteJogador,
									   defesaGoleiro, 
									   Ponto.errar(), 
									   Ponto.errar(), 
									   jogador.getPerfil().getQualidade(), 
									   timeAdversario.getGoleiro().getPerfil().getQualidade() );
				
				//avisa para os times que foi Gol
				avisaGol( time.getTorcida(), timeAdversario.getTorcida(), foiGol );
				
				//atualiza o placar
				atualizaPlacar();	
			}
		}
	}
	
	private void avisaGol(Torcida torcida, Torcida torcidaAdversaria, boolean isGol) {
		// TODO Auto-generated method stub
		
	}
	private void atualizaPlacar() {
		// TODO Auto-generated method stub
		
	}
	
	// verifica se foi gol
	public boolean isGol(Ponto chuteJogador, 
						 Ponto defesaGoleiro, 
						 boolean jogadorErrou, 
						 boolean goleiroErrou,
						 float qualidadeJogador,
						 float qualidadeGoleiro){
		
		// se o chute do jogador não for no mesmo local que a defesa do goleiro
		if ( chuteJogador != defesaGoleiro){
			// retorna que foi gol se o jogador não errou
			return !jogadorErrou;
			
		}else{ 
			// se o chute foi no mesmo lugar da defesa
			// se o goleiro não errou e o jogador não errou
			if(!goleiroErrou && !jogadorErrou){
				// se a qualidade do Goleiro for maior que a do jogador
				if (qualidadeGoleiro > qualidadeJogador){
					// não foi gol
					return false;
				}
				// se o caso acima for falso, então foi gol
				return true;
				
			}else if( !goleiroErrou && jogadorErrou){
				// se goleiro não errou e jogador errou, não foi gol
				return false;
				
			}else if( goleiroErrou && !jogadorErrou){
				// se goleiro errou e jogador não errou foi gol
				return true;
			}else{
				// se goleiro errou e jogador errou, não foi gol
				return false;
			}
		}
	}
	
	private void pressaoTorcida(Jogador jogador, Time timeAdversario){
		float apoioTorcida = jogador.getTime().getTorcida().aplaudir(); 
		float vaiaAdversario = timeAdversario.getTorcida().vaiar();
		jogador.getPerfil().sofrePressao(vaiaAdversario, apoioTorcida); 
		jogador.getPerfil().medeQualidade();
	}
	
	private Ponto selecionaPonto() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private Time retornaTimeAdversario(Time time){
		// se o time que for passado como parametro for o time do jogador
		if (time == this.timesDaPartida[TIME_JOGADOR]){
			// então time adversario é o da maquina
			return this.timesDaPartida[TIME_MAQUINA];
		}
		// se não time adversario é o do jogador
		return this.timesDaPartida[TIME_JOGADOR];
	}
}
