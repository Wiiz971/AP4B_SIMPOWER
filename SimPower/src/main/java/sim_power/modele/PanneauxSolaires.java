package sim_power.modele;

/**
 * Classe représentant une centrale : Panneaux Solaires
 */
public class PanneauxSolaires extends Centrale {

    /**
     * Constructeur specifique
     * @param emplacement Référence vers la case sur laquelle la construction est construite.
     */
    public PanneauxSolaires(Case emplacement, ZoneDeJeu zone) {
        super(10, 1000, 10, emplacement, 50, 3,0, zone);
    }

    /**
     * Retourne la couleur de fond en css correspondant
     * à l'apparence de la classe
     *
     * @return la couleur de fond
     */
    @Override
    public String getStyle() {
        return "-fx-background-color: #FF7F00;";
    }

    /**
     * @return Le nom correspondant à la classe.
     */
    @Override
    public String getName() {
        return "Champs de panneaux solaires";
    }
}