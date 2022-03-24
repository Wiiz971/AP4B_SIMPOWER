package sim_power.modele;

/**
 * Classe représentant une construction qui implemente la classe CaseAffichage
 */
public abstract class Construction implements CaseAffichable {

    /**
     * Coût de fonctionnement.
     */
    protected int coutFonctionnement;

    /**
     * Coût de construction
     */
    protected float coutConstruction;

    /**
     * Temps de construction.
     */
    protected int tempsDeConstruction;

    /**
     * Référence vers la case sur laquelle la construction est construite.
     */
    protected Case emplacement;

    /**
     * Identifiant incrémenté qui fournit des identifiant unique.
     */
    private static int ID_INCREMENT = 0;

    /**
     * Identifiant de l'installation.
     */
    private int id;

    /**
     *Constructeur par defaut
     *
     * @param cFonc cout de fonctionnement
     * @param cCon cout de construction
     * @param tCon temps de construction
     * @param caseRef emplacement de la construction
     */
    public Construction(int cFonc, float cCon, int tCon, Case caseRef) {
        this.coutConstruction = cCon;
        this.coutFonctionnement = cFonc;
        this.tempsDeConstruction = tCon;
        this.emplacement = caseRef;

        this.id = ID_INCREMENT;
        ID_INCREMENT++;
    }

    /**
     * @return le coût de construction.
     */
    public float getCoutConstruction() {
        return this.coutConstruction;
    }

    /**
     * @return l'identifiant de l'installation.
     */
    public int getId() {
        return  this.id;
    }
    /**
     * @return la case de l'installation.
     */
    public Case getCase() { return emplacement; }
}