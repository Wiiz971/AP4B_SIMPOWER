package sim_power.controleur;

import javafx.fxml.Initializable;
import sim_power.Main;

/**
 * Interface implémentée par tous les controleur de fichiers FXML.
 */
public abstract class ControleurJFX implements Initializable {

    /**
     * Référence vers la classe main pour charger
     * les différentes vues.
     */
    protected Main main;

    /**
     * Ajoute une référence vers la classe main pour charger
     * les différentes vues et accéder au modele.
     *
     * @param ref Référence de la classe main
     */
    public void setMain(Main ref) {
        main = ref;
    }

    /**
     * Actualise l'affichage.
     */
    public abstract void update();
}
