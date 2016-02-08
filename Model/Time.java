package model;

import java.util.ArrayList;
import java.util.List;

public class Time {
	
//	private final int TAMANHO_TIME = 11;
//	private final int TAMANHO_EQUIPE = 6;
	
	private String nome;
	private List<Jogador> jogadores = new ArrayList<>();
	private List<Jogador> batedores = new ArrayList<>();
	private Jogador goleiro;
	private Torcida torcida;
	private int gols;
	
	public Time(String nome, List<Jogador> jogadores){
		this.torcida = new Torcida();
		this.nome = nome;
		this.jogadores = jogadores;
	}
	
	public List<Jogador> getJogadores() {
		return jogadores;
	}
	public void setJogadores(List<Jogador> jogadores) {
		this.jogadores = jogadores;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Torcida getTorcida() {
		return torcida;
	}
	public void setTorcida(Torcida torcida) {
		this.torcida = torcida;
	}

	public Jogador selecionaBatedor(int numeroCobranca) {
		return batedores.get(numeroCobranca);
	}
	
	public void marcaGol(){
		this.gols++;
	}
	public int getGols(){
		return this.gols;
	}

	public Jogador getGoleiro() {
		return goleiro;
	}

	public void setGoleiro(Jogador goleiro) {
		this.goleiro = goleiro;
	}

}
