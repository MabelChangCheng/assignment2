//s3584767 Chang Cheng
package exception;

import game.Game;

/**
 * Exception thrown when trying to add an athlete to a game which already has 7 athlete registered.
 */
public class GameFullException extends GameException {

    /**
     * Constructor.
     */
    public GameFullException() {
        super("No more than " + Game.MAX_ATHLETES + " athletes.");
    }
}
