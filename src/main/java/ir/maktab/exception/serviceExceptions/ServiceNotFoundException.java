package ir.maktab.exception.serviceExceptions;
/**
 * Thrown when application cannot find {@code Service} in database
 */
public class ServiceNotFoundException extends RuntimeException{

    /**
     * Constructs a {@code ServiceNotFoundException} with no detail message.
     */
    public ServiceNotFoundException() {
        super();
    }

    /**
     * Constructs a {@code ServiceNotFoundException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public ServiceNotFoundException(String s) {
        super(s);
    }
}
