package Model;

public abstract class Jogador {

	private String Nome;
	private Perfil perfil;
	private Time time;
	
	public Jogador(){
		int confianca = Util.random(Util.POTENCIA_MAX);
		int qualidade = Util.random(Util.POTENCIA_MAX);
		this.setPerfil(new Perfil(qualidade, confianca));
	}
	
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
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
	public Ponto direcionar(){;
		return Ponto.DIREITACIMA;
	}
	
}
