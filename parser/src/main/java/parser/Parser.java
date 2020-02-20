package parser;

import tokens.Token;
import utils.BrainfuckChar;
import utils.HelperFunctions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Class for parsing valid Brainfuck input.
 */
public class Parser
{
    static final String PARSER_EXCEPTION_WRITE_CHAR_MSG = "Couldn't write character at position: ";
    static final String PARSER_EXCEPTION_WRITE_SUBSTRING_MSG = "Couldn't write sub string: ";
    static final String PARSER_EXCEPTION_INVALID_OPEN_BRACKETS_MSG =
            "Incorrect number of open brackets detected in loop: ";
    static final String PARSER_EXCEPTION_INVALID_CLOSED_BRACKETS_MSG = "Invalid closed bracket detected at position: ";
    static final String PARSER_EXCEPTION_INVALID_CHAR_MSG = "Invalid closed bracket detected at position: ";

    /**
     * Check if the input is valid.
     *
     * @param tokens The input tokens.
     * @return True if there were no exceptions parsing the input and the parsed output matched the input, false if
     *         there were no exceptions and the parsed output did not match the input.
     * @throws ParserException Thrown if an exception is encountered when parsing or if the parsed output doesn't match
     *                         the input.
     */
    boolean isInputValidBrainfuck(List<Token> tokens) throws ParserException
    {
        String tokenString = "";

        // Iterate through the tokens and create a string of their characters.
        for (Token token : tokens) {
            tokenString = tokenString.concat(String.valueOf(token.getCharacter()));
        }

        OutputStream parsedOutputStream = parse(tokenString, new ByteArrayOutputStream());

        // Check if the output from parse matches the char values of the tokens. If no match, throw an exception.
        if (tokenString.equals(HelperFunctions.convertOutputStreamToString(parsedOutputStream)))
        {
            return true;
        }
        else
        {
            throw new ParserException("Parsed output did not match token char input.");
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
                    throw new ParserException(PARSER_EXCEPTION_WRITE_CHAR_MSG + i);
                }
            }
            else if (inputString.charAt(i) == BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.getCharValue())
            {
                // Set a bracket index to the current index + 1;
                int bracketIndex = i;

                // Set the number of unmatched brackets to 1;
                int unmatchedBrackets = 1;

                // While the next character (++ needed here) has not surpassed the end of the input string and there
                // are still unmatched brackets:
                while (++bracketIndex < inputString.length() && unmatchedBrackets > 0)
                {
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
                if (unmatchedBrackets == 0)
                {
                    try
                    {
                        // Append the loop string to the parser output.
                        outputStream.write(loopString.getBytes());
                    }
                    catch (IOException e)
                    {
                        throw new ParserException(PARSER_EXCEPTION_WRITE_SUBSTRING_MSG + loopString);
                    }

                    // Set the index to the end of the loop, which causes the parent parse to skip it. -- Here is needed
                    // as bracket index is one character after the loop string's last character (']').
                    i = --bracketIndex;
                }
                else
                {
                    // Throw an exception as there are more open bracket characters than closed bracket characters in
                    // the above loop.
                    throw new ParserException(PARSER_EXCEPTION_INVALID_OPEN_BRACKETS_MSG + loopString);
                }
            }
            else if (inputString.charAt(i) == BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.getCharValue())
            {
                // Above case means this should never be reached with valid brackets. Throw an exception as there
                // has been a closed bracket without a preceding open bracket.
                throw new ParserException(PARSER_EXCEPTION_INVALID_CLOSED_BRACKETS_MSG + i);
            }
            else
            {
                // Throw an exception as the character is unrecognized, and the Parser only accepts valid Brainfuck
                // characters.
                throw new ParserException(PARSER_EXCEPTION_INVALID_CHAR_MSG + inputString.charAt(i));
            }
        }

        return outputStream;
    }
}
