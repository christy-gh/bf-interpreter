package execution;

import org.junit.Before;
import org.junit.Test;
import utils.TestUtils;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class ExecutorTest
{
    private static final String TWO_PLUS_FIVE_INPUT = "++>+++++[<+>-]++++++++[<++++++>-]<.";
    private static final String TWO_PLUS_FIVE_EXPECTED_OUTPUT = "7";
    private static final String TWO_PLUS_FIVE_FILENAME = "two_plus_five_executor_input.txt";
    private static final String HELLO_WORLD_EXPECTED_OUTPUT = "Hello World!\n";
    private static final String HELLO_WORLD_INPUT = "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---."
            + "+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.";
    private static final String HELLO_WORLD_FILENAME = "hello_world_executor_input.txt";

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
                     new FileInputStream(classLoader.getResource(TWO_PLUS_FIVE_FILENAME).getFile()))
        {
            assertEquals(TWO_PLUS_FIVE_INPUT, executor.readInputStreamToString(fileInputStream));
        }

        try (FileInputStream fileInputStream =
                     new FileInputStream(classLoader.getResource(HELLO_WORLD_FILENAME).getFile()))
        {
            assertEquals(HELLO_WORLD_INPUT, executor.readInputStreamToString(fileInputStream));
        }
    }

    @Test
    public void testInterpretTwoPlusFiveInput() throws IOException
    {
        try (OutputStream outputStream = executor.interpret(TWO_PLUS_FIVE_INPUT, mockOutputStream))
        {
            assertEquals(TWO_PLUS_FIVE_EXPECTED_OUTPUT, TestUtils.convertOutputStreamToString(outputStream));
        }
    }

    @Test
    public void testExecuteTwoPlusFiveFileInput() throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();

        try (FileInputStream fileInputStream =
                     new FileInputStream(classLoader.getResource(TWO_PLUS_FIVE_FILENAME).getFile());
             OutputStream output = executor.execute(fileInputStream, mockOutputStream))
        {
            assertEquals(TWO_PLUS_FIVE_EXPECTED_OUTPUT, TestUtils.convertOutputStreamToString(output));
        }
    }

    @Test
    public void testInterpretHelloWorldInput() throws IOException
    {
        try (OutputStream outputStream = executor.interpret(HELLO_WORLD_INPUT, mockOutputStream))
        {
            assertEquals(HELLO_WORLD_EXPECTED_OUTPUT, TestUtils.convertOutputStreamToString(outputStream));
        }
    }

    @Test
    public void testExecuteHelloWorldFileInput() throws IOException
    {
        ClassLoader classLoader = getClass().getClassLoader();

        try (FileInputStream fileInputStream =
                     new FileInputStream(classLoader.getResource(HELLO_WORLD_FILENAME).getFile());
             OutputStream output = executor.execute(fileInputStream, mockOutputStream))
        {
            assertEquals(HELLO_WORLD_EXPECTED_OUTPUT, TestUtils.convertOutputStreamToString(output));
        }
    }
}
