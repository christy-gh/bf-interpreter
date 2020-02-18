package parser;

import org.junit.Before;
import org.junit.Test;
import utils.HelperFunctions;
import utils.TestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class ParserTest
{
    private Parser parser = new Parser();
    private static OutputStream mockOutputStream;

    @Before
    public void setup()
    {
        mockOutputStream = new ByteArrayOutputStream();
    }

    @Test
    public void testIsValidTwoPlusFiveInput() throws ParserException
    {
        assertTrue(parser.isInputValidBrainfuck(TestUtils.TWO_PLUS_FIVE_INPUT));
    }

    @Test
    public void testIsValidHelloWorldInput() throws ParserException
    {
        assertTrue(parser.isInputValidBrainfuck(TestUtils.HELLO_WORLD_INPUT));
    }

    @Test
    public void testParseTwoPlusFiveInput() throws ParserException, IOException
    {
        try (OutputStream outputStream = parser.parse(TestUtils.TWO_PLUS_FIVE_INPUT, mockOutputStream))
        {
            assertEquals(TestUtils.TWO_PLUS_FIVE_INPUT, HelperFunctions.convertOutputStreamToString(outputStream));
        }
    }

    @Test
    public void testParseHelloWorldInput() throws ParserException, IOException
    {
        try (OutputStream outputStream = parser.parse(TestUtils.HELLO_WORLD_INPUT, mockOutputStream))
        {
            assertEquals(TestUtils.HELLO_WORLD_INPUT, HelperFunctions.convertOutputStreamToString(outputStream));
        }
    }

    // Parser exceptions and exception messages.

    @Test
    public void testParseInvalidCharInput() throws IOException
    {
        // Position 0:
        String invalidInput = "1+1=2";
        parseExpectFail(invalidInput, Parser.PARSER_EXCEPTION_INVALID_CHAR_MSG, String.valueOf(invalidInput.charAt(0)));

        // Later position:
        invalidInput = "++>++.-.Q-++";
        parseExpectFail(invalidInput, Parser.PARSER_EXCEPTION_INVALID_CHAR_MSG, String.valueOf(invalidInput.charAt(8)));
    }

    @Test
    public void testParseInvalidBracketMatchCount() throws IOException
    {
        // Too many open brackets:
        String invalidInput = "++[++[++]++";
        parseExpectFail(invalidInput, Parser.PARSER_EXCEPTION_INVALID_OPEN_BRACKETS_MSG, invalidInput.substring(2));

        // Too many closed brackets:
        invalidInput = "++[++]++]++";
        parseExpectFail(invalidInput, Parser.PARSER_EXCEPTION_INVALID_CLOSED_BRACKETS_MSG, "8");
    }

    @Test
    public void testParseInvalidBracketPosition() throws IOException
    {
        // Closed bracket before all open brackets:
        String invalidInput = "++]++[++[++]++";
        parseExpectFail(invalidInput, Parser.PARSER_EXCEPTION_INVALID_CLOSED_BRACKETS_MSG, "2");

        // Open bracket after all closing brackets:
        invalidInput = "++[++]++]++[++";
        parseExpectFail(invalidInput, Parser.PARSER_EXCEPTION_INVALID_CLOSED_BRACKETS_MSG, "8");
    }

    // TODO: Add mockito tests for throwing output stream writing exceptions.

    /**
     * Parse the input expecting an exception with the expected message that highlights the info (char/substring/index)
     * responsible at the end.
     */
    private void parseExpectFail(String input, String expectedExceptionMessage, String extraInfo) throws IOException
    {
        try (OutputStream outputStream = parser.parse(input, mockOutputStream))
        {
            fail(TestUtils.EXCEPTION_EXPECTED_MSG);
        }
        catch (ParserException e)
        {
            assertEquals(expectedExceptionMessage + extraInfo, e.getMessage());
        }
    }
}
