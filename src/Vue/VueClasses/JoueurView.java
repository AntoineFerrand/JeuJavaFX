package Vue.VueClasses;

import Modele.Joueur;
import Modele.Entite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * JoueurView permet de paramétrer l'image d'un joueur en fonction de son numéro numJoueur.
 */
public class JoueurView extends Entite {

    private int numJoueur;
    public Joueur modele;


    public JoueurView(int num, int x, int y) throws FileNotFoundException {
        this.numJoueur=num;
        if(num==1) {
            modele = new Joueur(KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.DOWN, num);
            this.setSprite(new ImageView(new Image(new FileInputStream("resources/Images/perso.png"))));
        }
        else{
            modele = new Joueur(KeyCode.Z, KeyCode.Q, KeyCode.D, KeyCode.S, num);
            this.setSprite(new ImageView(new Image(new FileInputStream("resources/Images/perso2.png"))));
        }
        this.getSprite().setFitHeight(40);
        this.getSprite().setFitWidth(40);
        this.getSprite().setTranslateX(x);
        this.getSprite().setTranslateY(y);
        this.getProperties().put("alive",true);
    }


    public int getNumJoueur() {
        return numJoueur;
    }
}
