package sim_power.modele;

import sim_power.exception.ExceptionCaseHabitee;
import sim_power.exception.ExceptionCaseIncompatible;
import sim_power.exception.ExceptionCaseInondee;
import sim_power.exception.ExceptionCaseOccupee;

/**
 * Classe représentant une case d'un cours d'eau
 */
public class CaseCoursDEau extends Case {

    /**
     * Quantité de charbon.
     */
    private float debit;

    /**
     * Constructeur par défaut.
     *
     * @param coordX Coordonnée de la case en X
     * @param coordY Coordonnée de la case en Y
     */
    public CaseCoursDEau(int coordX, int coordY) {
        super(coordX, coordY);
        this.debit = 0;
    }

    /**
     * Constructeur spécifique.
     *
     * @param coordX Coordonnée de la case en X
     * @param coordY Coordonnée de la case en Y
     * @param debit debit du cours d'eau
     */
    public CaseCoursDEau(int coordX, int coordY, float debit) {
        super(coordX, coordY);
        this.debit = debit;
    }

    /**
     * Ajoute une construction sur la case
     *
     * @param construction construction à ajouter
     */
    public void construire(Construction construction) throws Exception {
        if (this.estInondee())
            throw new ExceptionCaseInondee();

        if (this.construction != null) {
            if (construction instanceof Habitation)
                throw new ExceptionCaseHabitee();
            else
                throw new ExceptionCaseOccupee();
        }
        else {
            if (construction instanceof MineCharbon)
                throw new ExceptionCaseIncompatible();
        }
        this.construction = construction;
    }

    /**
     * Retourne la couleur de fond en css correspondant
     * à l'apparence de la classe
     *
     * @return la couleur de fond
     */
    @Override
    public String getStyle() {
        return "-fx-background-color: #4aaef0;";
    }

    /**
     * @return Le nom correspondant à la classe.
     */
    @Override
    public String getName() {
        String name = "Cours d\u0027eau";
        if (this.construction != null)
            name += "\n"+this.construction.getName();
        return name;
    }
}
