package models;

public class AirlineException extends FlightPlanException  {

    private static final long serialVersionUID = 0;

    /**
     * Constructs a default AirplaneException object.
     */
    public AirlineException() {
        super();
    }

    /**
     * Constructs a AirplaneException object with a given message.
     *
     * @param message holds the AirplaneException's description
     */
    public AirlineException(String message) {
        super(message);
    }

    /**
     * Constructs a AirplaneException object with a given message and Throwable object
     *
     * @param message holds the AirplaneException's description
     * @param cause   holds a Throwable object to include with the AirplaneException
     */
    public AirlineException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a AirplaneException object with a given Throwable object
     *
     * @param cause holds a Throwable object to include with the AirplaneException
     */
    public AirlineException(Throwable cause) {
        super(cause);
    }
}
