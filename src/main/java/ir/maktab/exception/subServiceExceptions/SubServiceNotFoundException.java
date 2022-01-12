package ir.maktab.exception.subServiceExceptions;
/**
 * Thrown when application cannot find {@code SubService} in database
 */
public class SubServiceNotFoundException extends RuntimeException{

    /**
     * Constructs a {@code SubServiceNotFoundException} with no detail message.
     */
    public SubServiceNotFoundException() {
        super();
    }

    /**
     * Constructs a {@code SubServiceNotFoundException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public SubServiceNotFoundException(String s) {
        super(s);
    }
}
