package br.uem.penaltis.model;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Torcida implements Observer{

	private final int TAMANHO_TORCIDA = 100;
	public static final String GOL = "Gol";
	public static final String ERRO = "ERRO";
	
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
		executaSom("resources/Cheering_3.wav");
	}
	public void lamentar(){
		executaSom("resources/Crowd_Boo_4.wav");
	}
	
	@Override
	public void update(String msg) {
		if ( msg.equals(GOL))
			comemorar();
		else if (msg.equals(ERRO))
			lamentar();

	}
	
	public void executaSom( String arquivo){
		new Thread(){
			public void run(){
				Clip clip;
				InputStream is;
				try {

					is = getClass().getResourceAsStream(arquivo);
					AudioInputStream audioIn;
					audioIn = AudioSystem.getAudioInputStream(is);
					clip = AudioSystem.getClip();

					clip.open(audioIn);
					clip.start();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

}
