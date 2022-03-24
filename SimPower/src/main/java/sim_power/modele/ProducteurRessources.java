package sim_power.modele;

/**
 * Classe représentant les producteurs de ressources
 */
public abstract class ProducteurRessources extends Installation {

    /**
     * Capacité de l'installation à collecter la ressource exploité.
     */
    protected int capaciteCollecteRsrc;

    /**
     * Constructeur spécifique
     *
     * @param cFonc cout de fonctionnement
     * @param cCon cout de construction
     * @param tCon temps de construction
     * @param emplacement case où la centrale est presente
     * @param capaciteCollecteRsrc
     */
    public ProducteurRessources(int cFonc, float cCon, int tCon, Case emplacement, int capaciteCollecteRsrc, ZoneDeJeu zone) {
        super(cFonc, cCon, tCon, emplacement, zone);
        this.capaciteCollecteRsrc = capaciteCollecteRsrc;
    }

    /**
     * @return la capacite de recolte.
     */
    public int recolter() {
        return capaciteCollecteRsrc;
    }

    /**
     * @return une description des éléments de la case.
     */
    @Override
    public String getDescription() {
        return  super.getDescription()
                + "Vitesse de r\u00e9cup\u00e9ration des ressources: "+capaciteCollecteRsrc;
    }
}
