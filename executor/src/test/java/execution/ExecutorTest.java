package execution;

import org.junit.Before;
import org.junit.Test;
import utils.HelperFunctions;
import utils.TestUtils;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class ExecutorTest
{
    private static OutputStream mockOutputStream;

    private execution.Executor executor = new execution.Executor();

    @Before
    public void setup()
    {
        mockOutputStream = new ByteArrayOutputStream();
    }

    @Test
    public void testReadInputStreamToString() throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();

        try (FileInputStream fileInputStream =
                     new FileInputStream(classLoader.getResource(TestUtils.TWO_PLUS_FIVE_FILENAME).getFile()))
        {
            assertEquals(TestUtils.TWO_PLUS_FIVE_INPUT, executor.readInputStreamToString(fileInputStream));
        }

        try (FileInputStream fileInputStream =
                     new FileInputStream(classLoader.getResource(TestUtils.HELLO_WORLD_FILENAME).getFile()))
        {
            assertEquals(TestUtils.HELLO_WORLD_INPUT, executor.readInputStreamToString(fileInputStream));
        }
    }

    @Test
    public void testInterpretTwoPlusFiveInput() throws IOException
    {
        try (OutputStream outputStream = executor.interpret(TestUtils.TWO_PLUS_FIVE_INPUT, mockOutputStream))
        {
            assertEquals(TestUtils.TWO_PLUS_FIVE_EXPECTED_OUTPUT, HelperFunctions.convertOutputStreamToString(outputStream));
        }
    }

    @Test
    public void testExecuteTwoPlusFiveFileInput() throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();

        try (FileInputStream fileInputStream =
                     new FileInputStream(classLoader.getResource(TestUtils.TWO_PLUS_FIVE_FILENAME).getFile());
             OutputStream output = executor.execute(fileInputStream, mockOutputStream))
        {
            assertEquals(TestUtils.TWO_PLUS_FIVE_EXPECTED_OUTPUT, HelperFunctions.convertOutputStreamToString(output));
        }
    }

    @Test
    public void testInterpretHelloWorldInput() throws IOException
    {
        try (OutputStream outputStream = executor.interpret(TestUtils.HELLO_WORLD_INPUT, mockOutputStream))
        {
            assertEquals(TestUtils.HELLO_WORLD_EXPECTED_OUTPUT, HelperFunctions.convertOutputStreamToString(outputStream));
        }
    }

    @Test
    public void testExecuteHelloWorldFileInput() throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();

        try (FileInputStream fileInputStream =
                     new FileInputStream(classLoader.getResource(TestUtils.HELLO_WORLD_FILENAME).getFile());
             OutputStream output = executor.execute(fileInputStream, mockOutputStream))
        {
            assertEquals(TestUtils.HELLO_WORLD_EXPECTED_OUTPUT, HelperFunctions.convertOutputStreamToString(output));
        }
    }
}
