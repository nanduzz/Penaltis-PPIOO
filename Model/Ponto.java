package Model;

public enum Ponto {

	ESQUERDACIMA, ESQUERDABAIXO, 
	DIREITACIMA, DIRETABAIXO,
	MEIOCIMA, MEIOBAIXO;
	
	public boolean errar(){
		return Util.randomBoolean();
	}
	
}
