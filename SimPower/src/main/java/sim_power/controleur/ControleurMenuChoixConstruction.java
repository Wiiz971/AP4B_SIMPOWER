package sim_power.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import sim_power.Main;
import sim_power.modele.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controleur de la vue "VueMenuChoixConstruction".
 */
public class ControleurMenuChoixConstruction extends ControleurJFX {

    /**
     * Panneau qui contient les différents boutons.
     */
    @FXML
    private VBox choixPanel;

    /**
     * Texte qui correspond aux infomations de l'installation sélectionnée.
     */
    @FXML
    private Label labelInfos;

    /**
     * Texte qui correspond au nom de l'installation sélectionnée.
     */
    @FXML
    private  Label labelNomInstallation;

    /**
     * Liste des boutons qui permettent de sélectionner une installation.
     */
    private ArrayList<Button> choix;

    /**
     * Identifiant du bouton sélectionné.
     */
    private int idBoutonSelect;

    /**
     * Instance de l'installation sélectionée.
     */
    private Installation choixInstallation;

    /**
     * Liste des installations.
     */
    private final String[] nomsInstallations = {
            "Barrage",
            "Centrale \u00e0 charbon",
            "Centrale nucl\u00eaaire",
            "Eoliennes",
            "Champs de panneaux solaires",
            "Mine de charbon"
    };

    /**
     * Permet d'initialiser les éléments visuels.
     * La méthode est appelée suite au chargement de vue correspondante.
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        this.idBoutonSelect = 0;

        // Ajout et configuration des boutons pour choisir une installation
        choix = new ArrayList<>();
        for (int i=0; i<6; i++) {
            Button b = new Button();
            b.setId(""+i);
            b.setPrefWidth(185);
            b.setText(nomsInstallations[i]);
            b.setOnAction(e -> {
                selectionnerChoix(((Button)e.getTarget()).getId());
            });
            choix.add(b);
            choixPanel.getChildren().add(b);
        }
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
        this.selectionnerChoix(String.valueOf(idBoutonSelect));
        this.updateButtonsColor();
    }

    /**
     * Actualise l'affichage de la vue, non implémentée.
     */
    @Override
    public void update() {}

    /**
     * Créer l'installation sélectionnée sur la case et met à jour l'affichage.
     */
    public void lancerConstruction() {
        Case caseSelectionnee = main.getZoneDeJeu().getCaseSelectionnee();
        try {
            caseSelectionnee.construire(this.choixInstallation);
            this.main.getZoneDeJeu().ajouterInstallation(this.choixInstallation);
            if(this.choixInstallation instanceof Barrage) {
                ((Barrage) this.choixInstallation).inonderZone();
            }

            this.main.getZoneDeJeu().depenserArgent(this.choixInstallation.getCoutConstruction());

            ControleurZoneDeJeu ctrlZoneJeu = (ControleurZoneDeJeu) main.getCurrentMainControleur();
            ctrlZoneJeu.update();
        } catch (Exception e) {
        }
    }

    /**
     * Méthode appelée lorsuqe que le joueur apuie sur un bouton.
     * Sélectionne une instalaltio en fonction de l'id du bouton
     * puis met à jour la vue.
     *
     * @param id identifiant du bouton
     */
    private void selectionnerChoix(String id) {
        Case caseSelectionnee = main.getZoneDeJeu().getCaseSelectionnee();
        switch (id) {
            case "0":
                this.choixInstallation = new Barrage(caseSelectionnee, main.getZoneDeJeu());
                break;
            case "1":
                this.choixInstallation = new CentralesCharbon(caseSelectionnee, main.getZoneDeJeu());
                break;
            case "2":
                this.choixInstallation = new CentraleNucleaire(caseSelectionnee, main.getZoneDeJeu());
                break;
            case "3":
                this.choixInstallation = new Eoliennes(caseSelectionnee, main.getZoneDeJeu());
                break;
            case "4":
                this.choixInstallation = new PanneauxSolaires(caseSelectionnee, main.getZoneDeJeu());
                break;
            case "5":
                this.choixInstallation = new MineCharbon(caseSelectionnee, main.getZoneDeJeu());
                break;
        }
        // mise à jour de la vue
        this.idBoutonSelect = Integer.parseInt(id);
        this.labelNomInstallation.setText(this.nomsInstallations[this.idBoutonSelect]);
        this.labelInfos.setText(this.choixInstallation.getInfosCreation());
        this.updateButtonsColor();
    }

    /**
     * Met à jour l'apparance des boutons, en fonction de s'ils sont
     * slélectionnés ou pas.
     */
    private void updateButtonsColor() {
        for (int i=0; i<6; i++) {
            if (i == idBoutonSelect)
                choix.get(i).setStyle("-fx-background-color:  #a83489;"+
                        "-fx-text-fill: white;"+
                        "-fx-border-color: black");
            else
                choix.get(i).setStyle("-fx-background-color:  white;"+
                        "-fx-text-fill: black;"+
                        "-fx-border-color: black");
        }
    }
}
