package lexer;

import org.junit.Before;
import org.junit.Test;
import tokens.Token;
import utils.BrainfuckChar;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.Assert.*;

public class LexerTest
{
    private Lexer<BrainfuckChar> lexer;

    // TODO: Change chars to actual enum values.
    private final String VALID_STRIPPED_BRAINFUCK_INPUT = "><+-.,[]";
    private final String VALID_INPUT_WITH_WHITESPACE = "\t\n    \t >\n<  +  - .    \n   , [   ]     \t\n";
    private final String VALID_INPUT_WITH_OTHER_CHARS = "!1q>@2w<#3e+$4r-%5t.^6y,&7u[*8i](9o";
    private final String VALID_INPUT_WITH_WHITESPACE_AND_OTHER_CHARS =
            " \t\n  ! 1   q>  @2   w<#3e  \n+$4r-%5t.^6y ,&7u[*8i]  (9o     \t";

    @Before
    public void setup()
    {
        lexer = new Lexer<>(BrainfuckChar.class);
    }
    @Test
    public void testLexerValidStrippedInput() throws LexerException
    {
        List<Token> tokens = lexer.stripIllegalChars(new ByteArrayInputStream(VALID_STRIPPED_BRAINFUCK_INPUT.getBytes()));
        validateTokens(tokens);
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT.length(), tokens.size());
    }

    @Test
    public void testLexerValidInputStringWithSpaces() throws LexerException
    {
        List<Token> tokens = lexer.stripIllegalChars(new ByteArrayInputStream(VALID_INPUT_WITH_WHITESPACE.getBytes()));
        validateTokens(tokens);
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT.length(), tokens.size());
    }

    @Test
    public void testLexerValidInputStringWithOtherChars() throws LexerException
    {
        List<Token> tokens = lexer.stripIllegalChars(new ByteArrayInputStream(VALID_INPUT_WITH_OTHER_CHARS.getBytes()));
        validateTokens(tokens);
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT.length(), tokens.size());
    }

    @Test
    public void testLexerValidInputStringWithWhiteSpaceAndOtherChars() throws LexerException
    {
        List<Token> tokens = lexer.stripIllegalChars(new ByteArrayInputStream(VALID_INPUT_WITH_WHITESPACE_AND_OTHER_CHARS.getBytes()));
        validateTokens(tokens);
        assertEquals(VALID_STRIPPED_BRAINFUCK_INPUT.length(), tokens.size());
    }

    @Test
    public void testLexerValidChars()
    {
        assertEquals(BrainfuckChar.INC_POINTER, lexer.getLanguageChar(BrainfuckChar.INC_POINTER.getCharValue(),
                BrainfuckChar.class));
        assertEquals(BrainfuckChar.DEC_POINTER, lexer.getLanguageChar(BrainfuckChar.DEC_POINTER.getCharValue(),
                BrainfuckChar.class));
        assertEquals(BrainfuckChar.INC_BYTE_AT_POINTER,
                lexer.getLanguageChar(BrainfuckChar.INC_BYTE_AT_POINTER.getCharValue(), BrainfuckChar.class));
        assertEquals(BrainfuckChar.DEC_BYTE_AT_POINTER,
                lexer.getLanguageChar(BrainfuckChar.DEC_BYTE_AT_POINTER.getCharValue(), BrainfuckChar.class));
        assertEquals(BrainfuckChar.OUTPUT_BYTE_AT_POINTER,
                lexer.getLanguageChar(BrainfuckChar.OUTPUT_BYTE_AT_POINTER.getCharValue(), BrainfuckChar.class));
        assertEquals(BrainfuckChar.INPUT_BYTE_TO_POINTER,
                lexer.getLanguageChar(BrainfuckChar.INPUT_BYTE_TO_POINTER.getCharValue(), BrainfuckChar.class));
        assertEquals(BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO,
                lexer.getLanguageChar(BrainfuckChar.JUMP_FORWARD_IF_BYTE_IS_ZERO.getCharValue(), BrainfuckChar.class));
        assertEquals(BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO,
                lexer.getLanguageChar(BrainfuckChar.JUMP_BACKWARD_IF_BYTE_IS_NOT_ZERO.getCharValue(),
                        BrainfuckChar.class));
    }

    @Test
    public void testLexerAllBytes()
    {
        // Iterate through every possible byte value.
        for (int i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++)
        {
            byte b = (byte)i;
            System.out.println("byte = " + (int)b);

            boolean isLegal = false;

            for (BrainfuckChar brainfuckChar : BrainfuckChar.values())
            {
                System.out.println("brainfuckChar byte value: " + brainfuckChar.getByteValue());
                if (b == brainfuckChar.getByteValue())
                {
                    isLegal = true;
                }
            }

            if (isLegal)
            {
                assertNotNull(lexer.getLanguageChar((char)b, BrainfuckChar.class));
            }
            else
            {
                assertNull(lexer.getLanguageChar((char)b, BrainfuckChar.class));
            }
        }
    }

    private void validateTokens(List<Token> tokens)
    {
        assertNotNull(tokens);
        assertTrue(tokens.size() > 0);

        for (Token token : tokens)
        {
            BrainfuckChar brainfuckChar = BrainfuckChar.valueOf(token.getName());
            assertEquals(brainfuckChar.getCharValue(), token.getCharacter());
        }
    }
}
