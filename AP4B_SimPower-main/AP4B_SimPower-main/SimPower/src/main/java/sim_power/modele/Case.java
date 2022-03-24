package sim_power.modele;

import javafx.scene.layout.Pane;
import sim_power.exception.*;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe représentant une case du terrain.
 */
public class Case implements CaseAffichable {

    /**
     * Force du vent.
     */
    private float vent;
    private float ventBase;

    /**
     * Niveau d'ensoleillement.
     */
    private float ensoleillement;
    private float ensoleillementBase;

    /**
     * Niveau de pollution.
     */
    private float pollution;

    /**
     * Coordonnée de la case en X.
     */
    private int coordX;

    /**
     * Coordonnée de la case en Y.
     */
    private int coordY;

    /**
     * Vrai (true) si la case es inondee, donc inexploitable,
     * faux (false) sinon.
     */
    private boolean inondee;

    /**
     * Vrai (true) si la case est sélectionnée (par le joueur).
     */
    private boolean estSlectionnee;

    /**
     * Construction sur la case.
     */
    protected Construction construction;

    /**
     * Constructeur par défaut.

     * @param coordX Coordonnée de la case en X
     * @param coordY Coordonnée de la case en Y
     */
    public Case(int coordX, int coordY) {
        this.ventBase = ThreadLocalRandom.current().nextInt(30, 80 + 1);
        this.vent = ventBase;
        this.ensoleillementBase = ThreadLocalRandom.current().nextInt(50, 130 + 1);
        this.ensoleillement = ensoleillementBase;
        this.pollution = 0;
        this.coordX = coordX;
        this.coordY = coordY;
        this.inondee = false;
        this.estSlectionnee = false;
        this.construction = null;
    }

    /**
     * Ajoute une construction sur la case
     *
     * @param construction construction à ajouter
     */
    public void construire(Construction construction) throws Exception {
        if (this.inondee)
            throw new ExceptionCaseInondee();

        if (this.construction != null) {
            if (this.construction instanceof Habitation)
                throw new ExceptionCaseHabitee();
            else
                throw new ExceptionCaseOccupee();
        }
        else {
            if (construction instanceof Barrage
                    || construction instanceof CentraleNucleaire
                    || construction instanceof MineCharbon)
                throw new ExceptionCaseIncompatible();
        }
        this.construction = construction;
    }

    /**
     * Détruit une construction sur la case.
     */
    public void detruire() throws Exception {
        if (construction == null)
            throw new ExceptionCaseVide();
        else if (construction instanceof Habitation)
            throw new ExceptionCaseHabitee();
        this.construction = null;
    }

    /**
     * Détruit une habitation, inaccessible au joueur.
     *
     * @param HouseSafetyOverride
     * @throws Exception
     */
    public void detruire(boolean HouseSafetyOverride) throws Exception {
        if (!HouseSafetyOverride)
            detruire();
        if (construction == null)
            throw new ExceptionCaseVide();
        this.construction = null;
    }

    /**
     * Récolte les ressources s'il y a un producteurs de
     * ressources sur la case..
     */
    public int recolter() {
        return 0;
    }

        /**
         * Sélectionne la case.
         */
    public void selectionner() {
        this.estSlectionnee = true;
    }

    /**
     * Désélectionne la case.
     */
    public void deselectionner() {
        this.estSlectionnee = false;
    }

    /**
     * Si vrai innonde la case,
     * sinon annule l'inondation     *
     * @param bool indique s'il y a une inondation ou pas
     */
    public void setInondee(boolean bool) {
        this.inondee = bool;
    }

    /**
     * Retourne la couleur de fond en css correspondant
     * à l'apparence de la classe
     *
     * @return la couleur de fond
     */
    public String getStyle() {
        return "-fx-background-color: #BFFF00;";
    }

    /**
     * Change l'apparence du panneau pour lui ajouter une bordure.
     *
     * @param panel panneau dont l'apparence change
     */
    public void setStyle(Pane panel) {
        String style = "";
        if (this.construction == null)
            style = this.getStyle();
        else
            style = this.construction.getStyle();

        if (inondee)
            style += "-fx-background-color: #0b2fd4;";
        if (estSlectionnee) {
            style += ("-fx-border-color: #ff9500;\n" +
                    "-fx-border-width: 5 ;");
        }
        else {
            style += ("-fx-border-color: black;\n" +
                    "-fx-border-width: 1 ;");
        }
        panel.setStyle(style);
    }

