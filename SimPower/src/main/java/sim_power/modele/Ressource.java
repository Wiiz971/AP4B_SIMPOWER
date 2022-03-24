package sim_power.modele;

/**
 * Classe représentant les ressources.
 */
public abstract class Ressource {

    /**
     * quantite de ressources
     */
    private int qteRessource;

    Ressource(int stockinit) {
        qteRessource = stockinit;
    }

    /**
     * @return la quantite de ressources
     */
    public int getQteRessource() {
        return qteRessource;
    }

    /**
     *Ajoute une quantité de ressource.
     *
     * @param v une quantite de ressources a ajouter
     */
    public void ajouter(int v) {
        qteRessource += v;
    }

    /**
     * Consomme une quantité de ressource.
     *
     * @param v quantite de ressources a consommer
     */
    public void consommer(int v) {
        ajouter(-v);
    }
}

