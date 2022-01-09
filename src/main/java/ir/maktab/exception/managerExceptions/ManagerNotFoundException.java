package ir.maktab.exception.managerExceptions;
/**
 * Thrown when application cannot find {@code Manager} in database
 */
public class ManagerNotFoundException extends RuntimeException{

    /**
     * Constructs a {@code ManagerNotFoundException} with no detail message.
     */
    public ManagerNotFoundException() {
        super();
    }

    /**
     * Constructs a {@code ManagerNotFoundException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public ManagerNotFoundException(String s) {
        super(s);
    }
}
