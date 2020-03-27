package Vue;

import Controleur.CollisionController;
import Modele.LevelData;
import Vue.VueClasses.ArriveeView;
import Vue.VueClasses.FlammeView;
import Vue.VueClasses.JoueurView;
import Vue.VueClasses.PiqueView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * FenetreJeu gère l'affichage et la disposition des éléments d'un niveau.
 * Elle gère également les déplacements des joueurs qui n'ont pas pu être mis dans Joueur par manque de temps
 */


public class FenetreJeu extends BorderPane {


    private ArrayList<Node> platforms = new ArrayList<Node>();  //Pour les plateformes on crée une liste
    private ArrayList<Node> coins = new ArrayList<Node>();
    private ArrayList<Node> piques = new ArrayList<Node>();
    private ArrayList<Node> flammes = new ArrayList<Node>();
    private ArrayList<Node> arrivees = new ArrayList<Node>();

    public Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();

    private JoueurView j1;
    private Node j1Sprite;

    private JoueurView j2;
    private Node j2Sprite;

    private Point2D playerVelocity1 = new Point2D(0, 0);
    private Point2D playerVelocity2 = new Point2D(0, 0);

    private double dt;

    private int levelWidth;


    private CollisionController collisionController;


    public FenetreJeu(Stage window)
    {
        super();
        collisionController= new CollisionController(coins,piques,flammes,arrivees,window);
        Image ImageBackground = new Image("file:resources/Images/background.png");
        ImageView background = new ImageView(ImageBackground);

        levelWidth = LevelData.LEVEL1[0].length() * 60;


        try {
            j1 = new JoueurView(1,0,600);
            j1Sprite = j1.getSprite();
            j2 = new JoueurView(2,0,600);
            j2Sprite = j2.getSprite();
            creerNiveau(LevelData.LEVEL1);
            //j1Sprite = createEntity(0,600,40,40,Color.BLUE);
            j1Sprite.getProperties().put("alive", true);

            j1Sprite.translateXProperty().addListener((obs, old, newValue) -> {
                int offset = newValue.intValue();
                if (offset > 640 && offset < levelWidth - 640 && offset < j2Sprite.getTranslateX()) {
                    gameRoot.setLayoutX(-(offset - 640));
                }
                if(j1Sprite.getTranslateX()==0)
                    gameRoot.setLayoutX(0);
            });

            j2Sprite.translateXProperty().addListener((obs, old, newValue) -> {
                int offset2 = newValue.intValue();
                if (offset2 > 640 && offset2 < levelWidth - 640 && offset2 < j1Sprite.getTranslateX()) {
                    gameRoot.setLayoutX(-(offset2 - 640));
                }
                if(j2Sprite.getTranslateX()==0)
                    gameRoot.setLayoutX(0);
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        appRoot.getChildren().addAll(background, gameRoot);

    }

    /**
     * Crée un niveau en assignant un objet à chaque valeur présente dans le niveau défini dans LevelData.
     * @param LEVEL
     */
    public void creerNiveau (String[] LEVEL)
    {
        platforms.clear();
        coins.clear();
        piques.clear();
        flammes.clear();
        arrivees.clear();
        gameRoot.getChildren().clear();
        j1Sprite.setTranslateX(0);
        j1Sprite.setTranslateY(600);
        j2Sprite.setTranslateX(0);
        j2Sprite.setTranslateY(600);

        Text text1 = new Text();
        text1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,80));
        StringProperty slave1 = new SimpleStringProperty();
        Bindings.bindBidirectional(slave1, j1.modele.getScore(), new NumberStringConverter());
        text1.setText(String.valueOf(slave1.getValue()));
        text1.setX(800);
        text1.setY(100);
        text1.setFill(Color.BLUE);

        StringProperty slave2 = new SimpleStringProperty();
        Bindings.bindBidirectional(slave2, j2.modele.getScore(), new NumberStringConverter());
        Text text2 = new Text();
        text2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,80));
        text2.setText(String.valueOf(slave2.getValue()));
        text2.setX(1100);
        text2.setY(100);
        text2.setFill(Color.GREEN);

