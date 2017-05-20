package participant;

import game.EventType;

/**
 * Cycling athlete.
 */
public class Cycling extends Athlete {

    /**
     * Constructor.
     *
     * @param id    id of the athlete.
     * @param name  name of the athlete.
     * @param age   age of the athlete.
     * @param state state of the athlete.
     */
    public Cycling(String id, String name, int age, String state) {
        super(id, name, age, state);
    }

    /**
     * Returns true if the athlete can play the event.
     *
     * @param type event type.
     * @return true if the athlete can play the event or false otherwise.
     */
    @Override
    public boolean canPlay(EventType type) {
        return type == EventType.CYCLING;
    }

    /**
     * @return a string represents the participant.
     */
    @Override
    public String toString() {
        return "[Cycling] " + super.toString();
    }
}
