package participant;

import game.EventType;

/**
 * Swimmer.
 */
public class Swimmer extends Athlete {

    /**
     * Constructor.
     *
     * @param id    id of the athlete.
     * @param name  name of the athlete.
     * @param age   age of the athlete.
     * @param state state of the athlete.
     */
    public Swimmer(String id, String name, int age, String state) {
        super(id, name, age, state);
    }

    @Override
    public boolean canPlay(EventType type) {
        return type == EventType.SWIMMING;
    }

    /**
     * @return a string represents the participant.
     */
    @Override
    public String toString() {
        return "[Swimmer] " + super.toString();
    }
}
