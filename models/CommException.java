package models;

/**
 * Thrown when an Exception occurs in the Comm class
 *
 * @author  Sodiq Ali Dogo
 *
 */
public class CommException extends AirportException {

	/** Generated by the serialver utility */
	private static final long serialVersionUID = 2864983087672915572L;
	
	/**
	 * Constructs a default CommException object.
	 */
	public CommException() {
		super();
	}
	
	/**
	 * Constructs a CommException object with a given message.
	 * 
	 * @param message holds the CommException's description
	 */
	public CommException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a CommException object with a given message and Throwable object
	 * 
	 * @param message holds the CommException's description
	 * @param cause   holds a Throwable object to include with the CommException
	 */
	public CommException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructs a CommException object with a given Throwable object
	 * 
	 * @param cause holds a Throwable object to include with the CommException
	 */
	public CommException(Throwable cause) {
		super(cause);
	}
}