        j1.modele.scoreProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                text1.setText(slave1.getValue());
            }
        });

        j2.modele.scoreProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                text2.setText(slave2.getValue());
            }
        });


        gameRoot.getChildren().add(text1);
        gameRoot.getChildren().add(text2);
        gameRoot.getChildren().add(j1Sprite);
        gameRoot.getChildren().add(j2Sprite);
        for (int i = 0; i < LEVEL.length; i++) {
            String line = LEVEL[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Node platform = createEntity(j * 60, i * 60, 60, 60, Color.BROWN);//on crée les plateformes
                        platforms.add(platform); //on ajoute ensuite les plateformes à la liste.
                        break;
                    case '2':
                        Node coin = createEntity(j * 60, i * 60, 60, 60, Color.GOLD);
                        coins.add(coin);
                        break;
                    case '3':
                        try {
                            PiqueView p = new PiqueView(j * 60, i * 60);
                            gameRoot.getChildren().add(p.getSprite());
                            Node pique = p.getSprite();
                            piques.add(pique);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case '4':
                        try {
                            FlammeView f = new FlammeView(j * 60, i * 60);
                            gameRoot.getChildren().add(f.getSprite());
                            Node flamme = f.getSprite();
                            flammes.add(flamme);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case '5':
                        try {
                            ArriveeView a = new ArriveeView(j * 60, i * 60);
                            gameRoot.getChildren().add(a.getSprite());
                            Node arrivee = a.getSprite();
                            arrivees.add(arrivee);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                }
            }
        }
        collisionController.setCollisions(coins,piques,flammes,arrivees);
    }

    private Node createEntity(int x, int y, int w, int h, Color color) {

        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        entity.getProperties().put("alive", true);

        gameRoot.getChildren().add(entity);
        return entity;
    }


    public boolean update(HashMap<KeyCode, Boolean> keys,double Dt) {
        dt=Dt;
        if (isPressed(j1.modele.getHaut(), keys) && j1Sprite.getTranslateY() >= 5) {

            jumpPlayer(j1);
        }

        if (isPressed(j1.modele.getGauche(), keys) && j1Sprite.getTranslateX() >= 5) {
            movePlayerX(j1Sprite,-5);
        }

        if (isPressed(j1.modele.getDroite(), keys) && j1Sprite.getTranslateX() + 40 <= levelWidth - 5) {
            movePlayerX(j1Sprite,5);
        }

        if (isPressed(j1.modele.getBas(), keys)){
            chute(j1);
        }

        if (isPressed(j2.modele.getHaut(), keys) && j2Sprite.getTranslateY() >= 5) {
            jumpPlayer(j2);
        }

        if (isPressed(j2.modele.getGauche(), keys) && j2Sprite.getTranslateX() >= 5) {
            movePlayerX(j2Sprite,-5);
        }

        if (isPressed(j2.modele.getDroite(), keys) && j2Sprite.getTranslateX() + 40 <= levelWidth - 5) {
            movePlayerX(j2Sprite,5);
        }

        if (isPressed(j2.modele.getBas(), keys)){
            chute(j2);
        }

        for (Node platform : platforms) {
            if (playerVelocity1.getY()!=30 && j1Sprite.getBoundsInParent().intersects(platform.getBoundsInParent())) //On essaie d'empêcher le joueur de continuer son saut lorsqu'il touche une plateforme avec sa tête.
                playerVelocity1.add(0,0);
            if (playerVelocity2.getY()!=30 && j1Sprite.getBoundsInParent().intersects(platform.getBoundsInParent()))
                playerVelocity2.add(0,0);
        }

        if (playerVelocity1.getY() < 10) {   // Si le joueur a une vitesse inférieur à 10, sa vitesse tombe petit à petit jusqu'à 10 (vitesse de chute vers le bas maximal si le joueur n'appuie pas sur la flèche du bas)

            playerVelocity1 = playerVelocity1.add(0, 1);
        }
        if (playerVelocity2.getY() < 10) {
            playerVelocity2 = playerVelocity2.add(0, 1);
        }
        movePlayerY(j1, j1Sprite, (int)playerVelocity1.getY());
        movePlayerY(j2, j2Sprite, (int)playerVelocity2.getY());

        for (Iterator<Node> it = coins.iterator(); it.hasNext(); ) {
            Node coin = it.next();
            if (!(Boolean)coin.getProperties().get("alive")) {
                it.remove();
                gameRoot.getChildren().remove(coin);
            }
        }

        return collisionController.collisionCheck(this,j1, j2) && collisionController.collisionCheck(this,j2,j1) ;
    }

    private void movePlayerX(Node jSprite, int value) {
        boolean movingRight = value > 0;
        //value = (int) (value*dt);
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (jSprite.getBoundsInParent().intersects(platform.getBoundsInParent())) { //et on regarde quand le joueur entre en contact avec les plateformes
                    if (movingRight) {
                        if (jSprite.getTranslateX() + 40 == platform.getTranslateX()) {
                            return;
                        }
                    }

                    else {
                        if (jSprite.getTranslateX() == platform.getTranslateX() + 60) {
                            return;
                        }
                    }
                }
            }
            jSprite.setTranslateX(jSprite.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    private void movePlayerY(JoueurView j,Node jSprite, int value) {
        boolean movingDown = value > 0;  //regarde si PlayerVelocity est supérieur à 0 (ce qui le fait tomber ou bien inférieur à 0 (ce qui le fait sauter) )
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (jSprite.getBoundsInParent().intersects(platform.getBoundsInParent())) {  // Si le joueur touche un mur on arrête le saut
                    if (movingDown) {
                        if (jSprite.getTranslateY() + 40 == platform.getTranslateY()) { //le joueur est sur le sol, on peut à nouveau sauter
                            jSprite.setTranslateY(jSprite.getTranslateY() - 1);
                            j.modele.setCanJump(true);
                            return;
                        }
                    }
                    else {
                        if (jSprite.getTranslateY() == platform.getTranslateY() + 60) { // si le joueur tape un bloc au dessus il arrête de monter
                            return;
                        }
                    }
                }
            }
            //System.out.println(jSprite.getTranslateY() + (movingDown ? 1 : -1));
            jSprite.setTranslateY(jSprite.getTranslateY() + (movingDown ? 1 : -1));  // Si le joueur tombe on le déplace vers le bas, sinon vers le haut
        }
    }

    private void jumpPlayer(JoueurView j) {  //Quand le joueur appuie sur sauter, la vitesse augmente de 30 vers le haut
        if (j.modele.isCanJump()) {
            //System.out.println(dt);
            if (j.getNumJoueur()==1)
                playerVelocity1 = playerVelocity1.add(0, -30);
            if (j.getNumJoueur()==2)
                playerVelocity2 = playerVelocity2.add(0, -30);
            j.modele.setCanJump(false);
        }
    }

    private void chute(JoueurView j){  //Quand le joueur appuie sur chuter, la vitesse augmente de 30 vers le haut
        if(!j.modele.isCanJump()){
            if (j.getNumJoueur()==1)
                playerVelocity1=playerVelocity1.add(0,2);
            else
                playerVelocity2=playerVelocity2.add(0,2);
        }
    }

    private boolean isPressed(KeyCode key, HashMap<KeyCode, Boolean> keys) {
        return keys.getOrDefault(key, false);
    }


}
