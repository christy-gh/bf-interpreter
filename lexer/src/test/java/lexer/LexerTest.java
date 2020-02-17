package lexer;

import org.junit.Test;
import utils.BrainfuckChar;
import utils.HelperFunctions;

import java.io.ByteArrayInputStream;
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
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, HelperFunctions.convertOutputStreamToString(outputStream));
    }

    @Test
    public void testLexerValidInputStringWithSpaces() throws IOException
    {
        OutputStream outputStream =
                lexer.stripNonBrainfuckChars(new ByteArrayInputStream(VALID_INPUT_WITH_WHITESPACE.getBytes()));
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, HelperFunctions.convertOutputStreamToString(outputStream));
    }

    @Test
    public void testLexerValidInputStringWithOtherChars() throws IOException
    {
        OutputStream outputStream =
                lexer.stripNonBrainfuckChars(new ByteArrayInputStream(VALID_INPUT_WITH_OTHER_CHARS.getBytes()));
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, HelperFunctions.convertOutputStreamToString(outputStream));
    }

    @Test
    public void testLexerValidInputStringWithWhiteSpaceAndOtherChars() throws IOException
    {
        OutputStream outputStream =
                lexer.stripNonBrainfuckChars(new ByteArrayInputStream(VALID_INPUT_WITH_WHITESPACE_AND_OTHER_CHARS
                        .getBytes()));
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, HelperFunctions.convertOutputStreamToString(outputStream));
    }

    @Test
    public void testLexerValidChars()
    {
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.INC_POINTER.getCharValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.DEC_POINTER.getCharValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.INC_BYTE_AT_POINTER.getCharValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.DEC_BYTE_AT_POINTER.getCharValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.OUTPUT_BYTE_AT_POINTER.getCharValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.INPUT_BYTE_TO_POINTER.getCharValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.getCharValue()));
        assertTrue(lexer.isValidBrainfuckChar(BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.getCharValue()));
    }

    @Test
    public void testLexerAllBytes()
    {
        // Iterate through every possible byte value.
        for (int i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++)
        {
            byte b = (byte)i;
            System.out.println("byte = " + (int)b);

            boolean isLegal = false;

            for (BrainfuckChar brainfuckChar : BrainfuckChar.values())
            {
                System.out.println("brainfuckChar byte value: " + brainfuckChar.getByteValue());
                if (b == brainfuckChar.getByteValue())
                {
                    isLegal = true;
                }
            }

            assertEquals(isLegal, lexer.isValidBrainfuckChar((char)b));
        }
    }
}
