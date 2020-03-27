package Modele;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;

/**
 * Entite est une classe qui permet d'avoir un sprite (une image) à un Objet de type Parent
 */
public class Entite extends Parent {
    private ImageView sprite;

    public Entite(){

    }


    public ImageView getSprite() {
        return sprite;
    }

    public void setSprite(ImageView sprite) {
        this.sprite = sprite;
    }
}
