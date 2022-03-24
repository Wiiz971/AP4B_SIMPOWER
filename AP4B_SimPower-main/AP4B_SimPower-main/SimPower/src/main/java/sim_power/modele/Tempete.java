package sim_power.modele;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe représentant un evenement tempete
 */
public class Tempete {
    /**
     * Rayon
     */
    private int rayon;

    /**
     * intensité
     */
    private float intensite;

    /**
     * Coordonnée de la case en X.
     */
    private int coordX;

    /**
     * Coordonnée de la case en Y.
     */
    private int coordY;

    /**
     * Coordonéne en X de la prochaine position.
     */
    private int targetX;

    /**
     * Coordonéne en Y de la prochaine position.
     */
    private int targetY;

    /**
     * Portée de la tempêt en X.
     */
    private int rangeX;

    /**
     * Portée de la tempêt en Y.
     */
    private int rangeY;

    /**
     * Référence vers la case
     */
    private Case ca;

    /**
     * Constructeur spécifique
     *
     * @param x Coordonnée de la case en X
     * @param y Coordonnée de la case en Y
     */
    Tempete(int x, int y){
        rayon = ThreadLocalRandom.current().nextInt(2, 7 + 1);
        intensite = 0;
        while (Math.abs(intensite) < 20) {
            intensite = ThreadLocalRandom.current().nextInt(-30, 40 + 1);
        }
        setRange(x, y);
        relocateInRange();
        setTargetInRange();
    }

    /**
     * Change les valeurs des coordonnées
     *
     * @param newX Nouvelle coordonnée de la case en X
     * @param newY Nouvelle coordonnée de la case en Y
     */
    public void relocate(int newX, int newY) {
        coordX = newX;
        coordY = newY;
    }

    /**
     * Change les valeurs des coordonnées dasn une portée precise
     */
    public void relocateInRange() {
        relocate(ThreadLocalRandom.current().nextInt(0, rangeX), ThreadLocalRandom.current().nextInt(0, rangeY));
    }

    /**
     * Cahnge la cible
     *
     * @param x Coordonnée de la case en X
     * @param y Coordonnée de la case en Y
     */
    public void setTarget(int x, int y) {
        targetX = x;
        targetY = y;
    }

    /**
     * Change la portée
     */
    public void setTargetInRange() {
        boolean loop = true;
        int newX = 0;
        int newY = 0;
        while (loop) {
            newX = ThreadLocalRandom.current().nextInt(0, rangeX);
            newY = ThreadLocalRandom.current().nextInt(0, rangeY);
            if (Math.abs(newX - coordX) + Math.abs(newY - coordY) > Math.min((rangeX+rangeY)/8,4)) {
                loop = false;
            }
        }
        setTarget(newX, newY);
    }

    /**
     * Change la portée
     *
     * @param x Coordonnée de la case en X
     * @param y Coordonnée de la case en Y
     */
    public void setRange(int x, int y) {
        rangeX = x;
        rangeY = y;
    }

    /**
     * @return vrai (true) si hors de range. Retourne faux (false) sinon
     */
    public boolean update() {
        // get new target if precedent target reached
        if (coordX == targetX && coordY == targetY)
            setTargetInRange();
        // move towards target

        int moves = ThreadLocalRandom.current().nextInt(0, 6);
        moves = moves > 3 ? 1 : moves;

        for (int i = 0 ; i < moves ; i++) {
            if (coordX == targetX || ThreadLocalRandom.current().nextInt(0, 2) == 0) {
                coordY += coordY < targetY ? 1 : -1;
            } else {
                coordX += coordX < targetX ? 1 : -1;
            }
        }

        // return true if outside range
        if (coordX >= rangeX || coordX < 0 || coordY >= rangeY || coordY < 0)
            return true;
        else
            return false;
    }

    /**
     * @return l'intensité
     */
    public float getIntensite() {
        return intensite;
    }

    /**
     * @return le rayon
     */
    public int getRayon() {
        return rayon;
    }

    /**
     * @return la coordonée en X
     */
    public int getCoordX() {
        return coordX;
    }

    /**
     * @return la coordonnée en Y
     */
    public int getCoordY() {
        return coordY;
    }

}
