package exception;

import game.Game;

/**
 * Exception thrown when trying to run a game, which has less than 4 athletes registered.
 */
public class TooFewAthleteException extends GameException {

    /**
     * Constructor.
     */
    public TooFewAthleteException() {
        super("No less than " + Game.MIN_ATHLETES + " athletes.");
    }
}
