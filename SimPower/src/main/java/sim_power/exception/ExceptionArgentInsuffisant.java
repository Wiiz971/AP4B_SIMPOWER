package sim_power.exception;

/**
 * Exception declenchée lorsque l'argent est insuffisant.
 */
public class ExceptionArgentInsuffisant extends Exception {

    /**
     * Constructeur de la classe.
     *
     * @param montant Montant nécessaire
     */
    public ExceptionArgentInsuffisant(float montant) {
        String erreur = "Argent insuffisant !";
        String message = "Cette action n\u00e9cessite le montant suivant: "+montant+"$.";
        new AlertePopup(erreur, message);
    }
}
