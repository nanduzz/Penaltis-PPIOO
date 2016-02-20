package view.extensoes;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ButtonComValor extends Button {

	private int valor;

	public ButtonComValor(int valor, String texto, ImageView imageView) {
		super(texto, imageView);
		this.valor = valor;
	}
	
	public ButtonComValor(int valor, String texto) {
		super(texto);
		this.valor = valor;
	}
	
	
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
}
