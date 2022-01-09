package ir.maktab.exception.UserEceptions;
/**
 * Thrown when user enter wrong email
 */
public class WrongEmailException extends RuntimeException{
    /**
     * Constructs a {@code WrongEmailException} with no detail message.
     */
    public WrongEmailException() {
        super();
    }

    /**
     * Constructs a {@code WrongEmailException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public WrongEmailException(String s) {
        super(s);
    }
}
