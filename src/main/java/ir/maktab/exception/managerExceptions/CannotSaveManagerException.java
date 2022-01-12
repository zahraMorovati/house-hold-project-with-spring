package ir.maktab.exception.managerExceptions;
/**
 * Thrown when application cannot save {@code Manager} in database
 */
public class CannotSaveManagerException extends RuntimeException{

    /**
     * Constructs a {@code CannotSaveManagerException} with no detail message.
     */
    public CannotSaveManagerException() {
        super();
    }

    /**
     * Constructs a {@code CannotSaveManagerException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public CannotSaveManagerException(String s) {
        super(s);
    }
}
