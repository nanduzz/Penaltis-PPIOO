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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Jogador;
import model.Ponto;
import model.Time;
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
    
    public PartidaView(Time timeJogador, Time timeAdversario){
    	this.timeJogador    = timeJogador;
    	this.timeAdversario = timeAdversario;
    	partidaController = new PartidaController();
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
			ButtonComValor b = new ButtonComValor(i, "Chutar");
			b.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					if(partidaController.isJogadorChutando()){
						trocaTextoBotoes("Defender");
						partidaController.setJogadorChutando(false);
					}else{
						System.out.println("Defendeu em :" + b.getValor());
						trocaTextoBotoes("Chutar");
						partidaController.setJogadorChutando(true);
						
						
					}
					
				}

				private void trocaTextoBotoes(String texto) {
					for( ButtonComValor b : botoes){
						b.setText(texto);
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
    	ObservableList<String> listaJogadores = FXCollections.observableArrayList();
    	for (Jogador j: time.getJogadores()){
    		listaJogadores.add(j.getNome());
    	}
    	listView.setItems(listaJogadores);
    }

}
