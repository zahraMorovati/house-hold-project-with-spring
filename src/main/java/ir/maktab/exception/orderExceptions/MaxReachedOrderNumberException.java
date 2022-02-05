package ir.maktab.exception.orderExceptions;
/**
 * Thrown when customer want to save too many {@code Order} in database
 */
public class MaxReachedOrderNumberException extends RuntimeException{

    /**
     * Constructs a {@code MaxReachedOrderNumberException} with no detail message.
     */
    public MaxReachedOrderNumberException() {
        super();
    }

    /**
     * Constructs a {@code MaxReachedOrderNumberException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public MaxReachedOrderNumberException(String s) {
        super(s);
    }
}
