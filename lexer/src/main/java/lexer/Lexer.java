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
    OutputStream stripNonBrainfuckChars(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int read;

        try (inputStream)
        {
            // Iterate through the bytes of the input stream.
            while ((read = inputStream.read(buffer)) != -1)
            {
                for (int i = 0; i < read; i++)
                {
                    char currentChar = (char)buffer[i];

                    if (isValidBrainfuckChar(currentChar))
                    {
                        outputStream.write(buffer[i]);
                    }
                }
            }
        }

        return outputStream;
    }

    private boolean isValidBrainfuckChar(char currentChar)
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
