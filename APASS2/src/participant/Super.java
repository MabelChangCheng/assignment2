package participant;

import game.EventType;

/**
 * Super athlete.
 */
public class Super extends Athlete {

    /**
     * Constructor.
     *
     * @param id    id of the athlete.
     * @param name  name of the athlete.
     * @param age   age of the athlete.
     * @param state state of the athlete.
     */
    public Super(String id, String name, int age, String state) {
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
        return true;
    }

    /**
     * @return a string represents the participant.
     */
    @Override
    public String toString() {
        return "[Super] " + super.toString();
    }
}
