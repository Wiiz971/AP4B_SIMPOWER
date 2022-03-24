package sim_power.modele;

/**
 * Classe représentant une centrale : centrale nucleaire.
 */
public class CentraleNucleaire extends Centrale {

    /**
     * constructeur spécifique
     * @param emplacement emplacement de la centrale
     * @param zone zeone de jeu
     */
    public CentraleNucleaire(Case emplacement, ZoneDeJeu zone) {
        super(10, 1000, 10, emplacement, 50, 5,1, zone);
    }
    /**
     * Retourne la couleur de fond en css correspondant
     * à l'apparence de la classe
     *
     * @return la couleur de fond
     */
    @Override
    public String getStyle() {
        return "-fx-background-color: #FF0000;";
    }

    /**
     * @return Le nom correspondant à la classe.
     */
    @Override
    public String getName() {
        return "Centrale nucl\u00eaaire";
    }
}