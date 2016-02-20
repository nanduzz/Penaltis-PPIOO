package br.uem.penaltis.model;

public class Batedor extends Jogador{
	
	public Batedor(String nome) {
		super(nome);
	}

	public boolean chutar(){
		return Ponto.errar();
	}
	
}