    /**
     * @return les infomations sur la météo, dont la pollution.
     */
    public String toStringMeteo() {
        return "vent: "+ (int)getVent()
                + "\nensoleillement: "+ (int)getEnsoleillement()
                + "\npollution: "+(int)pollution;
    }

    /**
     * @return la construction.
     */
    public Construction getConstruction() {
        return this.construction;
    }

    /**
     * @return Le nom correspondant à la classe.
     */
    @Override
    public String getName() {
        String name = "Plaine";
        if (this.construction != null)
            name += "\n"+this.construction.getName();
        return name;
    }

    /**
     * @return une description des éléments de la case.
     */
    @Override
    public String getDescription() {
        String description = "";
        if (this.estInondee())
            description += "Zone inond\u00eae\n";
        if (this.construction != null)
            description += this.construction.getDescription();

        return description;
    }

    /**
     * @return la coordonnée en X de la case.
     */
    public int getCoordX() {
        return this.coordX;
    }

    /**
     * @return la coordonnée en Y de la case.
     */
    public int getCoordY() {
        return this.coordY;
    }

    /**
     * @return vrai (true) si la case est inondée.
     */
    public boolean estInondee() {
        return inondee;
    }

    /**
     * @return la valeur du vent.
     */
    public float getVent() {  return vent > 120 ? 120 : vent < 0 ? 0 : vent;  }

    /**
     * @return la valeur de l'ensoleillement.
     */
    public float getEnsoleillement() {
        return ensoleillement > 100 ? 100 : ensoleillement < 0 ? 0 : ensoleillement;
    }

    /**
     * @return la valeur de la pollution.
     */
    public float getPollution() { return pollution;}

    /**
     * Calcule le vent de chaque case en fonction des tempêtes du terrain.
     * L'intensité effective des tempêtes diminue linéairement avec la distance au centre de la tempête.
     *
     * @param tempetes liste des tempetes
     */
    public void calcVent(Tempete[] tempetes) {
        float oldVent = vent;
        vent = ventBase;
        for (Tempete x : tempetes) {
            float dist = calcDistance(x.getCoordX(), x.getCoordY());
            if (dist < x.getRayon()) {
                vent += x.getIntensite() * ((x.getRayon() - dist) / x.getRayon());
            }
        }
    }

    /**
     * Calcule l'ensoleillement de chaque case en fonction des tempêtes du terrain.
     * L'intensité effective des tempêtes diminue non-linéairement avec la distance au centre.
     *
     * @param tempetes liste des tempetes
     */
    public void calcEnsoleillement(Tempete[] tempetes){
        ensoleillement = ensoleillementBase;
        for (Tempete x : tempetes) {
            float dist = calcDistance(x.getCoordX(), x.getCoordY());
            if (dist < x.getRayon()) {
                ensoleillement -= Math.abs(x.getIntensite() - (dist*2)) + 10;
            }
        }
    }
    /**
     * Modifie la polllution de chaque case en fonction des centrales productrices du terrain.
     * Tant qu'une centrale est à portée d'une case, elle applique son modificateur de répercussions.
     * La distance n'a pas d'impact.
     *
     * @param installations liste des installations
     */
    public void calcPollution(ArrayList<Installation> installations) {
        float mod = -12;
        for (Installation ins : installations) {
            if (ins instanceof Centrale && calcDistance(ins.getCase()) < ((Centrale) ins).rayonRepercussions) {
                mod += 10 * ((Centrale) ins).getIntensiteRepercussions();
            }
        }
        pollution += mod;
        if (pollution < 0)
            pollution = 0;
        else if (pollution > 300)
            pollution = 300;
    }

    /**
     * Retourne la distance entre deux cases.
     *
     * @param c2 deuxième case
     * @return la distance
     */
    public float calcDistance(Case c2) {
        return calcDistance(c2.coordX, c2.coordY);
    }

    /**
     * Retourne la distance entre deux cases.
     *
     * @param x coordonnée en X de la deuxième case
     * @param y coordonnée en Y de la deuxième case
     * @return la distance
     */
    public float calcDistance(float x, float y){
        return (float)Math.sqrt(Math.pow(coordX - x,2) + Math.pow(coordY - y,2));
    }
}
