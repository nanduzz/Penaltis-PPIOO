package model;

import java.util.ArrayList;
import java.util.List;

public class Time {
	
	private String nome;
	private List<Jogador> jogadores = new ArrayList<>();
	private List<Batedor> batedores = new ArrayList<>();
	private Goleiro goleiro;
	private Torcida torcida;
	private int gols;
	private int erros;
	
	public Time(String nome, List<Jogador> jogadores){
		this.torcida = new Torcida();
		this.nome = nome;
		this.jogadores = jogadores;
		this.goleiro = (Goleiro) jogadores.get(0);
		adicionaTimeAosJogadores();
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

	public Goleiro getGoleiro() {
		return this.goleiro;
	}

	public void setGoleiro(Goleiro goleiro) {
		this.goleiro = goleiro;
	}

	public void marcaErro() {
		this.erros++;
		
	}
	public int getErros(){
		return this.erros;
	}

	public void adicionaTimeAosJogadores(){
		for( Jogador j : this.jogadores){
			j.setTime(this);
		}
		this.goleiro.setTime(this);
	}
	
	public int getCobrancas(){
		return this.gols + this.erros;
		
	}
}
