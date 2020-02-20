package tokens;

public class Token
{
    private String name;
    private char character;

    public Token(String name, char character)
    {
        this.name = name;
        this.character = character;
    }

    public String getName()
    {
        return name;
    }

    public char getCharacter()
    {
        return character;
    }
}
