package sim_power.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sim_power.Main;
import sim_power.modele.Case;
import sim_power.modele.ZoneDeJeu;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controleur de la vue "VueCase".
 */
public class ControleurCase extends ControleurJFX {

    /**
     * Panneau qui correspond à l'affichage de la case.
     */
    @FXML
    private Pane casePanel;

    /**
     * Coordonnée de la case en X.
     */
    private int coordX;

    /**
     * Coordonnée de la case en Y.
     */
    private int coordY;

    /**
     * Info-bulle.
     */
    private Tooltip tip;

    /**
     * Permet d'initialiser les éléments visuels.
     * La méthode est appelée suite au chargement de vue correspondante.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    /**
     * Ajoute une référence vers la classe main pour pouvoir charger
     * les différentes vues et accéder au modele.
     *
     * @param ref Référence de la classe main
     */
    @Override
    public void setMain(Main ref) {
        super.setMain(ref);
        this.coordY = GridPane.getRowIndex(casePanel);
        this.coordX = GridPane.getColumnIndex(casePanel);

        this.setTooltip();
        this.update();
    }

    /**
     * Mettre à jour l'affichage de la case
     */
    @Override
    public void update() {
        Case currentCase = main.getZoneDeJeu().getCase(coordX, coordY);
        currentCase.setStyle(casePanel);
        this.tip.setText(currentCase.toStringMeteo());
        Tooltip.install(casePanel, tip);
    }

    /**
     * Sélectionne la case losque le joueur clique dessus et
     * met à jour l'affichage.
     *
     * @param e Evènement déclenché
     */
    @FXML
    public void onMousePressed(MouseEvent e) {
        ZoneDeJeu zoneJeu = main.getZoneDeJeu();
        Case precedenteCase = zoneJeu.getCaseSelectionnee();
        zoneJeu.selectionnerCase(coordX, coordY);
        this.update();

        if (precedenteCase != null) {
            ControleurZoneDeJeu ctrlZoneJeu = (ControleurZoneDeJeu) main.getCurrentMainControleur();
            ctrlZoneJeu.updateAffichageCase(precedenteCase.getCoordX(), precedenteCase.getCoordY());
        }
    }

    /**
     * Affiche une info bulle lorsque la souris survole la case.
     */
    private void setTooltip() {
        Case currentCase = main.getZoneDeJeu().getCase(coordX, coordY);
        this.tip = new Tooltip(currentCase.toStringMeteo());
        tip.setShowDelay(Duration.seconds(0));
        Tooltip.install(casePanel, tip);
    }
}
