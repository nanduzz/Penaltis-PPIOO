package br.uem.penaltis.model;

public class Torcedor {
	
	private Torcida torcida;
	
	public Torcida getTorcida() {
		return torcida;
	}

	public void setTorcida(Torcida torcida) {
		this.torcida = torcida;
	}

	public int apoiar(){
		return Util.random(Util.POTENCIA_MAX);
	}
	
	public int xingar(){
		return Util.random(Util.POTENCIA_MAX);
	}

}
