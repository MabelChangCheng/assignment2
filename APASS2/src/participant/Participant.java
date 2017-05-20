package participant;

import java.util.Objects;

/**
 * Participant for ozlympic games.
 */
public abstract class Participant {

    // id of the participant.
    private final String id;

    // name of the participant.
    private final String name;

    // age of the participant.
    private final int age;

    // state of the participant.
    private final String state;

    /**
     * Constructor.
     *
     * @param id    id of the participant.
     * @param name  name of the participant.
     * @param age   age of the participant.
     * @param state state of the participant.
     */
    public Participant(String id, String name, int age, String state) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getState() {
        return state;
    }

    /**
     * @return a string represents the participant.
     */
    @Override
    public String toString() {
        return toShortString();
    }

    public String toShortString() {
        return String.format("[%s] %s", id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participant that = (Participant) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
