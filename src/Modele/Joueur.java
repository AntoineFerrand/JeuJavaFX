package Modele;

import Vue.VueClasses.JoueurView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;

/**
 * Définit le joueur, son numéro et gère son nombre de morts ses mouvements.
 * Malheureusement les mouvements ne sont pas traitées directement dans Joueur mais dans FenetreJeu par manque de temps.
 */
public class Joueur{
    public int getNum() {
        return num;
    }

    private int num;
    private IntegerProperty score = new SimpleIntegerProperty();
    private int nbMorts=0;
    private boolean canJump = true;
    private KeyCode haut;
    private KeyCode gauche = KeyCode.UP;
    private KeyCode droite = KeyCode.UP;
    private KeyCode bas = KeyCode.UP;

    private Rectangle position = new Rectangle();

    private long velocityX;
    private long velocityY;

    public Joueur(KeyCode Haut, KeyCode Gauche, KeyCode Droite, KeyCode Bas, int Num) throws FileNotFoundException {
        this.haut= Haut;
        this.gauche= Gauche;
        this.droite= Droite;
        this.bas= Bas;
        this.num= Num;
        position.setX(30);
        position.setY(30);
        position.setHeight(30);
        position.setWidth(30);
    }


    public KeyCode getHaut() { return haut; }

    public void setHaut(KeyCode haut) {
        this.haut = haut;
    }

    public KeyCode getGauche() {
        return gauche;
    }

    public void setGauche(KeyCode gauche) {
        this.gauche = gauche;
    }

    public KeyCode getDroite() {
        return droite;
    }

    public void setDroite(KeyCode droite) {
        this.droite = droite;
    }

    public KeyCode getBas() {
        return bas;
    }

    public void setBas(KeyCode bas) {
        this.bas = bas;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public IntegerProperty getScore() {
        return score;
    }

    public void setScore(int score) { this.score.setValue(score); }

    public int getNbMorts() {
        return this.nbMorts;
    }

    public void setNbMorts(int nbMorts) {
        this.nbMorts = nbMorts;
    }

    public final IntegerProperty scoreProperty() {
        return score;
    }

    public void augmenterScore(int points)
    {
        this.score.setValue(score.getValue() + points);
    }


    public void meurt()
    {
        this.nbMorts = this.nbMorts+1;
    }
/*
    public void refresh(double dt){
        this.position.setX(this.position.getX()+this.velocityX*dt);
        this.position.setY(this.position.getY()+this.velocityY*dt);
        this.velocityX=0;
        this.velocityY=0;
    }


    public void moveY(long velocityY){ this.velocityY = velocityY; }

    public void moveX(long velocityX){ this.velocityX = velocityX; }



    public void jumpPlayer() {  //Quand le joueur appuie sur sauter, la vitesse augmente de 30 vers le haut
        if (this.isCanJump()) {
                moveY(-30);
                this.setCanJump(false);
        }
    }

    public void chute(JoueurView j){  //Quand le joueur appuie sur chuter, la vitesse augmente de 30 vers le haut
        if(isCanJump()){
            moveY(2);
        }
    }*/

    public void mortJoueur(JoueurView j, Node jSprite){
        j.modele.meurt();
        jSprite.setTranslateX(0);
        jSprite.setTranslateY(600);

    }

}
