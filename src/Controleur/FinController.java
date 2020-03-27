package Controleur;

import Modele.ScoreFinDePartie;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FinController gère l'écran de fin de jeu en fonction de qui a gagné.
 */
public class FinController implements Initializable{

    private static Stage window = new Stage();
    private Parent root2;


    @FXML
    private Label victory;

    public static void setWindow(Stage window) {
        FinController.window = window;
    }

    @FXML
    private void revenirMenu() {
        try {
            root2 = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene menuScene = new Scene(root2);
        window.setTitle("Jeu");
        window.setMaximized(false);
        window.setScene(menuScene);
        window.setMaximized(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StringProperty gagnant = new SimpleStringProperty();
        if (ScoreFinDePartie.getNumGagnant() == 1)
            gagnant.setValue("Joueur 1 : Super Meat Boy");
        else
            gagnant.setValue("Joueur 2 : Super Meat Girl");
        victory.textProperty().bind(gagnant);
    }
}
