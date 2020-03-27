package Modele;

/**
 * Score est un objet qui permet de stocker le score et de le rendere comparable pour le trier
 */
public class Score implements Comparable {

    private int score;
    private String nom;

    public Score()
    {

    }

    public Score (int score, String nom) {
        this.score = score;
        this.nom = nom;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "nom : " + nom +
                ", score : " + score;
    }


    @Override
    public int compareTo(Object o) {
        int cScore=((Score)o).getScore();
        /* For Ascending order*/
        return cScore - this.score;
    }
}
