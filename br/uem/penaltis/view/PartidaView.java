package br.uem.penaltis.view;

import java.util.ArrayList;
import java.util.List;

import br.uem.penaltis.controller.PartidaController;
import br.uem.penaltis.model.Jogador;
import br.uem.penaltis.model.Ponto;
import br.uem.penaltis.model.Time;
import br.uem.penaltis.model.Util;
import br.uem.penaltis.view.extensoes.ButtonComValor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PartidaView{

	private Time timeJogador;
	private Time timeAdversario;
	private List<ButtonComValor> botoes = new ArrayList<ButtonComValor>();
	private PartidaController partidaController;
	
    @FXML
    private Label lbPlacar;

    @FXML
    private Label lbTime2;

    @FXML
    private Label lbTime1;

    @FXML
    private ImageView ivJogo;

    @FXML
    private ListView<String> lvTime2;
    
    @FXML
    private HBox hBoxCima;

    @FXML
    private HBox hBoxBaixo;
    
    @FXML
    private ListView<String> lvTime1;
    
    ObservableList<String> listaJogadores; 
    Image bola;
    Image luva;
    
    public PartidaView(Time timeJogador, Time timeAdversario, PartidaController partidaController){
    	this.timeJogador    = timeJogador;
    	this.timeAdversario = timeAdversario;
    	this.partidaController = partidaController;
		this.bola = new Image(getClass().getResourceAsStream("resources/Bola-de-Futebol-mini.png"));
		this.luva = new Image(getClass().getResourceAsStream("resources/LuvaMini.png"));
    }
    
    @FXML
    void initialize(){
		lbTime1.setText(timeJogador.getNome());
		lbTime2.setText(timeAdversario.getNome());
		preencheListViews(timeJogador, lvTime1);
		preencheListViews(timeAdversario, lvTime2);
		inicializaBotoesChute();
		
    }
       
 	private void inicializaBotoesChute() {
		for( int i = 0; i < Ponto.values().length; i++){
			ButtonComValor b = new ButtonComValor(i, "");
			b.setGraphic( new ImageView( this.retornaImagemDoBotao() ) );
			b.setOnAction( new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					ObservableList<String> listaJogadores;
					if(partidaController.isJogadorChutando()){
						int indexJogador = lvTime1.getSelectionModel().getSelectedIndex();
						if (indexJogador == 0){
							return;
						}
						partidaController.chuteJogador(b.getValor(), indexJogador);
						listaJogadores = lvTime1.getItems();
						listaJogadores.remove(indexJogador);
						lvTime1.getSelectionModel().select(0);				
						
					}else{
						int indexBatedor = Util.random( lvTime2.getItems().size() -1);
						partidaController.defesaGoleiro(b.getValor(), indexBatedor);
						listaJogadores = lvTime2.getItems();
						listaJogadores.remove(indexBatedor);
					}
					if( partidaController.isFimJogo() ){
						fechaJanela();
					}else if(listaJogadores.size() == 1){
						voltaJogadores();
					}
					
					atualizaPlacar();
					partidaController.setJogadorChutando(!partidaController.isJogadorChutando());
					trocaImagemBotoes( retornaImagemDoBotao() );
					
				}

				private void trocaImagemBotoes(Image imagem) {
					for( ButtonComValor b : botoes){
						b.setGraphic(new ImageView(imagem));
					}
				}
			});
			
			botoes.add(b);
			if (i < 3){
				hBoxCima.getChildren().add(b);
			}else{
				hBoxBaixo.getChildren().add(b);
			}
		}
		
	}

	private void preencheListViews(Time time, ListView<String> listView){
    	listaJogadores = FXCollections.observableArrayList();
    	for (Jogador j: time.getJogadores()){
    		listaJogadores.add(j.getNome());
    	}
    	listView.setItems(listaJogadores);
    }
	
	private Image retornaImagemDoBotao(){
		if (this.partidaController.isJogadorChutando()){
			return this.bola;
		}
		return this.luva;
	}
	
	private void atualizaPlacar(){
		this.lbPlacar.setText( this.partidaController.getTimesDaPartida()[0].getGols() + " X " + 
							   this.partidaController.getTimesDaPartida()[1].getGols()			);
	}
	
	private void fechaJanela(){
		Stage stage = (Stage) lvTime1.getScene().getWindow();
		stage.close();
	}
	
	private void voltaJogadores(){
		if (partidaController.isJogadorChutando()){
			Time time = partidaController.getTimesDaPartida()[PartidaController.TIME_JOGADOR];
			preencheListViews(time, lvTime1);
		}else{
			Time time = partidaController.getTimesDaPartida()[PartidaController.TIME_MAQUINA];
			preencheListViews(time, lvTime2);
		}
	}

}
