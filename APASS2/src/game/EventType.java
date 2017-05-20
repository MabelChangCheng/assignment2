package game;

/**
 * Event type.
 */
public enum EventType {
    SWIMMING('S', 100, 200),
    CYCLING('C', 500, 800),
    RUNNING('R', 10, 20);

    // Symbol character of the event.
    private final char symbol;

    // Minimum time of the event.
    private final int minTime;

    // Maximum time of the event.
    private final int maxTime;


    /**
     * Constructor.
     *
     * @param minTime Minimum time of the event.
     * @param maxTime Maximum time of the event.
     */
    EventType(char symbol, int minTime, int maxTime) {
    	this.symbol = symbol;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    /**
     * @return a time value generated randomly for the event.
     */
    public int getTime() {
        return RandomUtil.generate(minTime, maxTime);
    }

    /**
     * @return the symbol character of the event.
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * @return an event selected randomly.
     */
    public static EventType random() {
        switch (RandomUtil.generate(0, 2)) {
            case 0:
                return SWIMMING;
            case 1:
                return RUNNING;
            default:
                return CYCLING;
        }
    }

    public static EventType parse(String value) {
        for (EventType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown event type: " + value);
    }
}
