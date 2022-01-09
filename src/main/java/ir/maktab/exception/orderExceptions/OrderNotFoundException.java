package ir.maktab.exception.orderExceptions;
/**
 * Thrown when application cannot find {@code Order} in database
 */
public class OrderNotFoundException extends RuntimeException{

    /**
     * Constructs a {@code OrderNotFoundException} with no detail message.
     */
    public OrderNotFoundException() {
        super();
    }

    /**
     * Constructs a {@code OrderNotFoundException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public OrderNotFoundException(String s) {
        super(s);
    }
}
