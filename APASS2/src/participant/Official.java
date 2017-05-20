package participant;

/**
 * Game official.
 */
public class Official extends Participant {

    /**
     * Constructor.
     *
     * @param id    id of the official.
     * @param name  name of the official.
     * @param age   age of the official.
     * @param state state of the official.
     */
    public Official(String id, String name, int age, String state) {
        super(id, name, age, state);
    }

    /**
     * @return a string represents the participant.
     */
    @Override
    public String toString() {
        return "[Official] " + super.toString();
    }
}
