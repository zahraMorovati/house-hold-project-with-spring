package ir.maktab.exception.customerExceptions;
/**
 * Thrown when application cannot find {@code Customer} in database
 */
public class CustomerNotFoundException extends RuntimeException{

    /**
     * Constructs a {@code CustomerNotFoundException} with no detail message.
     */
    public CustomerNotFoundException() {
        super();
    }

    /**
     * Constructs a {@code CustomerNotFoundException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public CustomerNotFoundException(String s) {
        super(s);
    }

}
