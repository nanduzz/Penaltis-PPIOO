package view;

import java.util.ArrayList;
import java.util.List;

import controller.PartidaController;
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
import model.Jogador;
import model.Ponto;
import model.Time;
import model.Util;
import view.extensoes.ButtonComValor;

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
					if(partidaController.isJogadorChutando()){
						int indexJogador = lvTime1.getSelectionModel().getSelectedIndex();
						if (indexJogador == 0){
							return;
						}
						partidaController.chuteJogador(b.getValor(), indexJogador);
						ObservableList<String> listaJogadores = lvTime1.getItems();
						listaJogadores.remove(indexJogador);
						lvTime1.getSelectionModel().select(0);
						if(listaJogadores.isEmpty()){
							//TODO  acabou os jogadores para bater o penalti manulo
							System.out.println("acabou os jogadores");
						}
						
						
						
						partidaController.setJogadorChutando(false);
					}else{
						int indexBatedor = Util.random( lvTime2.getItems().size() -1);
						partidaController.defesaGoleiro(b.getValor(), indexBatedor);
						ObservableList<String> listaJogadores = lvTime2.getItems();
						listaJogadores.remove(indexBatedor);
						partidaController.setJogadorChutando(true);
					}
					trocaImagemBotoes( retornaImagemDoBotao() );
					atualizaPlacar();
					if( partidaController.ifFimJogo() ){
			    		Stage stage = (Stage) b.getScene().getWindow();
			    		stage.close();
					}
					
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

}
