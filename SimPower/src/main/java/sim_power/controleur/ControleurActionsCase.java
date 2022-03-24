package sim_power.controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Window;
import sim_power.Main;
import sim_power.modele.Case;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controleur de la vue "ActionsCase".
 */
public class ControleurActionsCase extends  ControleurJFX {

    /**
     * Nom de la case délectionnée et de la construction s'il y en a une.
     */
    @FXML
    private Text nomCaseSlectionnee;

    /**
     *  Description de la case délectionnée er de la construction s'il y en a une.
     */
    @FXML
    private Label descriptionCase;

    /**
     * Permet d'initialiser les éléments visuels.
     * La méthode est appelée suite au chargement de vue correspondante.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        descriptionCase.setWrapText(true);
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
        this.update();
    }

    /**
     * Met à jour l'affichage des informations de la case sélectionnée.
     */
    @Override
    public void update() {
        Case caseSelectionnee = main.getZoneDeJeu().getCaseSelectionnee();
        this.nomCaseSlectionnee.setText(caseSelectionnee.getName());
        this.descriptionCase.setText(caseSelectionnee.getDescription());
    }

    /**
     * Méthode appelée losque le joueur appuie sur le bouton correspondant.
     * Permet de détruire une installation sur la case sélectionnée.
     */
    @FXML
    public void detruire() {
     // Création d'une alerte
        ButtonType bDetruire = new ButtonType("D\u00e9truire");
        ButtonType bAnuler = new ButtonType("Anuler");
        Alert a = creerAlerte(null, "Etes-vous s\u00fbr de vouloir d\u00e9truire ?", bDetruire, bAnuler );
        a.setAlertType(Alert.AlertType.WARNING);

        // Affichage de l'alerte
        Optional<ButtonType> result = a.showAndWait();
        // Programmation des boutons de l'alerte
        result.ifPresent(res->{
            if (res.equals(bDetruire)) {  // Détruire l'installation sélectionnée
                Case caseSelectionnee = main.getZoneDeJeu().getCaseSelectionnee();
                try {
                    int id = caseSelectionnee.getConstruction().getId();
                    this.main.getZoneDeJeu().supprimerInstallation(id);
                    caseSelectionnee.detruire();
                } catch (Exception e) {
                }
                ControleurZoneDeJeu ctrlZoneJeu = (ControleurZoneDeJeu) main.getCurrentMainControleur();
                ctrlZoneJeu.update();
            }
            a.close();
        });
    }

    /**
     * Méthode appelée losque le joueur appuie sur le bouton correspondant
     * Permet de construire une installation sur la case sélectionnée.
     */
    @FXML
    public void construire() {
        try {
            // chargement de la vue
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("VueMenuChoixConstruction.fxml"));
            Pane menuPanel  = loader.load();

            // chargement du controleur
            ControleurMenuChoixConstruction controleur = loader.getController();
            controleur.setMain(this.main);

            // Création d'une alerte
            ButtonType bConstruire = new ButtonType("Construire");
            ButtonType bAnuler = new ButtonType("Anuler");
            Alert a = creerAlerte(menuPanel, "", bConstruire, bAnuler );

            // Affichage de l'alerte
            Optional<ButtonType> result = a.showAndWait();
            // Programmation des boutons de l'alerte
            result.ifPresent(res->{
                if (res.equals(bConstruire)) {  // Construire l'installation sélectionnée
                    // Deuxième alerte de confirmation de la construction
                    Alert a2 = creerAlerte(null, "Etes-vous s\u00fbr de vouloir construire ?", bConstruire, bAnuler);
                    a2.setAlertType(Alert.AlertType.CONFIRMATION);
                    Optional<ButtonType> result2 = a2.showAndWait();
                    result2.ifPresent(res2 -> {
                        if (res2.equals(bConstruire)) { // Confirmer la construction
                            controleur.lancerConstruction();
                        }
                        a2.close();
                    });
                }
                else // Annuler la construction
                    a.close();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Créé et initialise une alete.
     *
     * @param menuPanel Panneau contenu dans l'alerte
     * @param b1 permier bouton à ajouter
     * @param b2 deuxième bouton à ajouter
     * @return Une alerte configurée.
     */
    private Alert creerAlerte(Pane menuPanel, String texte, ButtonType b1, ButtonType b2) {
        Alert a = new Alert(Alert.AlertType.NONE, texte, b1, b2);
        if (menuPanel != null)
            a.getDialogPane().setContent(menuPanel);
        Window window = a.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(e -> a.close());
        return a;
    }
}
