package lexer;

/**
 * Wrapper class for exceptions encountered in the Lexer.
 */
public class LexerException extends Exception
{
    /**
     * Constructor.
     *
     * @param message The exception message.
     */
    LexerException(String message)
    {
        super(message);
    }

    /**
     * Constructor with a cause.
     *
     * @param message The exception message.
     * @param cause   The cause of the exception.
     */
    LexerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
