package parser;

import utils.BrainfuckChar;
import utils.HelperFunctions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Class for parsing valid Brainfuck input.
 */
public class Parser
{
    /**
     * Check if the input is valid.
     *
     * @param inputString The input string.
     * @return True if there were no errors parsing the
     * @throws ParserException Thrown if an exception is encountered when parsing or if the parsed output doesn't match
     *                         the input.
     */
    boolean isInputValidBrainfuck(String inputString) throws ParserException
    {
        OutputStream parsedOutputStream = parse(inputString, new ByteArrayOutputStream());

        // If the parsed output stream was equal to the input stream, return true. Otherwise, throw an exception.
        if (inputString.equals(HelperFunctions.convertOutputStreamToString(parsedOutputStream)))
        {
            return true;
        }
        else
        {
            throw new ParserException("Parsed output did not match input.");
        }
    }

    /**
     * Parses the input as a string and writes to an output stream.
     *
     * @param inputString  The Brainfuck input as a string.
     * @param outputStream The output stream for the parser to write output to.
     * @return The parser output stream.
     * @throws ParserException Thrown if there is an issue writing to the output stream, or if any parser exceptions are
     *                         thrown.
     */
    OutputStream parse(String inputString, OutputStream outputStream) throws ParserException
    {
        for (int i = 0; i < inputString.length(); i++)
        {
            if (inputString.charAt(i) == BrainfuckChar.INC_POINTER.getCharValue()
                    || inputString.charAt(i) == BrainfuckChar.DEC_POINTER.getCharValue()
                    || inputString.charAt(i) == BrainfuckChar.INC_BYTE_AT_POINTER.getCharValue()
                    || inputString.charAt(i) == BrainfuckChar.DEC_BYTE_AT_POINTER.getCharValue()
                    || inputString.charAt(i) == BrainfuckChar.OUTPUT_BYTE_AT_POINTER.getCharValue()
                    || inputString.charAt(i) == BrainfuckChar.INPUT_BYTE_TO_POINTER.getCharValue())
            {
                try
                {
                    // Write the character to the output stream as it is valid and requires no further validation.
                    outputStream.write(inputString.charAt(i));
                }
                catch (IOException e)
                {
                    throw new ParserException("Couldn't write character at position: " + i + " to output stream.");
                }
            }
            else if (inputString.charAt(i) == BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.getCharValue())
            {
                // Set a bracket index to the current index + 1;
                int bracketIndex = i + 1;

                // Set the number of unmatched brackets to 1;
                int unmatchedBrackets = 1;

                // While there are still unmatched brackets:
                while (unmatchedBrackets > 0 && bracketIndex > inputString.length() - 2)
                {
                    // Increment the bracket index by 1.
                    bracketIndex++;

                    // If the character at the bracketIndex is '[':
                    if (inputString.charAt(bracketIndex) == BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.getCharValue())
                    {
                        // Increment the number of unmatched brackets.
                        unmatchedBrackets++;
                    }
                    // Otherwise if the character at the bracketIndex is ']':
                    else if (inputString.charAt(bracketIndex) ==
                            BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.getCharValue())
                    {
                        // Decrement the number of unmatched brackets by 1.
                        unmatchedBrackets--;
                    }
                }

                // Set a loop string to be the substring from the first open bracket to the last closed bracket.
                String loopString = inputString.substring(i, bracketIndex);

                // If there are no more unmatched brackets:
                if (unmatchedBrackets != 0)
                {
                    // Parse the loop string.
                    OutputStream loopOutputStream = parse(loopString, outputStream);

                    // Append output of above to current output stream.
                    outputStream = appendLoopOutputToParserOutput(outputStream, loopOutputStream);

                    // Set the index to the end of the loop, which causes the parent parse to skip it.
                    i = bracketIndex;
                }
                else
                {
                    // Throw an exception as there are more open bracket characters than closed bracket characters in
                    // the above loop.
                    throw new ParserException("Incorrect number of open brackets detected in loop: " + loopString);
                }
            }
            else if (inputString.charAt(i) == BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.getCharValue())
            {
                // Above case means this should never be reached with valid brackets. Throw an exception as there
                // has been a closed bracket without a preceding open bracket.
                throw new ParserException("Invalid closed bracket detected at position " + i);
            }
            else
            {
                // Throw an exception as the character is unrecognized, and the Parser only accepts valid Brainfuck
                // characters.
                throw new ParserException("Invalid Brainfuck character detected: " + inputString.charAt(i));
            }
        }

        return outputStream;
    }

    /**
     * Appends the loop output to the current output stream.
     *
     * @param outputStream The output stream that created the loop.
     * @param loopOutputStream The loop output stream.
     * @return The loop output stream appended to the parent output stream.
     * @throws ParserException Thrown if there is an issue writing to an output stream.
     */
    OutputStream appendLoopOutputToParserOutput(OutputStream outputStream, OutputStream loopOutputStream)
            throws ParserException
    {
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream)loopOutputStream;

        try
        {
            byteArrayOutputStream.writeTo(outputStream);
        }
        catch (IOException e)
        {
            throw new ParserException("Couldn't append loop output stream to parent output stream.", e.getCause());
        }

        return outputStream;
    }
}
