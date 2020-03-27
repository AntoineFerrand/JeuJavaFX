package Controleur;

import Launch.Main;
import Modele.LevelData;
import Modele.ScoreFinDePartie;
import Vue.FenetreJeu;
import Vue.VueClasses.JoueurView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * CollisionController gère les collisions entre le joueur et les différentes entités.
 */
public class CollisionController {

    private ArrayList<Node> coins = new ArrayList<Node>();
    private ArrayList<Node> piques = new ArrayList<Node>();
    private ArrayList<Node> flammes = new ArrayList<Node>();
    private ArrayList<Node> arrivees = new ArrayList<Node>();
    private Stage window;
    private Parent root2;

    public CollisionController(ArrayList<Node> coins, ArrayList<Node> piques, ArrayList<Node> flammes, ArrayList<Node> arrivees, Stage window) {
        this.coins = coins;
        this.piques = piques;
        this.flammes = flammes;
        this.arrivees = arrivees;
        this.window = window;
    }


    public void setCollisions(ArrayList<Node> coins,ArrayList<Node> piques,ArrayList<Node> flammes,ArrayList<Node> arrivees){
        setCoins(coins);
        setPiques(piques);
        setFlammes(flammes);
        setArrivees(arrivees);
    }


    public void setCoins(ArrayList<Node> coins) {
        this.coins = coins;
    }

    public void setPiques(ArrayList<Node> piques) {
        this.piques = piques;
    }

    public void setFlammes(ArrayList<Node> flammes) {
        this.flammes = flammes;
    }

    public void setArrivees(ArrayList<Node> arrivees) {
        this.arrivees = arrivees;
    }

    public boolean collisionCheck( FenetreJeu f, JoueurView j, JoueurView j2) {
        Node jSprite = j.getSprite();
        for (Node coin : coins) {
            if (jSprite.getBoundsInParent().intersects(coin.getBoundsInParent())) {
                coin.getProperties().put("alive", false);
                j.modele.augmenterScore(1);
            }
        }

        for (Node pique : piques){
            if(jSprite.getBoundsInParent().intersects(pique.getBoundsInParent())){
                j.modele.mortJoueur(j,jSprite);
                j.modele.setNbMorts(j.modele.getNbMorts()+1);
            }
        }

        for (Node flamme : flammes){
            if(jSprite.getBoundsInParent().intersects(flamme.getBoundsInParent())){
                j.modele.mortJoueur(j,jSprite);
                j.modele.setNbMorts(j.modele.getNbMorts()+1);
            }

        }

        for (Node arrivee : arrivees) {
            if (jSprite.getBoundsInParent().intersects(arrivee.getBoundsInParent())) {
                j.modele.augmenterScore(10);
                if (j.modele.getScore().get() > j2.modele.getScore().get()) {
                    ScoreFinDePartie.setNumGagnant(j.modele.getNum());
                    ScoreFinDePartie.setScoreGagnant((j.modele.getScore()).intValue());
                }
                else
                {
                    ScoreFinDePartie.setNumGagnant(j2.modele.getNum());
                    ScoreFinDePartie.setScoreGagnant((j2.modele.getScore()).intValue());
                }
                FinController.setWindow(window);

                if (LevelData.numLevel == 1) {//A remplacer par une liste pour éviter 10 milliard de if
                    f.creerNiveau(LevelData.LEVEL2);
                    LevelData.numLevel = 2;
                } else {
                    LevelData.numLevel=1;
                    f.creerNiveau(LevelData.LEVEL2);
                    try {
                        root2 = FXMLLoader.load(getClass().getResource("/fxml/victoire.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene finScene = new Scene(root2);
                    window.setMaximized(false);
                    window.setScene(finScene);
                    window.setMaximized(true);
                    PseudoController dialog = new PseudoController();
                    dialog.open();
                    return false;
                }

            }
        }

        return true;
    }

}
