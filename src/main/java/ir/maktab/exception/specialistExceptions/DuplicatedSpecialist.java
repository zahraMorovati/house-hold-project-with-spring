package ir.maktab.exception.specialistExceptions;
/**
 * Thrown when application find duplicated {@code Specialist} in subService Specialists list
 */
public class DuplicatedSpecialist extends RuntimeException{

        /**
         * Constructs a {@code DuplicatedSpecialist} with no detail message.
         */
        public DuplicatedSpecialist() {
            super();
        }

        /**
         * Constructs a {@code DuplicatedSpecialist} with the specified
         * detail message.
         *
         * @param   s   the detail message.
         */
        public DuplicatedSpecialist(String s) {
            super(s);
        }
}
