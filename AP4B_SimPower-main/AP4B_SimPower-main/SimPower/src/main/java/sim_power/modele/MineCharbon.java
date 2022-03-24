package sim_power.modele;

/**
 * Classe représentant une ressource : mine de charbon
 */
public class MineCharbon extends ProducteurRessources {

    public MineCharbon(Case emplacement, ZoneDeJeu zone) {
        super(10, 1000, 10, emplacement, 15, zone);
    }

    /**
     * Retourne la couleur de fond en css correspondant
     * à l'apparence de la classe
     *
     * @return la couleur de fond
     */
    @Override
    public String getStyle() {
        return "-fx-background-color: #737373;";
    }

    /**
     * @return Le nom correspondant à la classe.
     */
    @Override
    public String getName() {
        return "Mine de charbon";
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
                +"\nVitesse de collecte de charbon: "+capaciteCollecteRsrc;
        return infos;
    }
}
