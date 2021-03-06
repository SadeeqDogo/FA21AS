package models;

public class FlightException extends FlightPlanException {

    /** Generated by the serialver utility */
    private static final long serialVersionUID = 3472491618245853149L;

    /**
     * Constructs a default AirportException object.
     */
    public FlightException() {
        super();
    }

    /**
     * Constructs a AirportException object with a given message.
     *
     * @param message holds the AirportException's description
     */
    public FlightException(String message) {
        super(message);
    }

    /**
     * Constructs a AirportException object with a given message and Throwable object
     *
     * @param message holds the AirportException's description
     * @param cause   holds a Throwable object to include with the AirportException
     */
    public FlightException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a AirportException object with a given Throwable object
     *
     * @param cause holds a Throwable object to include with the AirportException
     */
    public FlightException(Throwable cause) {
        super(cause);
    }
}
