package sim_power.modele;

/**
 * Classe représentant une centrale
 */
public abstract class Centrale extends Installation {

    /**
     * Capacité de production.
     */
    protected float capaciteProduction;

    /**
     * Rayon de répercussion.
     */
    protected int rayonRepercussions;

    /**
     * Intensité des répercussions.
     */
    protected int intensiteRepercussions;

    /**
     * Constructeur spécifique
     *
     * @param cFonc cout de fonctionnement
     * @param cCon cout de construction
     * @param tCon temps de construction
     * @param emplacement case où la centrale est presente
     * @param prod capacite de production
     * @param rayonReper rayon de repercussion
     * @param intReper intensité de repercussion
     * @param zone Zone de jeu
     */
    public Centrale(int cFonc, float cCon, int tCon, Case emplacement, float prod, int rayonReper, int intReper, ZoneDeJeu zone) {
        super(cFonc, cCon, tCon, emplacement, zone);
        capaciteProduction = prod;
        rayonRepercussions = rayonReper;
        intensiteRepercussions = intReper;
    }


    /**
     * Retourne sous forme d'une chaine de charactères les informations à afficher au joueur
     * lorsqu'il veut créer une instalaltion.
     * @return les informations sur l'installation avant sa création.
     */
    @Override
    public String getInfosCreation() {
        String infos = "Co\u00fbt de construction: "+coutConstruction+"\u0024"
                +"\nCo\u00fbt de fonctionnement: "+coutFonctionnement+"\u0024"
                +"\nTemps de construction: "+tempsDeConstruction
                +"\nProduction d'\u00e9lectricit\u00e9: "+capaciteProduction;
        return infos;
    }

    /**
     * @return une description des éléments de la case.
     */
    @Override
    public String getDescription() {
        String description = super.getDescription()+
                "Co\u00fbt de fonctionnement: "+coutFonctionnement+"\u0024"
                +"\nCapacit\u00e9 de production: "+capaciteProduction+"MW"
                +"\nProduction effective: "+(int)getProductionEffective()+"MW";
        return description;
    }

    /**
     * @return la quantité d'énergie produite.
     */
    public float getProductionEffective() {
        if (incident)
            return 0;

        if (this instanceof Eoliennes)
            return (int)(capaciteProduction * (getCase().getVent()) / 100);
        else if (this instanceof PanneauxSolaires)
            return (int)(capaciteProduction * (getCase().getEnsoleillement()) / 100);
        else
            return capaciteProduction;
    }

    /**
     * @return l'intensité des répercussions.
     */
    public int getIntensiteRepercussions() {  return intensiteRepercussions;  }
}