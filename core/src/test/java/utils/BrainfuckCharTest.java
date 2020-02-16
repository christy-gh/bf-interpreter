package utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class BrainfuckCharTest
{
    private final char TEST_INC_POINTER = '>';
    private final char TEST_DEC_POINTER = '<';
    private final char TEST_INC_BYTE_AT_POINTER = '+';
    private final char TEST_DEC_BYTE_AT_POINTER = '-';
    private final char TEST_OUTPUT_BYTE_AT_POINTER = '.';
    private final char TEST_INPUT_BYTE_TO_POINTER = ',';
    private final char TEST_JUMP_FORWARD_IF_BYTE_IS_ZERO = '[';
    private final char TEST_JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO = ']';

    private final char INVALID_INC_POINTER = TEST_DEC_POINTER;
    private final char INVALID_DEC_POINTER = TEST_INC_POINTER;
    private final char INVALID_INC_BYTE_AT_POINTER = TEST_DEC_BYTE_AT_POINTER;
    private final char INVALID_DEC_BYTE_AT_POINTER = TEST_INC_BYTE_AT_POINTER;
    private final char INVALID_OUTPUT_BYTE_AT_POINTER = TEST_INPUT_BYTE_TO_POINTER;
    private final char INVALID_INPUT_BYTE_TO_POINTER = TEST_OUTPUT_BYTE_AT_POINTER;
    private final char INVALID_JUMP_FORWARD_IF_BYTE_IS_ZERO = TEST_JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO;
    private final char INVALID_JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO = TEST_JUMP_FORWARD_IF_BYTE_IS_ZERO;


    @Test
    public void testValidBrainfuckCharEquals()
    {
        assertTrue(BrainfuckChar.INC_POINTER.equals(TEST_INC_POINTER));
        assertTrue(BrainfuckChar.DEC_POINTER.equals(TEST_DEC_POINTER));
        assertTrue(BrainfuckChar.INC_BYTE_AT_POINTER.equals(TEST_INC_BYTE_AT_POINTER));
        assertTrue(BrainfuckChar.DEC_BYTE_AT_POINTER.equals(TEST_DEC_BYTE_AT_POINTER));
        assertTrue(BrainfuckChar.OUTPUT_BYTE_AT_POINTER.equals(TEST_OUTPUT_BYTE_AT_POINTER));
        assertTrue(BrainfuckChar.INPUT_BYTE_TO_POINTER.equals(TEST_INPUT_BYTE_TO_POINTER));
        assertTrue(BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.equals(TEST_JUMP_FORWARD_IF_BYTE_IS_ZERO));
        assertTrue(BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.equals(TEST_JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO));
    }

    @Test
    public void testInvalidBrainfuckCharEquals()
    {
        assertFalse(BrainfuckChar.INC_POINTER.equals(INVALID_INC_POINTER));
        assertFalse(BrainfuckChar.DEC_POINTER.equals(INVALID_DEC_POINTER));
        assertFalse(BrainfuckChar.INC_BYTE_AT_POINTER.equals(INVALID_INC_BYTE_AT_POINTER));
        assertFalse(BrainfuckChar.DEC_BYTE_AT_POINTER.equals(INVALID_DEC_BYTE_AT_POINTER));
        assertFalse(BrainfuckChar.OUTPUT_BYTE_AT_POINTER.equals(INVALID_OUTPUT_BYTE_AT_POINTER));
        assertFalse(BrainfuckChar.INPUT_BYTE_TO_POINTER.equals(INVALID_INPUT_BYTE_TO_POINTER));
        assertFalse(BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.equals(INVALID_JUMP_FORWARD_IF_BYTE_IS_ZERO));
        assertFalse(BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.equals(INVALID_JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO));
    }

    @Test
    public void testGetValue()
    {
        assertEquals(TEST_INC_POINTER, BrainfuckChar.INC_POINTER.getValue());
        assertEquals(TEST_DEC_POINTER, BrainfuckChar.DEC_POINTER.getValue());
        assertEquals(TEST_INC_BYTE_AT_POINTER, BrainfuckChar.INC_BYTE_AT_POINTER.getValue());
        assertEquals(TEST_DEC_BYTE_AT_POINTER, BrainfuckChar.DEC_BYTE_AT_POINTER.getValue());
        assertEquals(TEST_OUTPUT_BYTE_AT_POINTER, BrainfuckChar.OUTPUT_BYTE_AT_POINTER.getValue());
        assertEquals(TEST_INPUT_BYTE_TO_POINTER, BrainfuckChar.INPUT_BYTE_TO_POINTER.getValue());
        assertEquals(TEST_JUMP_FORWARD_IF_BYTE_IS_ZERO, BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.getValue());
        assertEquals(TEST_JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO, BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO
                .getValue());
    }
}
