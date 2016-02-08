package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import model.Jogador;
import model.Time;

public class PartidaView{

    @FXML
    private Label lbPlacar;

    @FXML
    private Label lbTime2;

    @FXML
    private Label lbTime1;

    @FXML
    private ImageView ivJogo;

    @FXML
    private ListView<Jogador> lvTime2;

    @FXML
    private ListView<Jogador> lvTime1;
    
    public PartidaView(){

    }
    
    private void preencheListViews(Time time, ObservableList<Jogador> observableList){

    }

}
