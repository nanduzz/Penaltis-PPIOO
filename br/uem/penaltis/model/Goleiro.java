package br.uem.penaltis.model;

public class Goleiro extends Jogador{

	public Goleiro(String nome){
		super(nome);
	}
	
	public boolean defender(){
		return Ponto.errar();
	}
	
}
