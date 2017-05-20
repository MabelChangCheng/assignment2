package participant;

import exception.WrongTypeException;
import game.EventType;
import exception.GameException;

/**
 * Abstract class for Swimmer, Super, Cycling and Sprinter.
 */
public abstract class Athlete extends Participant
        implements Comparable<Athlete> {

    // Game points of the athlete.
    private int points;

    /**
     * Constructor.
     *
     * @param id    id of the athlete.
     * @param name  name of the athlete.
     * @param age   age of the athlete.
     * @param state state of the athlete.
     */
    public Athlete(String id, String name, int age, String state) {
        super(id, name, age, state);
        this.points = 0;
    }

    /**
     * Competes in the game and returns the time used.
     *
     * @param event event type.
     * @return the time used.
     */
    public int compete(EventType event) {
        // If the athlete can play the event, create a time randomly.
        if (canPlay(event)) {
            return event.getTime();
        } else {
            throw new WrongTypeException(event, this);
        }
    }

    /**
     * Returns true if the athlete can play the event.
     *
     * @param type event type.
     * @return true if the athlete can play the event or false otherwise.
     */
    public abstract boolean canPlay(EventType type);


    /**
     * @return the points of the athlete.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Adds points to the athlete.
     *
     * @param points the points to be added.
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * Compares with another athlete by points.
     *
     * @param other the athlete to compared with.
     * @return 1 if the other athlete has a higher points or -1 otherwise.
     */
    @Override
    public int compareTo(Athlete other) {
        return points < other.points ? 1 : -1;
    }
}
