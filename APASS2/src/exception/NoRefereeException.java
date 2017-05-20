package exception;

/**
 * Exception thrown when trying run a game which has no official appointed.
 */
public class NoRefereeException extends GameException {

    /**
     * Constructor.
     */
    public NoRefereeException() {
        super("No referee.");
    }
}
