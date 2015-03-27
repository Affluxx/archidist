package tp.model;

/**
 * This class represent a CityNotFound Exception.
 */
public class CityNotFound extends Exception {
    /**
     * Construct an empty exception
     */
    public CityNotFound(){
        super();
    }

    /**
     * Construct an exception with a message
     * @param message
     * Message to send to the user
     */
    public CityNotFound(String message) {
        super(message);
    }

    /**
     * Construct an exception with the throwable
     * @param t
     * The throwable to use
     */
    public CityNotFound(Throwable t) {
        super(t);
    }

    /**
     * Construct an exception with a message and a throwable
     * @param message
     * The message to send to the user
     * @param t
     * The Throwable to use
     */
    public CityNotFound(String message, Throwable t) {
        super(message,t);
    }

}
