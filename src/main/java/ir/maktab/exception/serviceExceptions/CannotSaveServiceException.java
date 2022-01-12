package ir.maktab.exception.serviceExceptions;
/**
 * Thrown when application cannot save {@code Service} in database
 */
public class CannotSaveServiceException extends RuntimeException{

    /**
     * Constructs a {@code CannotSaveServiceException} with no detail message.
     */
    public CannotSaveServiceException() {
        super();
    }

    /**
     * Constructs a {@code CannotSaveServiceException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public CannotSaveServiceException(String s) {
        super(s);
    }

}
