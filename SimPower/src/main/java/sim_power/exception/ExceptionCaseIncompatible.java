package sim_power.exception;

/**
 * Exception declenchée lorsque la case est incompatible.
 */
public class ExceptionCaseIncompatible extends Exception {

    /**
     * Constructeur par défaut.
     */
    public ExceptionCaseIncompatible() {
        String erreur = "Case incompatible !";
        String message = "Cette action est incompatible avec ce terrain.";
        new AlertePopup(erreur, message);
    }
}
