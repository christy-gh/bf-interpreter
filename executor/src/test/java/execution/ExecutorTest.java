package execution;

import org.junit.Assert;
import org.junit.Test;

public class ExecutorTest {
    private execution.Executor executor = new execution.Executor();

    @Test
    public void testExecutorExecute()
    {
        Assert.assertNotNull(executor.execute());
        Assert.assertEquals(Executor.HELLO_WORLD, executor.execute());
    }
}
