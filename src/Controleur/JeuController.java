package Controleur;

import Vue.FenetreJeu;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * JeuController gère le jeu. Il déclare une boucle qui fait appel à FenetreJeu.
 */
public class JeuController{

    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private boolean running = true;
    private long prev = 0;
    private double dt = 0;
    private long prevNanos;


    public JeuController(Stage window)
    {
        FenetreJeu fen = new FenetreJeu(window);
        Scene jeuScene = new Scene(fen.appRoot);

        jeuScene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        jeuScene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        window.setScene(jeuScene);
        window.show();
        prevNanos = 0;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - prev >= 200000)
                {
                    dt = (double)(now-prev)/10000000;
                }

                long deltaNanos = now - prevNanos;
                prevNanos = now;
                final double dt = deltaNanos / 1.0e9;
                if (running) {
                    running = fen.update(keys,dt);
                }
                prev = now;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        timer.start();
    }

}
