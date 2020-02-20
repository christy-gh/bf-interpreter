package utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HelperFunctionsTest
{
    @Test
    public void testEnumValues()
    {
        List<String> brainfuckCharNames = new ArrayList<>();

        for (BrainfuckChar brainfuckChar : BrainfuckChar.values())
        {
            brainfuckCharNames.add(brainfuckChar.name());
        }

        assertNotNull(brainfuckCharNames);
        assertTrue(brainfuckCharNames.size() > 0);

        assertEquals(brainfuckCharNames, HelperFunctions.enumValues(BrainfuckChar.class));
    }
}
