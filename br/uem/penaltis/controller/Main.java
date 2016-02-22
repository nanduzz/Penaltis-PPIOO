package br.uem.penaltis.controller;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		JOptionPane.showMessageDialog(null, "Este jogo contem som, é aconcelhavel que não\n"
				+ "utilize volume muito alto");
		try{
			Parent root = FXMLLoader.load(getClass().getResource("../view/inicioView.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public static void main(String[] args) {
		launch(args);
	}

}
