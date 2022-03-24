package sim_power.modele;

/**
 * Classe représentant une habitation qui implemente la classe Construction
 */
public class Habitation extends Construction {

    /**
     * Constructeur spécifique
     * @param refCase Référence vers la case sur laquelle la construction est construite.
     */
    public Habitation(Case refCase) {
        super(0, 0, 0, refCase);
    }

    /**
     * Supprime l'habitation de la case.
     */
    public void supprimerHabitation() {
        try {
            emplacement.detruire();
        } catch (Exception e) {
        }
    }

    /**
     * Retourne la couleur de fond en css correspondant
     * à l'apparence de la classes
     *
     * @return la couleur de fond
     */
    @Override
    public String getStyle() {
        return "-fx-background-color: white;";
    }

    /**
     * @return Le nom correspondant à la classe.
     */
    @Override
    public String getName() {
        return "Zone d\u0027habitation";
    }

    /**
     * @return une description des éléments de la case.
     */
    @Override
    public String getDescription() {
        return "Satisfaction locale: " + (int)((getLocalSatisfaction() + 125) / 2.5) + "\u0025";
    }

    /**
     * @return la satisfaction locale de l'habitation de la case, dépendant du niveau de pollution.
     * Celle ci se mesure de -125 à 125.
     * Une pollution plus élevée a d'autant plus d'effets que son importance.
     * Une pollution faible a peu d'impact sur la satisfaction.
     * Le vent a aussi des effets mineurs sur la satisfaction.
     */
    public float getLocalSatisfaction() {
        float satisfaction = 250;
        float delta;

        float pollutionTolerance = emplacement.getPollution();

        // 50 0 100 0.5 150 1 200 1.5 250 2 300 2.5
        for (float scale = 250, mod = 2.5F; scale > 0 && mod > 0; scale -= 50, mod -= 0.5){
            if (pollutionTolerance >= scale) {
                satisfaction -= (pollutionTolerance - scale) * mod;
                pollutionTolerance = scale;
            }
        }
        if (emplacement.getVent() > 80) {
            satisfaction -= emplacement.getVent() + 80;
        }
        if (emplacement.getEnsoleillement() < 20)
            satisfaction -= (20 - emplacement.getEnsoleillement()) * 3;
        if (emplacement.estInondee())
            satisfaction -= 100;
        if (satisfaction < 0)
            satisfaction = 0;
        return satisfaction - 125;
    }
}
