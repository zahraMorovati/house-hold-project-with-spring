package ir.maktab.exception.orderExceptions;
/**
 * Thrown when application cannot save {@code Order} in database
 */
public class CannotSaveOrderException extends RuntimeException{

    /**
     * Constructs a {@code CannotSaveOrderException} with no detail message.
     */
    public CannotSaveOrderException() {
        super();
    }

    /**
     * Constructs a {@code CannotSaveOrderException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public CannotSaveOrderException(String s) {
        super(s);
    }
}
