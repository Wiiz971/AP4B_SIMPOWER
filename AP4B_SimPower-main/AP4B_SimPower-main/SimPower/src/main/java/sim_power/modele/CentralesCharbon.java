package sim_power.modele;

/**
 * Classe représentant une centrale : centrale à charbon.
 */
public class CentralesCharbon extends Centrale {

    /**
     * Consommation en charbon.
     */
    private int consommationCharbon;

    /**
     * Constructeur spécifique
     * @param emplacement emplacement de la centrale
     * @param zone Zone de jeu
     */
    public CentralesCharbon(Case emplacement, ZoneDeJeu zone) {
        super(10, 1000, 10, emplacement, 75, 3,3, zone);
        consommationCharbon = 40;
    }


    /**
     * Renvoie la quantité d'énergie produite et consomme
     * du charbon.
     *
     * @return renvoie la quantité d'énergie produite.
     */
    @Override
    public float getProductionEffective() {
        System.out.println("CONSOMMATION");
        int stockeCharbon = this.zoneDeJeu.getQteTotaleCharbon();
        if (stockeCharbon < this.consommationCharbon)
            return 0;
        else {
            zoneDeJeu.consommerCharbon(this.consommationCharbon);
            return this.capaciteProduction;
        }
    }

    /**
     * @return renvoie la quantité d'énergie produite.
     */
    public float getProductionEffectivedDescription() {
        int stockeCharbon = this.zoneDeJeu.getQteTotaleCharbon();
        if (stockeCharbon < this.consommationCharbon)
            return 0;
        else {
            return this.capaciteProduction;
        }
    }

    /**
     * Retourne la couleur de fond en css correspondant
     * à l'apparence de la classe
     *
     * @return la couleur de fond
     */
    @Override
    public String getStyle() {
        return "-fx-background-color: #AA00FF;";
    }

    /**
     * @return Le nom correspondant à la classe.
     */
    @Override
    public String getName() {
        return "Centrale \u00e0 charbon";
    }

    /**
     * @return une description des éléments de la case.
     */
    @Override
    public String getDescription() {
        String description = super.getDescription()+
                "Co\u00fbt de fonctionnement: "+coutFonctionnement+"$"
                +"\nCapacit\u00e9 de production: "+capaciteProduction+"MW"
                +"\nProduction effective: "+getProductionEffectivedDescription()+"MW";
        return description;
    }
}