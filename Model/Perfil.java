package model;

public class Perfil {

	private int qualidade;
	private int confianca;
	
	public Perfil(int qualidade, int confianca) {
		this.qualidade = qualidade;
		this.confianca = confianca;
	}
	public int getQualidade() {
		return qualidade;
	}
	public void setQualidade(int qualidade) {
		this.qualidade = qualidade;
	}
	public int getConfianca() {
		return confianca;
	}
	public void setConfianca(int confianca) {
		this.confianca = confianca;
	}
	
	public void sofrePressao(float vaias, float aplausos){
		float novaConfianca = this.confianca + aplausos - vaias;
		this.confianca = (int) novaConfianca ;
	}

	public void medeQualidade() {
		this.qualidade = (int) qualidade * ( confianca / 100);
	}
	
}
