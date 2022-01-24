package ir.maktab.exception.subServiceExceptions;
/**
 * Thrown when application cannot save duplicated {@code SubService} in database
 */
public class DuplicatedSubServiceException extends RuntimeException{
    /**
     * Constructs a {@code DuplicatedSubServiceException} with no detail message.
     */
    public DuplicatedSubServiceException() {
        super();
    }

    /**
     * Constructs a {@code DuplicatedSubServiceException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public DuplicatedSubServiceException(String s) {
        super(s);
    }
}
