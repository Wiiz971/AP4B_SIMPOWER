package sim_power.modele;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe représentant les caractéristiques de la population.
 */

public class Population {

    /**
     * Niveau de satisfaction de chaque habitant
     */
    private float satisfaction;

    /**
     * Compteur local de tic.
     */
    private int activeTickCounter;

    /**
     * Cosommation d'une habitation en électricité
     */
    private final int CONSOMMATION_ATOMIQUE = 10;

    /**
     * liste des habitation.
     */
    private ArrayList<Habitation> habitations;

    /**
     * Constructeur par défaut.
     */
    public Population(){
        this.satisfaction = 100;
        this.activeTickCounter = 5;
        this.habitations = new ArrayList<>();
    }

    /**
     * Supprime une habitation de la case et de la population.
     */
    public void supprimerHabitation(int id) {
       habitations.get(id).supprimerHabitation();
       habitations.remove(id);
    }

    /**
     * Ajoute une habitation.
     *
     * @param habitation habitation à ajouter
     */
    public void ajouterHabitation(Habitation habitation) {
        habitations.add(habitation);
    }

    /**
     * Supprime une habitation.
     *
     * @param habitation habitation à supprimer
     */
    public void supprimerHabitation(Habitation habitation) { habitations.remove(habitation); }

    /**
     * @return le besoin en électricité necessaire
     */
    public float calculerBesoinElectricite(){
        return this.habitations.size() * CONSOMMATION_ATOMIQUE;
    }

    /**
     *
     * @return le niveau de satisfaction général
     * Ce dernier est dépendant de la moyenne de la satisfaction de chaque habitation (fonction de la pollution et du vent),
     * du prix de l'électricité, et de la subvension des besoins énergétiques.
     */
    public float calculerSatisfaction(float prodDelta, float prixElectricite){

        float satMoyenne = 0;
        for (Habitation x : habitations) {
            satMoyenne += x.getLocalSatisfaction();
            if (x.getLocalSatisfaction() < 0)
                satMoyenne += x.getLocalSatisfaction();
        }
        satMoyenne /= habitations.size();
        // Si la prod est insuffisante : pénalité de 20 + le nombre de GW de retard
        if (prodDelta < 0) {
            satMoyenne -= 40 - prodDelta;
        }
        // Bonus de satisfaction pour coûts <40
        // Malus de satisfaction pour coûts >40
        if (prixElectricite < 40)
            satMoyenne += 40 - prixElectricite;
        else
            satMoyenne -= (prixElectricite - 40) * 1.5;

        if (satMoyenne < -125)
            satMoyenne = -125;
        else if (satMoyenne > 125)
            satMoyenne = 125;

        satisfaction = satMoyenne;
        return (float) ((satMoyenne + 125) / 2.5);
    }
    /**
     * Ajoute des habitants si la satisfaction est élevée. Supprime des habitatns si la satisfaction est basse.
     */
    public void updatePopulation(Case[][] terrain){
        if (activeTickCounter <= 0) {
            if (satisfaction > 50) {
                // Créer nouvelle habitation
                int d100 = ThreadLocalRandom.current().nextInt(0, 100);
                // System.out.println("dice roll " + d100 + " / " + (satisfaction - 40) / 2);
                if (d100 < (satisfaction - 40) / 2) {
                    int x;
                    int y;
                    do {
                        x = ThreadLocalRandom.current().nextInt(0, terrain.length);
                        y = ThreadLocalRandom.current().nextInt(0, terrain[0].length);
                    } while (terrain[x][y].getConstruction() != null && !(terrain[x][y] instanceof CaseCoursDEau || terrain[x][y] instanceof CaseReserveCharbon));
                    try {
                        Habitation habitation = new Habitation(terrain[x][y]);
                        terrain[x][y].construire(habitation);
                        ajouterHabitation(habitation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    activeTickCounter = 5;
                }

            } else if (satisfaction < -50 && habitations.size() > 1) {

                // Supprimer habitation existante
                int d100 = ThreadLocalRandom.current().nextInt(0, 100);
                System.out.println("dice roll " + d100 + " / " + (Math.abs(satisfaction) - 50) / 2);
                if (d100 < (Math.abs(satisfaction) - 35) / 2){
                    Habitation hab = habitations.get(ThreadLocalRandom.current().nextInt(0, habitations.size()));
                    try {
                        supprimerHabitation(hab);
                        hab.emplacement.detruire(true);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    activeTickCounter = 5;
                }

            }

        }
        if (activeTickCounter > 0)
            activeTickCounter--;
        else
            activeTickCounter = 0;
    }

    /**
     * @return le nombre d'habitants
     */
    public int getNbHabitations(){
        return habitations.size();
    }
}
