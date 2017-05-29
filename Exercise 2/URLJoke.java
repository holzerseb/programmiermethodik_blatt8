/**
 * Created by user on 29.05.2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLJoke
{
    /* MEMBERS */

    /**
     * Maximum Jokes-Counter
     */
    private int countMaxJokes = -1;


    /* CONSTRUCTOR */

    /**
     * Creates a new URLJoke
     */
    public URLJoke()
    {
        fetchMaxJokes();
    }


    /* PRIVATE METHODS */

    /**
     * Reads the contents from the given URL
     * @param urlString
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    private String readURL(String urlString) throws IOException
    {
        URL url = new URL(urlString); //maybe throws a malformedUrlException

        //openstream will return a java.io.inputstream object
        InputStreamReader streamReader = new InputStreamReader(url.openStream()); //reads bytes and decodes them into characters
        //to buffer the inputstreamreader, we use bufferedReader - since we don't know how costly the streamReader will actually be
        BufferedReader reader = new BufferedReader(streamReader); //maybe throws IOException

        //Next, we actually read the url "line by line"
        String line, page = "";
        while((line = reader.readLine()) != null)
            page += line;

        //clear up any mess
        reader.close();
        return page;
    }

    /**
     * This method loads a joke from the given url
     * @param urlString
     * @return
     * @throws NoJokeFoundException
     */
    private String loadJokeFromURL(String urlString) throws NoJokeFoundException
    {
        //create the pattern
        Pattern pattern = Pattern.compile("\"joke\": \"([^\"]*)\"");

        //make it matcher
        Matcher matcher;
        try
        {
            matcher = pattern.matcher(this.readURL(urlString));
        }
        catch (MalformedURLException ex)
        {
            throw new NoJokeFoundException("[ERROR] The given String is not a valid URL.");
        }
        catch (IOException ex)
        {
            throw new NoJokeFoundException("[ERROR] Error reading the url-stream.");
        }
        catch (Exception ex)
        {
            throw new NoJokeFoundException("[ERROR] Don't know, something bad happened.");
        }

        //and search for the joke
        if(!matcher.find())
            throw new NoJokeFoundException("[ERROR] There was no joke found at this url.");

        //return if there is one
        //since we placed the ' [^"]* ' regex-pattern inside a capturing group ' (*) '
        //we can simply access it by accessing it via the group-number (which is 1 in our case)
        return matcher.group(1);
    }

    /**
     * This will check how many jokes there are maximal
     * @return we usually want to find out, whether we succeeded here
     */
    private boolean fetchMaxJokes()
    {
        //create the pattern
        Pattern pattern = Pattern.compile("\"value\": \\d+");

        //make it matcher
        Matcher matcher;
        try
        {
            matcher = pattern.matcher(this.readURL("http://api.icndb.com/jokes/count"));
        }
        catch (MalformedURLException ex)
        {
            System.out.println("[WARNING] Could not fetch maximum joke counts, because URL seems malformed");
            return false;
        }
        catch (IOException ex)
        {
            System.out.println("[WARNING] Could not fetch maximum joke counts, because URL could not be read");
            return false;
        }
        catch (Exception ex)
        {
            System.out.println("[WARNING] Could not fetch maximum joke counts, something terrible happened");
            return false;
        }

        //check if there is a match
        if(!matcher.find())
        {
            System.out.println("[WARNING] Could not fetch maximum joke counts, something bad, but not terrible happened");
            return false;
        }

        //since the matched string is of form "value: x", we can just try to parse the substring
        String match = matcher.group();
        countMaxJokes = Integer.parseInt(match.substring(9));
        System.out.println("[INFO] Set Maximum Jokes Count to " + countMaxJokes);
        return true;
    }


    /* PUBLIC METHODS */

    /**
     * This returns, well, a random Joke :)
     * @return
     */
    public String randomJoke() throws NoJokeFoundException
    {
        return randomJoke(null, null);
    }

    /**
     * This returns, well, a random Joke :)
     * @param firstName
     * @param lastName
     * @return
     */
    public String randomJoke(String firstName, String lastName) throws NoJokeFoundException
    {
        String url = "http://api.icndb.com/jokes/random";

        //we concat the string with the possibility of a given firstName/lastName
        if(firstName != null && lastName != null)
            url += "?firstName=" + firstName + "&lastName=" + lastName;

        return this.loadJokeFromURL(url);
    }

    /**
     * Returns the joke with the given number
     * @param jokeNumber
     * @return
     */
    public String jokeWithNumber(int jokeNumber) throws NoJokeFoundException
    {
        return jokeWithNumber(jokeNumber, null, null);
    }

    /**
     * Returns the joke with the given number
     * @param jokeNumber
     * @param firstName
     * @param lastName
     * @return
     */
    public String jokeWithNumber(int jokeNumber, String firstName, String lastName) throws NoJokeFoundException
    {
        String url = "http://api.icndb.com/jokes/" + jokeNumber;

        //we concat the string with the possibility of a given firstName/lastName
        if(firstName != null && lastName != null)
            url += "?firstName=" + firstName + "&lastName=" + lastName;

        //if countMaxJokes is not set, we try to set it again - maybe we haven't had a connection before, but now
        if(countMaxJokes < 0)
            fetchMaxJokes();

        if(jokeNumber > countMaxJokes || jokeNumber < 0)
            throw new NoJokeFoundException("[ERROR] The given number is not within the valid range of [0, " + countMaxJokes +"]");

        return this.loadJokeFromURL(url);
    }

    /**
     * Returns a random joke from the given category
     * @param jokeCategory
     * @return
     */
    public String jokeWithCategory(JokeCategory jokeCategory) throws NoJokeFoundException
    {
        //because for easier code-maintaining
        return jokeWithCategory(jokeCategory, null, null);
    }

    /**
     * Returns a random joke from the given category
     * @param jokeCategory
     * @param firstName
     * @param lastName
     * @return
     */
    public String jokeWithCategory(JokeCategory jokeCategory, String firstName, String lastName) throws NoJokeFoundException
    {
        String url = "http://api.icndb.com/jokes/random?limitTo=[" + jokeCategory.getName() + "]";

        //we concat the string with the possibility of a given firstName/lastName
        if(firstName != null && lastName != null)
            url += "&firstName=" + firstName + "&lastName=" + lastName;

        return this.loadJokeFromURL(url);
    }


    /* TESTING METHODS */

    /**
     * This method tests the URLJoke.readURL(String) Method
     * required by Task b)
     * @return
     */
    public boolean test_readUrl()
    {
        String url = "http://api.icndb.com/jokes/random";
        boolean success = true;

        try
        {
            System.out.println(this.readURL(url));
        }
        catch (MalformedURLException ex)
        {
            System.out.println("[ERROR] The given String is not a valid URL");
            success = false;
        }
        catch (IOException ex)
        {
            System.out.println("[ERROR] Error reading the url-stream");
            success = false;
        }
        catch (Exception ex)
        {
            System.out.println("[ERROR] Don't know, something bad happened");
            success = false;
        }

        return success;
    }
}
