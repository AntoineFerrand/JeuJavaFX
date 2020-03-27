package Controleur;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Gère le popup à la fin de la partie qui demande le pseudo du joueur qui a gagné.
 */
public class PseudoController extends Stage {

    private Text textQuestion = new Text();

    private static String pseudo;
    private TextField champsPseudo = new TextField();

    public PseudoController() {
        Button btnSubmit = new Button("Valider");
        btnSubmit.setOnAction(event -> {
            champsPseudo.setEditable(false);
            pseudo=champsPseudo.getText();
            if (getPseudo() == null) {
                setPseudo("Joueur Inconnu");
            }
            close();

        });

        VBox vbox = new VBox(30, textQuestion, champsPseudo, btnSubmit);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);

        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
    }

    public static String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        PseudoController.pseudo = pseudo;
    }

    public TextField getChampsPseudo() {
        return champsPseudo;
    }

    public void open() {
        textQuestion.setText("Quel est votre pseudo");
        champsPseudo.setText("");
        champsPseudo.setEditable(true);
        show();
    }
}
