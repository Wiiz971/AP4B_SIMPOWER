package sim_power.modele;

/**
* Classe représentant une installation
*/
public abstract class Installation extends Construction {

    /**
     * Vrai s'il y a un incident dans la centrale.
     */
    protected boolean incident;

    /**
     * Référence vers la zone de jeu.
     */
    protected ZoneDeJeu zoneDeJeu;

    /**
     * Constructeur spécifique
     *
     * @param cFonc cout de fonctionnement
     * @param cCon cout de construction
     * @param tCon temps de construction
     * @param emplacement case où la centrale est presente
     */
    public Installation(int cFonc, float cCon, int tCon, Case emplacement, ZoneDeJeu zone) {
            super(cFonc, cCon, tCon, emplacement);
            this.zoneDeJeu = zone;
        }

    /**
     * Retourne sous forme d'une chaine de charactères les informations à afficher au joueur
     * lorsqu'il veut créer une instalaltion.
     * @return les informations sur l'installation avant sa création.
     */
    public abstract String getInfosCreation();

    /**
     * Affecte une référence vers la zone de jeu.
     *
     * @param zoneDeJeu référence de la zone de jeu
     */
    public void setZoneDeJeu(ZoneDeJeu zoneDeJeu) {
        this.zoneDeJeu = zoneDeJeu;
    }

    /**
     * @return une description des éléments de la case.
     */
    @Override
    public String getDescription() {
            if (emplacement.estInondee())
                return "L\u0024*INONDATION LE FONCTIONNEMENT\n";
            else
                return "";
    }
}