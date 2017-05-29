/**
 * Created by user on 29.05.2017.
 */
public class NoJokeFoundException extends Exception
{
    /**
     * I really hate Java for having to create a whole file just for this
     * @param message
     */
    public NoJokeFoundException(String message)
    {
        super(message);
    }
}