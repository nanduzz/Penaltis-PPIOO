package Controller;

import java.util.ArrayList;

import Model.Jogador;
import Model.Observer;
import Model.Parametros;
import Model.Time;
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
		this.timesDaPartida[TIME_JOGADOR] = new Time(nomeTimeJogador);
		this.timesDaPartida[TIME_MAQUINA] = new Time(nomeTimeMaquina);
		AdicionaTorcedoresAoObserver( this.timesDaPartida[TIME_JOGADOR].getTorcida() );
		AdicionaTorcedoresAoObserver( this.timesDaPartida[TIME_MAQUINA].getTorcida() );
		
		System.out.println( this.timesDaPartida[TIME_JOGADOR].getNome() + " contra " +
							this.timesDaPartida[TIME_MAQUINA].getNome() );
		Parametros.setInicioAltomatico(true);
		
		decideInicio();
		iniciaCobrancas();
		System.out.println("time de inicio:" + timesDaPartida[0].getNome());
		for( Observer o : this.torcedores ){
			o.update();
		}
	}
	
	private void decideInicio(){
		if (!Parametros.isInicioAltomatico() )
			return;
		
		int timeInicio = Util.random(2);
		if (timeInicio != 1){
			Time timeAux = timesDaPartida[TIME_JOGADOR];
			this.timesDaPartida[TIME_JOGADOR] = timesDaPartida[TIME_MAQUINA];
			this.timesDaPartida[TIME_MAQUINA] = timeAux;
		}
	}
	
	private void iniciaCobrancas(){
		for( int i = 0; i < NUMERO_COBRANCAS; i++ ){
			for(Time time : this.timesDaPartida){
				if (Parametros.isChamaProximoFila()){
					Jogador jogador = time.selecionaBatedor(i);
					realizaCobranca(jogador);
				}
				else{
					//TODO Jogador jogador = AbreTelaSelecao();
				}
			}
		}
	}
	
	private void realizaCobranca(Jogador jogador){
		Time timeAdversario = retornaTimeAdversario(jogador.getTime());
		float apoioTorcida = jogador.getTime().getTorcida().aplaudir();
		float vaiaAdversario = timeAdversario.getTorcida().vaiar();
		jogador.getPerfil().sofrePressao(vaiaAdversario, apoioTorcida);
		jogador.getPerfil().medeQualidade();
		for( Observer o : this.torcedores ){
			o.update();
		}
	}
	
	private Time retornaTimeAdversario(Time time){
		if (time == this.timesDaPartida[TIME_JOGADOR]){
			return this.timesDaPartida[TIME_MAQUINA];
		}
		return this.timesDaPartida[TIME_JOGADOR];
	}

}
