package br.uem.penaltis.model;

public enum Ponto {

	ESQUERDACIMA, ESQUERDABAIXO, 
	DIREITACIMA, DIRETABAIXO,
	MEIOCIMA, MEIOBAIXO;
	
	public static boolean errar(){
		return Util.randomBoolean();
	}
	
}
