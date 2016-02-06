package Controller;

import java.util.ArrayList;

import Model.Jogador;
import Model.Observer;
import Model.Parametros;
import Model.Ponto;
import Model.Time;
import Model.Torcida;
import Model.Util;

public class PartidaController {
	
	private final int TIME_JOGADOR = 0;
	private final int TIME_MAQUINA = 1;
	private final int NUMERO_COBRANCAS = 6;
	private ArrayList<Observer> torcedores = new ArrayList<Observer>();
	
	private Time[] timesDaPartida = new Time[2];

	public static void main(String[] args) {
		PartidaController partida = new PartidaController();
		
		partida.iniciaPartida("time1", "time2");
		
	}
	private void AdicionaTorcedoresAoObserver( Observer torcida){
		torcedores.add(torcida);
	}
	
	private void iniciaPartida(String nomeTimeJogador, String nomeTimeMaquina){
		this.timesDaPartida[TIME_JOGADOR] = new Time(nomeTimeJogador); // inicia time do jogador
		this.timesDaPartida[TIME_MAQUINA] = new Time(nomeTimeMaquina); // inicia time da Maquina
		AdicionaTorcedoresAoObserver( this.timesDaPartida[TIME_JOGADOR].getTorcida() ); // adiciona Torcedores do jogador ao observer
		AdicionaTorcedoresAoObserver( this.timesDaPartida[TIME_MAQUINA].getTorcida() ); // adiciona torcedores da maquina ao observer
		
		//diz o nome dos times
		System.out.println( this.timesDaPartida[TIME_JOGADOR].getNome() + " contra " +
							this.timesDaPartida[TIME_MAQUINA].getNome() );
		
		//define que o jogo tera inicio automatico
		Parametros.setInicioAltomatico(true);
		
		//decide quem come�a as cobran�as
		decideInicio();
		// inicia as cobran�as de penaltis
		iniciaCobrancas();
		System.out.println("time de inicio:" + timesDaPartida[0].getNome());
		for( Observer o : this.torcedores ){
			o.update();
		}
	}
	
	private void decideInicio(){
		if (!Parametros.isInicioAltomatico() )
			return;
		
		//gera um numero aleatorio at� 2
		int timeInicio = Util.random(2);
		
		// se o time que for come�ar o jogo n�o for o do jogador, � feita uma altera��o no array
		// para que o time da Maquina fique como primeira posi��o do array
		if (timeInicio != 1){
			Time timeAux = timesDaPartida[TIME_JOGADOR];
			this.timesDaPartida[TIME_JOGADOR] = timesDaPartida[TIME_MAQUINA];
			this.timesDaPartida[TIME_MAQUINA] = timeAux;
		}
	}
	
	private void iniciaCobrancas(){
		// para cada cobran�a at� o numero maximo de cobran�as fa�a:
		for( int i = 0; i < NUMERO_COBRANCAS; i++ ){
			
			//para cada time em cada time da partida fa�a:
			for(Time time : this.timesDaPartida){
				
				//declara Jogador
				Jogador jogador;
				
				//se parametro proximo da fila esta ativado
				if (Parametros.isChamaProximoFila()){
					// seleciona o proximo jogador
					jogador = time.selecionaBatedor(i);  
				}
				else{
					// se n�o abre tela de sel��o de jogador
					jogador = time.selecionaBatedor(i);  //TODO CRIAR TELA DE SELE��O DE JOGADORES
				}
				
				 //pega o time adversario
				Time timeAdversario = retornaTimeAdversario(jogador.getTime());
				
				// jogador sofre press�o da torcida
				pressaoTorcida(jogador, timeAdversario);  
				
				// Goleiro do time adversario sofre press�o da torcida
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
		
		// se o chute do jogador n�o for no mesmo local que a defesa do goleiro
		if ( chuteJogador != defesaGoleiro){
			// retorna que foi gol se o jogador n�o errou
			return !jogadorErrou;
			
		}else{ 
			// se o chute foi no mesmo lugar da defesa
			// se o goleiro n�o errou e o jogador n�o errou
			if(!goleiroErrou && !jogadorErrou){
				// se a qualidade do Goleiro for maior que a do jogador
				if (qualidadeGoleiro > qualidadeJogador){
					// n�o foi gol
					return false;
				}
				// se o caso acima for falso, ent�o foi gol
				return true;
				
			}else if( !goleiroErrou && jogadorErrou){
				// se goleiro n�o errou e jogador errou, n�o foi gol
				return false;
				
			}else if( goleiroErrou && !jogadorErrou){
				// se goleiro errou e jogador n�o errou foi gol
				return true;
			}else{
				// se goleiro errou e jogador errou, n�o foi gol
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
			// ent�o time adversario � o da maquina
			return this.timesDaPartida[TIME_MAQUINA];
		}
		// se n�o time adversario � o do jogador
		return this.timesDaPartida[TIME_JOGADOR];
	}

}
