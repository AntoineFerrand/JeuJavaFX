package Modele;

import Controleur.PseudoController;
import Interfaces.PersistanceInterface;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Persistance implements PersistanceInterface {
    @Override
    public void charger(ObservableList<Score> data) {
        try {
            FileInputStream fis = new FileInputStream(new File("resources/XML/scores.xml"));
            boolean cont = true;
            try{
                XMLDecoder decoder = new XMLDecoder(fis);
                while(cont){
                    Score ss1 = (Score) decoder.readObject();
                    if(ss1 != null)
                        data.add(ss1);
                    else
                        cont = false;
                }
                decoder.close();
                fis.close();
            }catch(Exception e){
                //System.out.println(e.printStackTrace());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void sauvegarder(ObservableList<Score> data) {
        if (PseudoController.getPseudo()!=null)
        {
            Score s = new Score(ScoreFinDePartie.getScoreGagnant(), PseudoController.getPseudo());
            data.add(s);
            try {
                FileOutputStream fos = new FileOutputStream(new File("resources/XML/scores.xml"));
                XMLEncoder encoder = new XMLEncoder(fos);
                for(Score score : data){
                    encoder.writeObject(score);
                }
                encoder.close();
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
