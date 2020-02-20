package lexer;

import tokens.Token;
import utils.HelperFunctions;
import utils.LanguageChar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Lexer class that takes in an input stream anD ignores all whitespace and non Brainfuck recognized characters
 * (as dictated by BrainfuckChar), returning the result as an output stream.
 */
class Lexer<T extends Enum<T> & LanguageChar>
{
    private Class<T> languageCharSetClass;
    private List<Token> tokens = new ArrayList<>();

    Lexer(Class<T> languageCharSetClass)
    {
        this.languageCharSetClass = languageCharSetClass;
    }

    /**
     * Strips any characters not recognized by Brainfuck (as dictated by the BrainfuckChar enum), and returns a
     * legal output for use by the Parser.
     *
     * @param inputStream The input stream provided to the Lexer.
     * @return An Outputstream with the only legal characters.
     * @throws LexerException Thrown if there is an issue reading from or closing the input stream.
     */
    List<Token> stripIllegalChars(InputStream inputStream) throws LexerException
    {
        byte[] buffer = new byte[1024];
        int read;

        try (inputStream)
        {
            // Iterate through the bytes of the input stream via a buffer.
            while ((read = inputStream.read(buffer)) != -1)
            {
                // Iterate through each byte that was read in the buffer.
                for (int i = 0; i < read; i++)
                {
                    // If the char is a valid Brainfuck char, create a token and add it to the list of returned tokens.
                    processChar((char)buffer[i]);
                }
            }
        }
        catch (IOException e)
        {
            throw new LexerException("Couldn't read or close from the input stream.", e);
        }

        return tokens;
    }

    private void processChar(char inputChar)
    {
        T languageChar = getLanguageChar(inputChar, languageCharSetClass);

        if (languageChar != null)
        {
            tokens.add(HelperFunctions.charToToken(languageChar));
        }
    }

    T getLanguageChar(char inputChar, Class<T> languageCharSetClass)
    {
        return HelperFunctions.charToLanguageChar(inputChar, languageCharSetClass);
    }
}
