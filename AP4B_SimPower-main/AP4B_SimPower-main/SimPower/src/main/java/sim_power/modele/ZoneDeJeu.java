package sim_power.modele;

import sim_power.exception.ExceptionArgentInsuffisant;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe représentant la zone de jeu et ses caractéristiques.
 */
public class ZoneDeJeu {

    /**
     * Taille de la zone de jeu (nombre de cases sur un côté).
     */
    private int taille;

    /**
     * Tableau qui contient les cases qui composent la zone de jeu.
     * Une case est accessible par ses coordonnées x et y.
     */
    private Case[][] cases;

    /**
     * Prix de l'électricité défini par le joueur.
     */
    private float prixElectricite;

    /**
     * Argent possédé par le joueur.
     */
    private float argent;

    /**
     * Energie totale produite et disponible dans la zone de jeu.
     */
    private float energieTotaleDisponible;

    /**
     * Case selectionnee par le joueur.
     */
    private Case caseSelectionnee;

    /**
     * Ressource totale en charbon à disposition
     */
    private Charbon rsrcTotaleCharbon;

    /**
     * Population globale de la zone de jeu.
     */
    private Population population;

    /**
     * Liste des installations dans la zone de jeu.
     */
    private ArrayList<Installation> installations;

    /**
     * Tempêtes actives.
     */
    private Tempete tempetes[];

    /**
     * Vrai si la partie est perdue.
     */
    private boolean gameOver;

    /**
     * Constructeur par défaut.
     */
    public ZoneDeJeu() {
        this.taille = 15;
        this.prixElectricite = 30;
        this.argent = 10000;
        this.energieTotaleDisponible = 0;
        this.caseSelectionnee = null;
        this.rsrcTotaleCharbon = new Charbon();
        this.population = new Population();
        this.installations = new ArrayList<>();

        this.cases = new Case[this.taille][this.taille];
        this.tempetes = new Tempete[this.taille / 5 + 1];
        genererTerrain();
    }

