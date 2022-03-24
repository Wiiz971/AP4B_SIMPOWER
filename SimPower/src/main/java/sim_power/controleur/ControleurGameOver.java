package sim_power.controleur;

import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controleur de la vue "VueGameOver".
 */
public class ControleurGameOver extends ControleurJFX {

    /**
     * Méthode inutilisée.
     * Mise à jour de l'interface.
     */
    @Override
    public void update() { }

    /**
     * Permet d'initialiser les éléments visuels.
     * La méthode est appelée suite au chargement de vue correspondante.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Méthode appelée lorsque le joueur appuie sur le bouton "Nouvelle Partie".
     * Relance une nouvelle partie.
     */
    @FXML
    public void lancerNouvellePartie() {
        main.chargerVueZoneDeJeu();
    }

    /**
     * Méthode appelée lorsque le joueur appuie sur le bouton "Quitter".
     * Ferme l'appplication javafx..
     */
    @FXML
    public void quitter() {
        main.quitterApplication();
    }
}
