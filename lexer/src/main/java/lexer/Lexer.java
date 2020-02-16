package lexer;

import utils.BrainfuckChar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Lexer class that takes in an input stream anD ignores all whitespace and non Brainfuck recognized characters
 * (as dictated by BrainfuckChar), returning the result as an output stream.
 */
class Lexer
{
    /**
     * Strips any characters not recognized by Brainfuck (as dictated by the BrainfuckChar enum), and returns a
     * legal output for use by the Parser.
     *
     * @param inputStream The input stream provided to the Lexer.
     * @return An Outputstream with the only legal characters.
     * @throws IOException Thrown if there is an issue reading from or closing the input stream.
     */
    OutputStream stripNonBrainfuckChars(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

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
                    // Cast the current byte read into the buffer to a char.
                    char currentChar = (char)buffer[i];

                    // If the char is a valid Brainfuck char, write the byte to the output stream.
                    if (isValidBrainfuckChar(currentChar))
                    {
                        outputStream.write(buffer[i]);
                    }
                }
            }
        }

        return outputStream;
    }

    /**
     * Checks if the given character is a valid Brainfuck character (as dictated by the BrainfuckChar enum).
     *
     * @param currentChar The current char.
     * @return True if the char is recognized, false if not.
     */
    boolean isValidBrainfuckChar(char currentChar)
    {
        for (BrainfuckChar brainfuckChar : BrainfuckChar.values())
        {
            if (brainfuckChar.equals(currentChar))
            {
                return true;
            }
        }

        return false;
    }
}
