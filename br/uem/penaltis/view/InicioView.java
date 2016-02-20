package br.uem.penaltis.view;

import java.util.ArrayList;
import java.util.List;

import br.uem.penaltis.controller.PartidaController;
import br.uem.penaltis.model.Jogador;
import br.uem.penaltis.model.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InicioView {
	
	private List<Time> timesParaSelecao  = new ArrayList<Time>(); 
	PartidaController partidaController  = new PartidaController();
	private List<Time> timesSelecionados = new ArrayList<Time>(); 

    @FXML
    private ListView<String> lvJogadores;

    @FXML
    private ListView<String> lvTime;

    @FXML
    private Label lbTime;

    @FXML
    private Label lbInformacoes;
    
    @FXML
    private Button btCancelar;
    
    @FXML
    private Button btAvancar;
    
    @FXML
    private ImageView ivFlexa;

    @FXML
    void btCancelarAction(Event event) {
    	if( timesSelecionados.isEmpty()){
    		Stage stage = (Stage) btAvancar.getScene().getWindow();
    		stage.close();
    	}else{
    		lbInformacoes.setTextFill(Color.web("#000000"));
    		lbInformacoes.setText("Selecione seu time");
    		btCancelar.setText("Fechar");
    		timesSelecionados.clear();
    	}
    }

    @FXML
    void btAvancarAction(Event event) {
    	int timeSelecionado = lvTime.getSelectionModel().getSelectedIndex();
    	if( timeSelecionado != -1){
    		timesSelecionados.add(timesParaSelecao.get(timeSelecionado));
    	}else{
    		return;
    	}
    	if(timesSelecionados.size() == 1){
    		lbInformacoes.setText("Selecione o time adversario");
    		btCancelar.setText("Voltar");
    	}else if(timesSelecionados.size() == 2){
    		if (timesSelecionados.get(1).equals(timesSelecionados.get(0))){
    			timesSelecionados.remove(timesSelecionados.get(1));
    			lbInformacoes.setText("Adversario deve ser um time\n diferente!");
    			lbInformacoes.setTextFill(Color.web("#FF0000"));
    			return;
    		}
    		partidaController.iniciaPartida(timesSelecionados.get(0), timesSelecionados.get(1));
    		Stage stage = (Stage) btAvancar.getScene().getWindow();
    		stage.close();
    	}
    }
    
    @FXML
    void initialize() {
        inicializaTimes();
    }
    
    @FXML
    void lvMouseClicked(Event event) {
    	int indexSelecionado = lvTime.getSelectionModel().getSelectedIndex();
    	mostraJogadores( indexSelecionado );
    }
    
    private void mostraJogadores(int indexSelecionado) {
    	ObservableList<String> listaJogadores = FXCollections.observableArrayList();
    	for (Jogador j : timesParaSelecao.get(indexSelecionado).getJogadores()){
    		listaJogadores.add(j.getNome());
    	}
    	lvJogadores.setItems(listaJogadores);
		
	}
    
    private void inicializaTimes(){
    	ObservableList<String> listaTimes = FXCollections.observableArrayList();
    	timesParaSelecao = PartidaController.PegaTimes();
    	for (Time t : timesParaSelecao){
    		listaTimes.add(t.getNome());
    	}
    	lvTime.setItems(listaTimes);
    	
    }
    
}
