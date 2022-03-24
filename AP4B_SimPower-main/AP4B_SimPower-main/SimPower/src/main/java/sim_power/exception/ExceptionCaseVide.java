package sim_power.exception;

/**
 * Exception declenchée lorsque la case est vide.
 */
public class ExceptionCaseVide extends Exception{

    /**
     * Constructeur par défaut.
     */
    public ExceptionCaseVide() {
        String erreur = "Case vide !";
        String message = "Le terrain est vide, il n'y a rien à d\u00e9truire.";
        new AlertePopup(erreur, message);
    }
}
