package utils;

/**
 * Enum for Brainfuck characters, primarily for readability.
 */
public enum BrainfuckChar
{
    INC_POINTER('>'),
    DEC_POINTER('<'),
    INC_BYTE_AT_POINTER('+'),
    DEC_BYTE_AT_POINTER('-'),
    OUTPUT_BYTE_AT_POINTER('.'),
    INPUT_BYTE_TO_POINTER(','),
    JUMP_FORWARD_IF_BYTE_IS_ZERO('['),
    JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO(']');

    private char value;

    /**
     * Constructor to give enum char values.
     *
     * @param assignedChar The char to assign to the enum entry.
     */
    BrainfuckChar(char assignedChar)
    {
        value = assignedChar;
    }

    /**
     * Check to validate a char equals the enum entry.
     *
     * @param checkValue The value to validate against the enum value.
     * @return True if the value is valid, false if not.
     */
    public boolean equals(char checkValue)
    {
        return value == checkValue;
    }

    /**
     * Returns the char value of an enum entry.
     *
     * @return The char value of the enum entry.
     */
    public char getValue()
    {
        return value;
    }
}
