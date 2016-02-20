package br.uem.penaltis.model;

import java.util.Random;

public class Util {

	public static final int POTENCIA_MAX = 10;

	public static int random(int max){
		Random rand = new Random();
		if (max > 0)
			return rand.nextInt(max) + 1;
		else
			return rand.nextInt(POTENCIA_MAX) + 1;
	}
	
	public static boolean randomBoolean(){
		return Math.random() < 0.5;
	}
	
	
}
