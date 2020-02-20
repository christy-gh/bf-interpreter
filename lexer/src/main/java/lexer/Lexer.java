package lexer;

import tokens.Token;
import utils.BrainfuckChar;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Lexer class that takes in an input stream anD ignores all whitespace and non Brainfuck recognized characters
 * (as dictated by BrainfuckChar), returning the result as an output stream.
 */
class Lexer
{
    private List<Token> tokens = new ArrayList<>();

    /**
     * Strips any characters not recognized by Brainfuck (as dictated by the BrainfuckChar enum), and returns a
     * legal output for use by the Parser.
     *
     * @param inputStream The input stream provided to the Lexer.
     * @return An Outputstream with the only legal characters.
     * @throws LexerException Thrown if there is an issue reading from or closing the input stream.
     */
    List<Token> stripNonBrainfuckChars(InputStream inputStream) throws LexerException
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

    void processChar(char inputChar)
    {
        BrainfuckChar brainfuckChar = getLanguageChar(inputChar);

        if (brainfuckChar != null)
        {
            tokens.add(validCharToToken(brainfuckChar));
        }
    }

    BrainfuckChar getLanguageChar(char inputChar)
    {
        for (BrainfuckChar brainfuckChar : BrainfuckChar.values())
        {
            if (brainfuckChar.equals(inputChar))
            {
                return brainfuckChar;
            }
        }

        return null;
    }

    /**
     * Checks if the given character is a valid Brainfuck character (as dictated by the BrainfuckChar enum).
     *
     * @param validChar The valid Brainfuck character.
     * @return A new token with the associated name of the char, and the char itself.
     */
    Token validCharToToken(BrainfuckChar validChar)
    {
        return new Token(validChar.name(), validChar.getCharValue());
    }
}
