package sim_power.exception;

/**
 * Exception declenchée lorsque la case est occupée.
 */
public class ExceptionCaseOccupee extends Exception {

    /**
     * Constructeur par défaut.
     */
    public ExceptionCaseOccupee() {
        String erreur = "Case occup\u00e9e !";
        String message = "Le terrain est d\u00e9j\u00e0 ocup\u00e9e par une installation.";
        new AlertePopup(erreur, message);
    }
}
