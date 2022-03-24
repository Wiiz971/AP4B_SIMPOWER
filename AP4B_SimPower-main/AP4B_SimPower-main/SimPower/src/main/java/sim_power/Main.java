package sim_power;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import sim_power.controleur.ControleurGameOver;
import sim_power.controleur.ControleurJFX;
import sim_power.controleur.ControleurZoneDeJeu;
import sim_power.modele.ZoneDeJeu;

import java.io.IOException;
import java.util.Timer;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Fenêtre de l'application.
     */
    private Stage stage;

    /**
     * Instance de la zone de jeu.
     */
    private ZoneDeJeu modele;

    /**
     * Controleur principal actuellement utilisé
     */
    private ControleurJFX currentMainControleur;

    @Override
    public void start(Stage primaryStage) {
        modele = new ZoneDeJeu();

        // Timer, un tic toutes les 3 secondes.
        Timeline timerTic = new Timeline(
                new KeyFrame(Duration.seconds(3),
                        event -> {
                            modele.updateDonnees();
                            if (getCurrentMainControleur() != null)
                                getCurrentMainControleur().update();
                        }));
        timerTic.setCycleCount(Timeline.INDEFINITE);
        timerTic.play();


        stage = primaryStage;
        chargerVueZoneDeJeu();
    }

    /**
     * Affichage de l'écran de game over.
     */
    public void chargerVueGameOver() {
        // Chargement du fichier fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("VueGameOver.fxml"));

        Parent vue = null;
        try {
            vue = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Afficheg de la fenêtre
        Scene scene = new Scene(vue);
        stage.setScene(scene);
        stage.show();

        // Configuration du controleur
        ControleurGameOver controleur = loader.getController();
        controleur.setMain(this);   // forunit le main au controleur
        currentMainControleur = controleur;

        // Reset de la zone de jeu
        this.modele = new ZoneDeJeu();
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setMaxHeight(400);
    }

    /**
     * Affichage de la zone de jeu.
     */
    public void chargerVueZoneDeJeu() {
        // Chargement du fichier fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("VueZoneDeJeu.fxml"));

        Parent vue = null;
        try {
            vue = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Afficheg de la fenêtre
        Scene scene = new Scene(vue);
        stage.setScene(scene);
        stage.show();

        // Configuration du controleur
        ControleurZoneDeJeu controleur = loader.getController();
        controleur.setMain(this);   // forunit le main au controleur
        currentMainControleur = controleur;
        stage.setMinWidth(850);
        stage.setMinHeight(640);
        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);
    }

    /**
     * @return la zone de jeu.
     */
    public ZoneDeJeu getZoneDeJeu() {
        return this.modele;
    }

    /**
     * Ferme l'application javafx.
     */
    public void quitterApplication() {
        javafx.application.Platform.exit();
    }

    /**
     * @return le controleur principal actuellement utilisé.
     */
    public ControleurJFX getCurrentMainControleur() {
        return this.currentMainControleur;
    }

    /**
     * @return la fenêtre graphique.
     */
    public Stage getStage() {
        return this.stage;
    }
}
