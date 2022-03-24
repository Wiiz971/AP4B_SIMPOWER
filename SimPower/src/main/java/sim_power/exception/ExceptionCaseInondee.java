package sim_power.exception;

/**
 * Exception declenchée lorsque la case est inondée.
 */
public class ExceptionCaseInondee extends Exception {

    /**
     * Constructeur par défaut.
     */
    public ExceptionCaseInondee() {
        String erreur = "Case inond\u00e9e !";
        String message = "Il est impossible de construire sur un terrain inond\u00e9.";
        new AlertePopup(erreur, message);
    }
}
