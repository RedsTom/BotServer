package eu.redstom.botapi.events;

/**
 * Represents the priority of an event when it's called
 */
public enum EventPriority {
    /**
     * Last to execute
     */
    LOWEST(0),
    /**
     * 4th to execute
     */
    LOW(1),
    /**
     * 3th to execute
     */
    NORMAL(2),
    /**
     * 2nd to execute
     */
    HIGH(3),
    /**
     * First to execute
     */
    HIGHEST(4);

    private final int priority;

    EventPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
