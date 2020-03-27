package Vue.VueClasses;

import Modele.Entite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * ArriveeView sert à paramétrer l'image d'une zone d'arrivée, sa hauteur, largeur et sa position x,y.
 */


public class ArriveeView extends Entite {

    public ArriveeView(int x, int y) throws FileNotFoundException {
        this.setSprite(new ImageView(new Image(new FileInputStream("resources/Images/fin.png"))));
        this.getSprite().setFitHeight(60);
        this.getSprite().setFitWidth(60);
        this.getSprite().setTranslateX(x);
        this.getSprite().setTranslateY(y);
    }
}

