package br.uem.penaltis.model;

public class Torcida implements Observer{

	private final int TAMANHO_TORCIDA = 100;
	
	private Torcedor[] torcedores = new Torcedor[TAMANHO_TORCIDA];
	
	public Torcida(){
		for( int i = 0; i < torcedores.length; i++){
			torcedores[i] = new Torcedor();
		}
	}
	
	public Torcedor[] getTorcedores() {
		return torcedores;
	}
	public void setTorcedores(Torcedor[] torcedores) {
		this.torcedores = torcedores;
	}
	
	public int aplaudir(){
		int apoiando = 0;
		for(Torcedor t : torcedores){
			apoiando += t.apoiar();
		}
		return (int) ( apoiando / torcedores.length );
	}
	public int vaiar(){
		int xingando = 0;
		for (Torcedor t : torcedores){
			xingando += t.xingar();
		}
		return (int) ( xingando / torcedores.length );
	}
	public void comemorar(){
		
	}
	public void lamentar(){
		
	}
	
	@Override
	public void update() {
		System.out.println("GooooooooooooooL");
		
	}

}
