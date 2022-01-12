package ir.maktab.exception.suggestionExceptions;

public class EmptySuggestionList extends RuntimeException{

    /**
     * Constructs a {@code EmptySuggestionList} with no detail message.
     */
    public EmptySuggestionList() {
        super();
    }

    /**
     * Constructs a {@code EmptySuggestionList} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public EmptySuggestionList(String s) {
        super(s);
    }
}
