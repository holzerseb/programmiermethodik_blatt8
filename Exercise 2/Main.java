import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by user on 29.05.2017.
 */
public class Main
{
    public static void main(String args[])
    {
        URLJoke urlJoke = new URLJoke();

        //test the readURL-method
        System.out.println("[INFO] Testing readUrl-Method");
        urlJoke.test_readUrl();
        System.out.println();

        //I realize that placing a try/catch-block around the whole main-method
        //is really not good coding, but for the sake of presentation, it looks much cleaner
        try
        {
            //for calling with names
            String firstName = "Queen";
            String lastName = "Elizabeth";

            //We print a random Joke:
            System.out.println("[INFO] Printing random joke without name");
            System.out.println("[JOKE] " + urlJoke.randomJoke());
            //System.out.println();
            System.out.println("[INFO] Printing random joke with name \"" + firstName + " " + lastName +"\"");
            System.out.println("[JOKE] " + urlJoke.randomJoke(firstName, lastName));
            System.out.println();

            //We print a Joke by its number:
            System.out.println("[INFO] Printing joke with number 219, without name");
            System.out.println("[JOKE] " + urlJoke.jokeWithNumber(219));
            //System.out.println();
            System.out.println("[INFO] Printing joke with number 209, with name \"" + firstName + " " + lastName +"\"");
            System.out.println("[JOKE] " + urlJoke.jokeWithNumber(209, firstName, lastName));
            System.out.println();

            //We print a random Joke:
            System.out.println("[INFO] Printing random joke by category \"nerdy\" without name");
            System.out.println("[JOKE] " + urlJoke.jokeWithCategory(JokeCategory.NERDY));
            //System.out.println();
            System.out.println("[INFO] Printing random joke by category \"nerdy\" with name \"" + firstName + " " + lastName +"\"");
            System.out.println("[JOKE] " + urlJoke.jokeWithCategory(JokeCategory.NERDY, firstName, lastName));
            System.out.println();
        }
        catch (NoJokeFoundException ex)
        {
            System.out.println("[ERROR] No Joke found: " + ex.getMessage());
            return;
        }
    }

}
