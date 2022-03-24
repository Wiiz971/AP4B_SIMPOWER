package sim_power.exception;

/**
 * Exception declenchée lorsque la case est déjà habitée.
 */
public class ExceptionCaseHabitee extends Exception {

    /**
     * Constructeur par défaut.
     */
    public ExceptionCaseHabitee() {
        String erreur = "Case habit\u00e9e !";
        String message = "Les habitations ne sont pas destructibles, \u00e7a serait cruel ....";
        new AlertePopup(erreur, message);
    }
}
