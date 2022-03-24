package sim_power.modele;

import sim_power.exception.ExceptionCaseHabitee;
import sim_power.exception.ExceptionCaseIncompatible;
import sim_power.exception.ExceptionCaseInondee;
import sim_power.exception.ExceptionCaseOccupee;

/**
 * Classe représentant une case de reserve de charbon sur le terrain
 */
public class CaseReserveCharbon extends Case {

    /**
     * Quantité de charbon.
     */
    private int qteCharbon;

    /**
     * Constructeur par défaut.
     *
     * @param coordX Coordonnée de la case en X
     * @param coordY Coordonnée de la case en Y
     */
    public CaseReserveCharbon(int coordX, int coordY) {
        super(coordX, coordY);
        this.qteCharbon = 1000;
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
            if (construction instanceof Barrage
                    || construction instanceof CentraleNucleaire)
                throw new ExceptionCaseIncompatible();
        }
        this.construction = construction;
    }

    /**
     * Récolte les ressources s'il y a un producteurs de
     * ressources sur la case..
     */
    @Override
    public int recolter() {
        int resultat = 0;
        if (this.construction != null && this.construction instanceof ProducteurRessources) {
            int quantite = ((ProducteurRessources) this.construction).recolter();
            if (qteCharbon > quantite)
                resultat = quantite;
            else if (qteCharbon > 0)
                resultat = qteCharbon;
        }
        qteCharbon -= resultat;
        return resultat;
    }

    /**
     * Retourne la couleur de fond en css correspondant
     * à l'apparence de la classe
     *
     * @return la couleur de fond
     */
    @Override
    public String getStyle() {
        return "-fx-background-color: #36375c;";
    }

    /**
     * @return les infomations sur la météo, dont la pollution.
     */
    @Override
    public String toStringMeteo() {
        return super.toStringMeteo()
                +"\nqte charbon: "+qteCharbon;
    }

    /**
     * @return Le nom correspondant à la classe.
     */
    @Override
    public String getName() {
        String name = "R\u00e9serve naturelle de charbon";
        if (this.construction != null)
            name += "\n"+this.construction.getName();
        return name;
    }

    /**
     * @return une description des éléments de la case.
     */
    @Override
    public String getDescription() {
        String description = super.getDescription()
                +"\nQuantit\u00e9 charbon: "+qteCharbon;
        return description;
    }
}

