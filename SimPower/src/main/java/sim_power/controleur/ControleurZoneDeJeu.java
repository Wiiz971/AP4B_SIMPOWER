package sim_power.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sim_power.Main;
import sim_power.modele.ZoneDeJeu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controleur de la vue "VueZoneDeJeu".
 */
public class ControleurZoneDeJeu extends ControleurJFX {

    /**
     * Panneau sous forme de grille qui contient les cases du terrain.
     */
    @FXML
    private GridPane grille;

    /**
     * Panneau qui permet d'effectuer des actions sur la case sélectionnée.
     */
    @FXML
    private VBox actionsPanel;

    /**
     * Slider qui permet de modifier le prix de l'électricité.
     */
    @FXML
    private Slider sliderPrixElec;


    //    INFORMATIONS GLOBALES SUR LA PARTIE     //
    /**
     * Text qui correspond au prix de l'électricité définit par le joueur.
     */
    @FXML
    private Text textPrixElec;
    /**
     * Text qui correspond à l'argent possédé par le joueur.
     */
    @FXML
    private Text valArgent;
    /**
     * Text qui correspond à la satisfaction globale des habitants.
     */
    @FXML
    private Text valSatisfaction;
    /**
     * Text qui correspond à la demande totale en électricité.
     */
    @FXML
    private Text valDemandeElec;
    /**
     * Text qui correspond à la production totale d'électricité.
     */
    @FXML
    private Text valProductionElec;
    /**
     * Text qui correspond à la quantité totale de charbon posédée.
     */
    @FXML
    private Text valQteCharbon;


    //      CONTROLEURS      //
    /**
     * Tableau contenant les controleurs des cases de la grille.
     */
    private ControleurCase[][] controleurs;
    /**
     * Référence vers le controleur de menu latéral.
     */
    private ControleurActionsCase controleurMenuAction;

    /**
     * Permet d'initialiser les éléments visuels.
     * La méthode est appelée suite au chargement de vue correspondante.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grille.setGridLinesVisible(true);
    }


    /**
     * Ajoute une référence vers la classe main pour pouvoir charger
     * les différentes vues et accéder au modele.
     *
     * @param ref Référence de la classe main
     */
    @Override
    public void setMain(Main ref) {
        super.setMain(ref);

        this.initialiserGrille();
        this.initialiserPanelActionsCase();
        this.initialiserSliderPrixElec();
        this.update();
    }

    /**
     * Mettre à jour l'affichage d'une case de la grille.
     *
     * @param coordX Coordonnée en X de la case
     * @param coordY Coordonnée en Y de la case
     */
    public void updateAffichageCase(int coordX, int coordY) {
        this.controleurs[coordX][coordY].update();

        // Mise à jour du pneau latéral ("Actions")
        this.controleurMenuAction.update();

        //Si la partie est perdu, chargement de l'écran correspondant.
        if (this.main.getZoneDeJeu().isGameOver())
            this.main.chargerVueGameOver();
    }

    /**
     * Actualise l'affichage de la vue.
     */
    @Override
    public void update() {
        updateInformationsZoneJeu();
        for (int i=0; i<controleurs.length; i++) {
            for (int j=0; j<controleurs[0].length; j++) {
                this.updateAffichageCase(i, j);
            }
        }
    }

    /**
     * Initialise la grille qui correspond à la zone du jeu.
     */
    private void initialiserGrille() {
        int taille = main.getZoneDeJeu().getTaille();
        this.controleurs = new ControleurCase[taille][taille];

        // Remplissage de la grille avec avec des panneaux
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                try {
                    // chargement de la vue
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("VueCase.fxml"));
                    Pane casePanel  = loader.load();
                    casePanel.setPrefWidth(grille.getWidth());
                    casePanel.setPrefHeight(grille.getHeight());
                    grille.add(casePanel, i, j);

                    // chargement du controleur
                    ControleurCase controleur = loader.getController();
                    controleur.setMain(main);
                    this.controleurs[i][j] = controleur;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GridPane.setMargin(grille, new Insets(10,10,10,10));
            }
        }
        grille.setAlignment(Pos.CENTER);
    }

    /**
     * Initialise le panneau qui contient de le menu pemettant
     * au joueur d'interagir avec la case sélectionnée.
     */
    private void initialiserPanelActionsCase() {
        try {
            // chargement de la vue
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("VueActionsCase.fxml"));
            VBox actionsCasePanel  = loader.load();

            // chargement du controleur
            this.controleurMenuAction = loader.getController();
            this.controleurMenuAction.setMain(this.main);
            actionsPanel.getChildren().add(actionsCasePanel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialise le slider, ce qui permet au joueur de changer
     * le prix de l'électricité avec celui-ci.
     */
    @FXML
    private void initialiserSliderPrixElec(){
        sliderPrixElec.valueProperty().addListener((observableValue, oldValue, newValue) -> {
                    main.getZoneDeJeu().setPrixElectricite(newValue.floatValue());
                    textPrixElec.setText(String.valueOf(newValue.intValue()));
                }
        );
    }

    /**
     * Remplace le texte adéquat par sa valeur courante.
     */
    private void updateInformationsZoneJeu() {
        ZoneDeJeu zoneJeu = main.getZoneDeJeu();
        valArgent.setText(""+zoneJeu.getArgent());
        valProductionElec.setText(""+zoneJeu.getEnergieTotaleDisponible());
        valQteCharbon.setText(""+zoneJeu.getQteTotaleCharbon());
        valDemandeElec.setText(""+zoneJeu.getPopulation().calculerBesoinElectricite());
        valSatisfaction.setText(""+(int)zoneJeu.getPopulation()
                .calculerSatisfaction(
                        zoneJeu.getEnergieTotaleDisponible() - zoneJeu.getPopulation().calculerBesoinElectricite(),
                        zoneJeu.getPrixElectricite()
                )
        );
    }

    // CHEAT GAME OVER
    @FXML
    public void cheatGameOver(KeyEvent e) {
        if (e.getCode() == KeyCode.O)  {
            this.main.getZoneDeJeu().setGameOverTrue();
        }
    }
}
