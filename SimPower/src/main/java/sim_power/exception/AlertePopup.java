package sim_power.exception;

import javafx.scene.control.Alert;

/**
 * Alerte sous forme de popup.
 */
public class AlertePopup extends Alert {

    /**
     * Constructeur de la classe.
     *
     * @param erreur Type de l'erreur
     * @param message Explications sur l'erreur
     */
    public AlertePopup(String erreur, String message) {
        super(AlertType.WARNING);
        setTitle("Action impossible \u00e0 effectuer");
        setHeaderText(erreur);
        setContentText(message);
        showAndWait();
    }
}
