package utils;

import tokens.Token;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class HelperFunctions
{
    /**
     * Converts an output stream to a string to compare with the expected value.
     *
     * @param outputStream The output stream to be converted.
     * @return The string value of the output stream.
     */
    public static String convertOutputStreamToString(OutputStream outputStream)
    {
        return new String(((ByteArrayOutputStream)outputStream).toByteArray());
    }

    /**
     * Converts an input string to a list of tokens.
     *
     * @param input            The string to convert.
     * @param languageSetClass The language to use for recognition/conversion.
     * @return A list of tokens for valid characters from the input string.
     */
    public static <T extends Enum<T> & LanguageChar> List<Token> convertStringToTokens(String input,
                                                                                      Class<T> languageSetClass)
    {
        List<Token> tokens = new ArrayList<>();

        for (int i = 0; i < input.length() - 1; i++)
        {
            T languageChar = charToLanguageChar(input.charAt(0), languageSetClass);

            if (languageChar != null)
            {
                tokens.add(charToToken(languageChar));
            }
        }

        return tokens;
    }

    /**
     * Gets the name values from a language class.
     *
     * @param languageSetClass The language class.
     * @return A list of language character names.
     */
    public static <T extends Enum<T> & LanguageChar> List<String> enumValues(Class<T> languageSetClass)
    {
        List<String> enumValues = new ArrayList<>();

        for (T c : languageSetClass.getEnumConstants())
        {
            enumValues.add(c.name());
        }

        return enumValues;
    }

    /**
     * Converts a basic char to a char recognized by the language if it matches.
     *
     * @param inputChar            The input char to convert.
     * @param languageCharSetClass The language class to use for recognition/conversion.
     * @return The char in the form of one the language will recognize easily.
     */
    public static <T extends Enum<T> & LanguageChar>T charToLanguageChar(char inputChar, Class<T> languageCharSetClass)
    {
        for (T languageChar : languageCharSetClass.getEnumConstants())
        {
            if (languageChar.getCharValue() == (inputChar))
            {
                return languageChar;
            }
        }

        return null;
    }

    /**
     * Converts a valid language character to a token with it's name and char value.
     *
     * @param validChar The valid character.
     * @return A new token with the associated name of the char, and the char itself.
     */
    public static <T extends Enum<T> & LanguageChar>Token charToToken(T validChar)
    {
        return new Token(validChar.name(), validChar.getCharValue());
    }
}
