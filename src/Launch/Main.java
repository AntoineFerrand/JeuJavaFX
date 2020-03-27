package Launch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Controleur.JeuController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Main extends Application {

    private static HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private static Stage window;
    private static boolean dialogEvent = false, running = true;
    private static Scene scoreScene;
    private static ArrayList<Node> scores = new ArrayList<Node>();


    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setMaximized(true); //on peut mettre les fenêtres en taille max
        //window.setFullScreen(true); //ou mettre les fenêtres en fullscreen


        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
        Scene menuScene = new Scene(root);

        window.setTitle("Jeu");
        window.setScene(menuScene);
        window.show();


    }

    @FXML
    private void lancerJeu() {
        new JeuController(window);
    }

    @FXML
    private void quitterJeu() {
        System.exit(0);
    }

    @FXML
    public void score() throws IOException {
        Parent root3 = FXMLLoader.load(getClass().getResource("/fxml/scores.fxml"));
        scoreScene = new Scene(root3);
        window.setMaximized(false);
        window.setScene(scoreScene);
        window.setMaximized(true);
    }

    public static void main(String[] args) {
        launch(args);
    }

}