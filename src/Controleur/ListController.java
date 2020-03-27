package Controleur;

import Modele.Persistance;
import Modele.Score;
import Modele.ScoreFinDePartie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.scene.control.ListView;

/**
 * Gère la liste des scores.
 */
public class ListController implements Initializable{

    @FXML
    private ListView<Score> listView;

    private ObservableList<Score> data = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {

        Persistance p = new Persistance();
        p.charger(data);
        p.sauvegarder(data);

        Collections.sort(data);
        if (data.size()>10){
            while (data.size()>10)
            {
                data.remove(data.size()-1);
            }
        }

        listView.setItems(data);
    }



}
