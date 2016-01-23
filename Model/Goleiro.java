package Model;

public class Goleiro extends Jogador{

	public Goleiro(){
		int confianca = (int) Math.random() * 100;
		int qualidade = (int) Math.random() * 100;
		this.setPerfil(new Perfil(qualidade, confianca));
	}
	
	
	public void defender(Ponto ponto){
		
	}
	
}
