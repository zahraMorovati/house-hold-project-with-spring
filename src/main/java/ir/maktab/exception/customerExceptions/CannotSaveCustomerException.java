package ir.maktab.exception.customerExceptions;
/**
 * Thrown when application cannot save {@code Customer} in database
 */
public class CannotSaveCustomerException extends RuntimeException{

    /**
     * Constructs a {@code CannotSaveCustomerException} with no detail message.
     */
    public CannotSaveCustomerException() {
        super();
    }

    /**
     * Constructs a {@code CannotSaveCustomerException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public CannotSaveCustomerException(String s) {
        super(s);
    }
}
