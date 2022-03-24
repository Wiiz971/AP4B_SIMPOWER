package sim_power.modele;

/**
 * Classe représentant une centrale : Eolienne
 */
public class Eoliennes extends Centrale {

    /**
     * Constructeur specifique
     * @param emplacement Référence vers la case sur laquelle la construction est construite.
     * @param zone Zone de jeu
     */
    public Eoliennes(Case emplacement, ZoneDeJeu zone) {
        super(10, 1000, 10, emplacement, 50, 3,-1, zone);
    }
    /**
     * @return renvoie la quantité d'énergie produite.
     */
    @Override
    public float getProductionEffective() {
        if (emplacement.estInondee())
            return 0;
        if (emplacement.getVent() < 30 && emplacement.getVent() > 80)
            return 0;
        else
            return capaciteProduction;
    }

    /**
     * Retourne la couleur de fond en css correspondant
     * à l'apparence de la classe
     *
     * @return la couleur de fond
     */
    @Override
    public String getStyle() {
        return "-fx-background-color: #FFFF00;";
    }

    /**
     * @return Le nom correspondant à la classe.
     */
    @Override
    public String getName() {
        return "Champs d\u0027\u00e9oliennes";
    }
}