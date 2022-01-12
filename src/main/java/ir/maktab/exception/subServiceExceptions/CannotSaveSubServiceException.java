package ir.maktab.exception.subServiceExceptions;
/**
 * Thrown when application cannot save {@code SubService} in database
 */
public class CannotSaveSubServiceException extends RuntimeException{
    /**
     * Constructs a {@code CannotSaveSubServiceException} with no detail message.
     */
    public CannotSaveSubServiceException() {
        super();
    }

    /**
     * Constructs a {@code CannotSaveSubServiceException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public CannotSaveSubServiceException(String s) {
        super(s);
    }
}