    /**
     * Génère les cases qui composent le terrain.
     */
    private void genererTerrain() {
        for (int y=0; y < this.taille; y++) {
            for (int x = 0; x < this.taille; x++) {
                // Nombre aléatoire pour la génération du terrain
                int nbAlea = ThreadLocalRandom.current().nextInt(0, 30 + 1);

                if (nbAlea < 2) {       // Création d'un cours d'eau
                    this.cases[x][y] = new CaseCoursDEau(x, y);
                }
                else if (nbAlea == 2) { // Création d'une réserve naturelle de charbon
                    this.cases[x][y] = new CaseReserveCharbon(x, y);
                }
                else if (nbAlea > 2) {  // Création d'une plaine 'case vide)
                    this.cases[x][y] = new Case(x, y);

                    if (nbAlea == 10) { // Construction d'une habitation
                        try {
                            Habitation habitation = new Habitation(this.cases[x][y]);
                            this.cases[x][y].construire(habitation);
                            population.ajouterHabitation(habitation);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

        }
        for (int i = 0 ; i < tempetes.length ; i++) {
            tempetes[i] = new Tempete(this.taille, this.taille);
        }
        updateMeteo();

        // Par défaut, la première case est sélectionée
        this.caseSelectionnee = this.cases[0][0];
        this.caseSelectionnee.selectionner();
    }

    /**
     * Met à jour, change, et recalcule toutes les données de la zone de jeu.
     */
    public void updateDonnees() {
        this.percevoirRevenu();
        this.updateEnergieTotaleDisponible();
        for (int i=0; i<cases.length; i++) {
            for (int j=0; j<cases[0].length; j++) {
                this.rsrcTotaleCharbon.ajouter(cases[i][j].recolter());
            }
        }

        updateMeteo();
        updatePollution();
        population.updatePopulation(cases);
        updateAccidents();  // non implémenté

        // Si tous les habitants sont partie, c'est la fin de la partie.
        // Le joueur a alors perdu.
        if (population.getNbHabitations() == 0)
            gameOver = true;
    }

    //  CHEAT CODE
    public void setGameOverTrue() {
        gameOver = true;
    }

    /**
     * Sélectionne une nouvelle case.
     *
     * @param x Coordonnée de la case en X
     * @param y Coordonnée de la case en Y
     */
    public void selectionnerCase(int x, int y) {
        if (caseSelectionnee != null)
            caseSelectionnee.deselectionner();
        caseSelectionnee = this.getCase(x,y);
        caseSelectionnee.selectionner();
    }

    /**
     * @return le prix de l'électricité
     */
    public float getPrixElectricite() { return prixElectricite; }

    /**
     * Change le prix de l'électricité.
     *
     * @param prix nouveau prix de l'électricité
     */
    public void setPrixElectricite(float prix) {
        this.prixElectricite = prix;
    }

    /**
     * Ajoute la référence de l'installation dans la liste
     * des installations.
     *
     * @param installation référence de l'installation
     */
    public void ajouterInstallation(Installation installation) {
            this.installations.add(installation);
            installation.setZoneDeJeu(this);
    }

    /**
     * Supprime la référence de l'installation de la liste
     * des installations.
     *
     * @param id identifiant de l'installation
     */
    public void supprimerInstallation(int id) {
        for (int i=0; i < this.installations.size(); i++) {
            Installation install = installations.get(i);
            if (install.getId() == id)
                if(install instanceof Barrage)
                    ((Barrage) install).annulerIondation();
            installations.remove(i);
        }
    }

    /**
     * Retourne la case de coordonnées x et y.
     *
     * @param x Coordonnée de la case en X
     * @param y Coordonnée de la case en Y
     * @return La case de coordonnées x et y
     */
    public Case getCase(int x, int y) {
        if (x < this.taille && y < this.taille)
            return this.cases[x][y];
        else {
            throw new IndexOutOfBoundsException("IndexOutOfBounds; x="+x+"y="+y+"\n\t--> taille="+this.taille);
        }
    }

    /**
     * @return la taille de la grille.
     */
    public int getTaille() {
        return this.taille;
    }

    /**
     * @return la case selectionnee.
     */
    public Case getCaseSelectionnee() {
        return this.caseSelectionnee;
    }

    /**
     * @return l'énergie totale produitre sur la zone de jeu.
     */
    public float getEnergieTotaleDisponible() {
        return energieTotaleDisponible;
    }

    /**
     * @return l'argent à disposition du joueur.
     */
    public float getArgent() {
        return argent;
    }

    /**
     * @return la quantité de charbon possédée par le joueur
     */
    public int getQteTotaleCharbon() {
        return rsrcTotaleCharbon.getQteRessource();
    }

    /**
     * @return vrai si la partie est perdu, faux sinon.
     */
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * Consomme une certaine quantité de charbon.
     *
     * @param qteCharbon quantité de charbon
     */
    public void consommerCharbon(int qteCharbon) {
        this.rsrcTotaleCharbon.consommer(qteCharbon);
    }

    /**
     * @return la population de la zone de jeu.
     */
    public Population getPopulation() {
        return population;
    }

    /**
     * Met à jour la quantité d'énergie produite au total sur la zone de jeu.
     */
    private void updateEnergieTotaleDisponible() {
        this.energieTotaleDisponible = 0;
        for (Installation installation: this.installations) {
            if(installation instanceof Centrale)
                this.energieTotaleDisponible += ((Centrale)installation).getProductionEffective();
        }
    }

    /**
     * Prélève de l'argent à la population pour l'ajouter au montant
     * total.
     */
    private void percevoirRevenu() {
        this.argent += this.prixElectricite * population.getNbHabitations();
    }

    /**
     * Dépense de l'argent lors de la création dun bâtiment.
     *
     * @param montant Montant à dépenser
     */
    public void depenserArgent(float montant) throws ExceptionArgentInsuffisant {
        if (montant > argent)
            throw new ExceptionArgentInsuffisant(montant);
        this.argent -= montant;
    }

    /**
     * met à joue la météo
     */
    private void updateMeteo() {
        // Génération nouvelle localisation pour les tempêtes
        for (Tempete x : tempetes) {
            x.update();
        }
        // Calcul météo
                for (Case[] x : cases) {
            for (Case y : x) {
                y.calcVent(tempetes);
                y.calcEnsoleillement(tempetes);
            }
        }


    }

    /**
     * Met à jour la pollution
     */
    private void updatePollution() {
        for (Case[] x : cases) {
            for (Case y : x) {
                y.calcPollution(installations);
            }
        }
    }

    /**
     * met à jour un accident
     */
    private void updateAccidents() {

    }


}
