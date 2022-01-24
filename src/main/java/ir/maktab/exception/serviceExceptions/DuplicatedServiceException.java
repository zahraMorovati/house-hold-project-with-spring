package ir.maktab.exception.serviceExceptions;
/**
 * Thrown when application cannot save {@code Service} in database
 */
public class DuplicatedServiceException extends RuntimeException{

    /**
     * Constructs a {@code CannotSaveServiceException} with no detail message.
     */
    public DuplicatedServiceException() {
        super();
    }

    /**
     * Constructs a {@code CannotSaveServiceException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public DuplicatedServiceException(String s) {
        super(s);
    }

}
