package exception;

import game.EventType;
import participant.Athlete;

/**
 * Exception thrown when trying to add an athlete to a wrong type of game.
 */
public class WrongTypeException extends GameException {

    /**
     * Constructor.
     */
    public WrongTypeException(EventType event, Athlete athlete) {
        super(athlete + " cannot be added to a wrong game [" + event.name().toLowerCase() + "]");
    }
}
