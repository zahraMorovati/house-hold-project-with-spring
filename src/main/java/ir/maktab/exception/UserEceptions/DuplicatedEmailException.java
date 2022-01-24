package ir.maktab.exception.UserEceptions;
/**
 * Thrown when user enter duplicated email
 */
public class DuplicatedEmailException extends RuntimeException{
    /**
     * Constructs a {@code DuplicatedEmailException} with no detail message.
     */
    public DuplicatedEmailException() {
        super();
    }

    /**
     * Constructs a {@code DuplicatedEmailException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public DuplicatedEmailException(String s) {
        super(s);
    }
}
