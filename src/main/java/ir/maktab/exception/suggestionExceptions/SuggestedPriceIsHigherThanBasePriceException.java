package ir.maktab.exception.suggestionExceptions;

public class SuggestedPriceIsHigherThanBasePriceException extends RuntimeException{

    /**
     * Constructs a {@code SuggestedPriceIsHigherThanBasePriceException} with no detail message.
     */
    public SuggestedPriceIsHigherThanBasePriceException() {
        super();
    }

    /**
     * Constructs a {@code SuggestedPriceIsHigherThanBasePriceException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public SuggestedPriceIsHigherThanBasePriceException(String s) {
        super(s);
    }
}
