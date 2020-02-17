package parser;

/**
 * Wrapper class for exceptions encountered in the preparser.
 */
public class ParserException extends Exception
{
    /**
     * Constructor.
     *
     * @param message The exception message.
     */
    ParserException(String message)
    {
        super(message);
    }

    /**
     * Constructor with a cause.
     *
     * @param message The exception message.
     * @param cause   The cause of the exception.
     */
    ParserException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
