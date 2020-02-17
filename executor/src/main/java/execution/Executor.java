package execution;

import utils.BrainfuckChar;

import java.io.*;
import java.util.Scanner;

/**
 * Executor class used to take output from the Parser and execute the program.
 */
public class Executor
{
    // Max amount of bytes available (largest 16-bit integer value).
    private static final int MAX_BYTES = 65535;

    private Scanner scanner = new Scanner(System.in);
    private byte[] memory = new byte[MAX_BYTES];
    private int pointer;

    /**
     * Reads an input stream into a string to pass to the interpreter.
     *
     * @param inputStream  The input stream containing the input to interpret.
     * @param outputStream The output stream for the interpreter to write to.
     * @return The output stream with interpreter output.
     * @throws IOException Thrown if there is an issue writing to the output stream.
     */
    OutputStream execute(InputStream inputStream, OutputStream outputStream) throws IOException
    {
        String inputString = readInputStreamToString(inputStream);

        // Interpret the input string.
        return interpret(inputString, outputStream);
    }

    /**
     * Reads an input stream into a string.
     *
     * @param inputStream The input stream to read from.
     * @return A string containing the input stream's contents.
     * @throws IOException Thrown if there is an issue reading from the input stream.
     */
    String readInputStreamToString(InputStream inputStream) throws IOException
    {
        // Read in inputStream to a string.
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();

        String read;
        while((read = bufferedReader.readLine()) != null)
        {
            stringBuilder.append(read);
        }

        return stringBuilder.toString();
    }

    /**
     * Interprets a Brainfuck input by simulating Brainfuck behaviour.
     *
     * @param input        The input. It is assumed that all characters are Brainfuck legal (as dictated by
     *                     BranfuckChar), and parsed for any memory or logic errors.
     * @param outputStream The output stream to write to.
     * @return The output stream with the input.
     * @throws IOException Thrown if there is an issue writing to the output stream.
     */
    OutputStream interpret(String input, OutputStream outputStream) throws IOException
    {
        int loopCounter = 0;

        // Iterate through each character of the input.
        for (int i = 0; i < input.length(); i++)
        {
            // If the current character is '>':
            if (input.charAt(i) == BrainfuckChar.INC_POINTER.getCharValue())
            {
                // If the pointer is at the maximum position, set the pointer to position 0. Otherwise increment it by
                // 1.
                pointer = (pointer == MAX_BYTES - 1) ? 0 : pointer + 1;
            }
            // Otherwise if the current character is '<':88
            else if (input.charAt(i) == BrainfuckChar.DEC_POINTER.getCharValue())
            {
                // If the pointer is at position 0, set it to the maximum position. Otherwise decrement it by 1.
                pointer = (pointer == 0) ? MAX_BYTES - 1 : pointer - 1;
            }
            // Otherwise if the current character is '+':
            else if (input.charAt(i) == BrainfuckChar.INC_BYTE_AT_POINTER.getCharValue())
            {
                // Increment the byte at the pointer by 1.
                memory[pointer]++;
            }
            // Otherwise if the current character is '-':
            else if (input.charAt(i) == BrainfuckChar.DEC_BYTE_AT_POINTER.getCharValue())
            {
                // Decrement the byte at the pointer by 1.
                memory[pointer]--;
            }
            // Otherwise if the current character is '.':
            else if (input.charAt(i) == BrainfuckChar.OUTPUT_BYTE_AT_POINTER.getCharValue())
            {
                // Write the current byte as a char to the output stream.
                char outputChar = (char)memory[pointer];
                outputStream.write(outputChar);
            }
            // Otherwise if the current character is ',':
            else if (input.charAt(i) == BrainfuckChar.INPUT_BYTE_TO_POINTER.getCharValue())
            {
                // Set the value of the current byte in memory to the next user input byte.
                memory[pointer] = (byte)scanner.next().charAt(0);
            }
            // Otherwise if the current character is '[':
            else if (input.charAt(i) == BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.getCharValue())
            {
                // If the current byte equals 0:
                if (memory[pointer] == 0)
                {
                    // Increment the current character in the input by 1.
                    i++;

                    // While the number of loops is greater than 0 or the current character is not ']':
                    while (loopCounter > 0 || input.charAt(i) !=
                            BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.getCharValue())
                    {
                        // If the current character is '[':
                        if (input.charAt(i) == BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.getCharValue())
                        {
                            // Increment the number of '[' characters to find and match.
                            loopCounter++;
                        }
                        // Otherwise if the current character is ']':
                        else if (input.charAt(i) == BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.getCharValue())
                        {
                            // Decrement the number of '[' characters to find and match.
                            loopCounter--;
                        }

                        // Increment the current character to the next character.
                        i++;
                    }
                }
            }
            // Otherwise if the current character is ']':
            else if (input.charAt(i) == BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.getCharValue())
            {
                // If the current byte doesn't equal 0:
                if (memory[pointer] != 0)
                {
                    // Decrement the current character to the previous character.
                    i--;

                    // While the number of loops is greater than 0 or the current character is not '[':
                    while (loopCounter > 0 || input.charAt(i) !=
                            BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.getCharValue())
                    {
                        // If the current character is ']':
                        if (input.charAt(i) == BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.getCharValue())
                        {
                            // Increment the number of loops.
                            loopCounter++;
                        }
                        // Otherwise if the current character is '[':
                        else if (input.charAt(i) == BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.getCharValue())
                        {
                            // Decrement the number of loops.
                            loopCounter--;
                        }

                        // Decrement the current character to the previous character.
                        i--;
                    }

                    // Decrement the current character to the previous character.
                    i--;
                }
            }
        }

        return outputStream;
    }
}
