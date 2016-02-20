package br.uem.penaltis.model;

public abstract class Jogador {

	private String nome;
	private Perfil perfil;
	private Time time;
	
	public Jogador(String nome){
		int confianca = Util.random(Util.POTENCIA_MAX);
		int qualidade = Util.random(Util.POTENCIA_MAX);
		this.setPerfil(new Perfil(qualidade, confianca));
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public Ponto direcionar(int posicao){
		return Ponto.values()[posicao];
	}
	
}
