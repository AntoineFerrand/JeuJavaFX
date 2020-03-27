package Vue.VueClasses;

import Modele.Entite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * PiqueView sert à paramétrer l'image d'une pique, sa hauteur, largeur et sa position x,y.
 */

public class PiqueView extends Entite {

    public PiqueView(int x, int y) throws FileNotFoundException {
        this.setSprite(new ImageView(new Image(new FileInputStream("resources/Images/Pique.PNG"))));
        this.getSprite().setFitHeight(60);
        this.getSprite().setFitWidth(60);
        this.getSprite().setTranslateX(x);
        this.getSprite().setTranslateY(y);
    }
}
