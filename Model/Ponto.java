package Model;

public enum Ponto {

	ESQUERDACIMA, ESQUERDABAIXO, 
	DIREITACIMA, DIRETABAIXO,
	MEIOCIMA, MEIOBAIXO;
	
	public static boolean errar(){
		return Util.randomBoolean();
	}
	
}
