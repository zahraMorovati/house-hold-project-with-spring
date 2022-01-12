package ir.maktab.exception.specialistExceptions;
/**
 * Thrown when application cannot save {@code Specialist} in database
 */
public class CannotSaveSpecialistException extends RuntimeException{

    /**
     * Constructs a {@code CannotSaveSpecialistException} with no detail message.
     */
    public CannotSaveSpecialistException() {
        super();
    }

    /**
     * Constructs a {@code CannotSaveSpecialistException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public CannotSaveSpecialistException(String s) {
        super(s);
    }
}
