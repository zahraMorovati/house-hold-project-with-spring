package ir.maktab.exception.customerExceptions;
/**
 * Thrown when {@code Customer} is not enough to pay the order
 */
public class BalanceIsNotEnoughException extends RuntimeException{

    /**
     * Constructs a {@code BalanceIsNotEnoughException} with no detail message.
     */
    public BalanceIsNotEnoughException() {
        super();
    }

    /**
     * Constructs a {@code BalanceIsNotEnoughException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public BalanceIsNotEnoughException(String s) {
        super(s);
    }
}
