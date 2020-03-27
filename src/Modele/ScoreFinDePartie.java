package Modele;

/**
 * ScoreFinDePartie stock le score final et le numéro du joueur qui finit le jeu en premier.
 */
public class ScoreFinDePartie {

    private static int numGagnant = 1;
    private static int scoreGagnant = 0;


    public static int getNumGagnant() {
        return numGagnant;
    }

    public static void setNumGagnant(int numGagnant) {
        ScoreFinDePartie.numGagnant = numGagnant;
    }

    public static int getScoreGagnant() {
        return scoreGagnant;
    }

    public static void setScoreGagnant(int scoreGagnant) {
        ScoreFinDePartie.scoreGagnant = scoreGagnant;
    }

}
