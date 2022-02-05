package ir.maktab.exception.UserEceptions;
/**
 * Thrown when user not confirmed
 */
public class UserNotConfirmedException extends RuntimeException{
    /**
     * Constructs a {@code UserNotConfirmedException} with no detail message.
     */
    public UserNotConfirmedException() {
        super();
    }

    /**
     * Constructs a {@code UserNotConfirmedException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public UserNotConfirmedException(String s) {
        super(s);
    }
}
