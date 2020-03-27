package Vue.VueClasses;
import Modele.Entite;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * FlammeView sert à paramétrer l'image d'une flamme, sa hauteur, largeur et sa position x,y.
 */

public class FlammeView extends Entite {

    public FlammeView(int x, int y) throws FileNotFoundException {
        this.setSprite(new ImageView(new Image(new FileInputStream("resources/Images/flamme.png"))));
        this.getSprite().setFitHeight(60);
        this.getSprite().setFitWidth(40);
        this.getSprite().setTranslateX(x);
        this.getSprite().setTranslateY(y);
    }
}

