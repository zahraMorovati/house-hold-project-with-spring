package ir.maktab.exception.suggestionExceptions;

public class SuggestionNotFoundException extends RuntimeException{

    /**
     * Constructs a {@code SuggestionNotFoundException} with no detail message.
     */
    public SuggestionNotFoundException() {
        super();
    }

    /**
     * Constructs a {@code SuggestionNotFoundException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public SuggestionNotFoundException(String s) {
        super(s);
    }
}
