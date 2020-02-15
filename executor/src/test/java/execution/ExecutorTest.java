package execution;

import org.junit.Assert;
import org.junit.Test;

public class ExecutorTest {
    private execution.Executor executor = new execution.Executor();

    /**
     * TEMPORARY test for correct maven setup.
     */
    @Test
    public void testCorrectSetup()
    {
        Assert.assertNotNull(executor.setup());
        Assert.assertEquals(Executor.HELLO_WORLD, executor.setup());
    }
}
