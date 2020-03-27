package Interfaces;

import Modele.Score;
import javafx.collections.ObservableList;

public interface PersistanceInterface {

    void charger(ObservableList<Score> data);
    void sauvegarder(ObservableList<Score> data);


}
