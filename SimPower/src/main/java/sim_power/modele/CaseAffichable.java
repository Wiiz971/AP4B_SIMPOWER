package sim_power.modele;

/**
 * Classe représentant une case affichable sur le terrain
 */
public interface CaseAffichable {

    /**
     * Retourne la couleur de fond en css correspondant
     * à l'apparence de la classe
     *
     * @return la couleur de fond
     */
    public String getStyle();

    /**
     * @return Le nom correspondant à la classe.
     */
    public String getName();

    /**
     * @return une description des éléments de la case.
     */
    public String getDescription();
}
