package lexer;

import org.junit.Test;
import utils.BrainfuckChar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class LexerTest
{
    private Lexer lexer = new Lexer();

    // TODO: Change chars to actual enum values.
    private final String VALID_STRIPPED_BRAINFUCK_INPUT = "><+-.,[]";
    private final String VALID_INPUT_WITH_WHITESPACE = "\t\n    \t >\n<  +  - .    \n   , [   ]     \t\n";
    private final String VALID_INPUT_WITH_OTHER_CHARS = "!1q>@2w<#3e+$4r-%5t.^6y,&7u[*8i](9o";
    private final String VALID_INPUT_WITH_WHITESPACE_AND_OTHER_CHARS =
            " \t\n  ! 1   q>  @2   w<#3e  \n+$4r-%5t.^6y ,&7u[*8i]  (9o     \t";

    @Test
    public void testLexerValidStrippedInput() throws IOException
    {
        OutputStream outputStream =
                lexer.stripNonBrainfuckChars(new ByteArrayInputStream(VALID_STRIPPED_BRAINFUCK_INPUT.getBytes()));
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, convertOutputStreamToString(outputStream));
    }

    @Test
    public void testLexerValidInputStringWithSpaces() throws IOException
    {
        OutputStream outputStream =
                lexer.stripNonBrainfuckChars(new ByteArrayInputStream(VALID_INPUT_WITH_WHITESPACE.getBytes()));
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, convertOutputStreamToString(outputStream));
    }

    @Test
    public void testLexerValidInputStringWithOtherChars() throws IOException
    {
        OutputStream outputStream =
                lexer.stripNonBrainfuckChars(new ByteArrayInputStream(VALID_INPUT_WITH_OTHER_CHARS.getBytes()));
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, convertOutputStreamToString(outputStream));
    }

    @Test
    public void testLexerValidInputStringWithWhiteSpaceAndOtherChars() throws IOException
    {
        OutputStream outputStream =
                lexer.stripNonBrainfuckChars(new ByteArrayInputStream(VALID_INPUT_WITH_WHITESPACE_AND_OTHER_CHARS.getBytes()));
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, convertOutputStreamToString(outputStream));
    }

    @Test
    public void testLexerValidChars()
    {
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.INC_POINTER.getValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.DEC_POINTER.getValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.INC_BYTE_AT_POINTER.getValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.DEC_BYTE_AT_POINTER.getValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.OUTPUT_BYTE_AT_POINTER.getValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.INPUT_BYTE_TO_POINTER.getValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.getValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.getValue()));
    }

    @Test
    public void testLexerInvalidChars()
    {
        // Iterate through every possible byte value.
        for (int i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++)
        {
            byte b = (byte)i;
            System.out.println("byte = " + (int)b);

            boolean isLegal = false;

            for (BrainfuckChar brainfuckChar : BrainfuckChar.values())
            {
                System.out.println("brainfuckChar byte value: " + (byte)brainfuckChar.getValue());
                if (b == (byte)brainfuckChar.getValue())
                {
                    isLegal = true;
                }
            }

            assertEquals(isLegal, lexer.isValidBrainfuckChar((char)b));
        }
    }

    /**
     * Converts the returned output stream to a string to compare with the expected value.
     *
     * @param outputStream The output stream returned by the Lexer.
     * @return The string value of the output stream.
     */
    private String convertOutputStreamToString(OutputStream outputStream)
    {
        return new String(((ByteArrayOutputStream)outputStream).toByteArray());
    }
}
