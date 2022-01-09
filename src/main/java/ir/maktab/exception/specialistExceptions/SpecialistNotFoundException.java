package ir.maktab.exception.specialistExceptions;
/**
 * Thrown when application cannot find {@code Specialist} in database
 */
public class SpecialistNotFoundException extends RuntimeException{

    /**
     * Constructs a {@code SpecialistNotFoundException} with no detail message.
     */
    public SpecialistNotFoundException() {
        super();
    }

    /**
     * Constructs a {@code SpecialistNotFoundException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public SpecialistNotFoundException(String s) {
        super(s);
    }
}
