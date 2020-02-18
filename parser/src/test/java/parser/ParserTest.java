package parser;

import org.junit.Before;
import org.junit.Test;
import utils.HelperFunctions;
import utils.TestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        String invalidCharString = "1+1=2";
        try (OutputStream outputStream = parser.parse(invalidCharString, mockOutputStream))
        {
            fail(TestUtils.EXCEPTION_EXPECTED_MSG);
        }
        catch (ParserException e)
        {
            assertEquals(Parser.PARSER_EXCEPTION_INVALID_CHAR_MSG + invalidCharString.charAt(0), e.getMessage());
        }
    }
}
