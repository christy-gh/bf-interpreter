package utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

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
}
