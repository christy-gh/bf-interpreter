package lexer;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LexerTest
{
    private Lexer lexer = new Lexer();
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
        Assert.assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, convertOutputStreamToString(outputStream));
    }

    @Test
    public void testLexerValidInputStringWithSpaces() throws IOException
    {
        OutputStream outputStream =
                lexer.stripNonBrainfuckChars(new ByteArrayInputStream(VALID_INPUT_WITH_WHITESPACE.getBytes()));
        Assert.assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, convertOutputStreamToString(outputStream));
    }

    @Test
    public void testLexerValidInputStringWithOtherChars() throws IOException
    {
        OutputStream outputStream =
                lexer.stripNonBrainfuckChars(new ByteArrayInputStream(VALID_INPUT_WITH_OTHER_CHARS.getBytes()));
        Assert.assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, convertOutputStreamToString(outputStream));
    }

    @Test
    public void testLexerValidInputStringWithWhiteSpaceAndOtherChars() throws IOException
    {
        OutputStream outputStream =
                lexer.stripNonBrainfuckChars(new ByteArrayInputStream(VALID_INPUT_WITH_WHITESPACE_AND_OTHER_CHARS.getBytes()));
        Assert.assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT, convertOutputStreamToString(outputStream));
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
