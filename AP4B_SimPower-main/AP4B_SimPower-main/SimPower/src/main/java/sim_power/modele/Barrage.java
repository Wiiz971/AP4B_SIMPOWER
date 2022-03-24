package sim_power.modele;

/**
 * Classe représentant une centrale : barrage.
 */
public class Barrage extends Centrale {

    /**
     * Vrai si la centrale produit de l'énergie.
     */
    private boolean productionEnCours;

    /**
     * Constructeur de la classe.
     *
     * @param emplacement emplacement de la centrale
     * @param zone référence vers la zone de jeu.
     */
    public Barrage(Case emplacement, ZoneDeJeu zone) {
        super(10, 1000, 10, emplacement, 50, 1,2, zone);
        this.productionEnCours = true;
    }
    /**
     * Inonde les cases près du barrage.
     * */
    public void inonderZone() {
        this.calculerInondation(true);
    }

    /**
     * Annule l'inondation des cases près du barrage.
     */
    public void annulerIondation() {
        this.calculerInondation(false);
    }

    /**
     * @return une description des éléments de la case.
     */
    @Override
    public String getDescription() {
        String description = "Co\u00fbt de fonctionnement: "+coutFonctionnement+"$"
                +"\nCapacit\u00e9 de production: "+capaciteProduction+"MW";
        if (!productionEnCours)
            description += "\nProduction effective: 0MW";
        else
            description += "\nProduction effective: "+capaciteProduction+"MW";
        return description;
    }

    /**
     * Retourne la couleur de fond en css correspondant
     * à l'apparence de la classe
     *
     * @return la couleur de fond
     */
    @Override
    public String getStyle() {
        return "-fx-background-color: #00EAFF;";
    }

    /**
     * @return Le nom correspondant à la classe.
     */
    @Override
    public String getName() {
        return "Barrage";
    }

    /**
     * Inonde ou annule l'inondation de la zone autour du barrage.
     *
     * @param bool si vrai alors inondde la zone, sinon annule l'inondation
     */
    private void calculerInondation(boolean bool) {
        int coordX = emplacement.getCoordX();
        int coordY = emplacement.getCoordY();

        int deplacementX = 1;
        int deplacementY = 1;
        if (coordX == zoneDeJeu.getTaille())
            deplacementX = -1;
        if (coordY == zoneDeJeu.getTaille())
            deplacementY = -1;

        this.zoneDeJeu.getCase(coordX+deplacementX, coordY+deplacementY).setInondee(bool);
        this.zoneDeJeu.getCase(coordX, coordY+deplacementY).setInondee(bool);
        this.zoneDeJeu.getCase(coordX+deplacementX, coordY).setInondee(bool);
    }
}