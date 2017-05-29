import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by user on 29.05.2017.
 */
public class PlayerManager
{
    /* MEMBERS */

    List<Player> playerList;


    /* GETTER'n'Setter */

    public List<Player> getPlayerList()
    {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList)
    {
        this.playerList = playerList;
    }

    /* CONSTRUCTOR AND STATIC STUFF */

    /**
     * Constructor
     * @param filePath path to the player-list-file
     */
    public PlayerManager(String filePath)
    {
        this.playerList = readPlayers(filePath);
    }

    /**
     * Reads the players from the given File
     * @param filePath
     * @return
     */
    private List<Player> readPlayers(String filePath)
    {
        //I create a file-object, because we might would want to perform some checks on the file first
        File playersFile = new File(filePath);

        //Since we know that the file is a stream of characters, we can use FileReader,
        //a convenience class of InputStreamReader
        FileReader fileStream;

        try
        {
            fileStream = new FileReader(playersFile);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("[ERROR] Couldn't find file located at \"" + playersFile.getAbsolutePath() + "\"");
            return null;
        }
        catch (Exception ex)
        {
            System.out.println("[ERROR] " + ex.getMessage());
            return null;
        }

        //Because reading from that file might be incredibly costly, we wrap the FileReader into a BufferedReader
        BufferedReader reader = new BufferedReader(fileStream);

        //Since we have to use a scanner, here we go
        Scanner scanner = new Scanner(reader);

        //read each player
        List<Player> playerList = new ArrayList<Player>(); //I use arraylist, because it is nowhere stated, they have to be sorted
        while(scanner.hasNextLine())
        {
            Player player = new Player();
            try
            {
                player.id = Integer.parseInt(scanner.next());
                player.firstName = scanner.next();
                player.lastName = scanner.next();
                player.strength = Integer.parseInt(scanner.next());
            }
            catch (Exception ex)
            {
                //We presume, when an error occurs, we can't handle the player
                scanner.nextLine(); //this will read the rest of the line
                continue;
            }

            playerList.add(player);
        }

        return playerList;
    }

    /**
     * Increases strength without Java 8
     */
    public void increaseStrength7()
    {
        if(getPlayerList() == null || getPlayerList().size() <= 0)
            return;

        for(Player player : getPlayerList()) //this is a Java5 for-loop
            player.strength++;

        /* The disadvantage is, that elements are processed sequentiell */
    }

    /**
     * Increases strength with Java 8
     */
    public void increaseStrength8()
    {
        if(getPlayerList() == null || getPlayerList().size() <= 0)
            return;

        getPlayerList().stream().forEach((player) ->
        {
            player.strength++;
        });

        /* Here we have a "functional looping", so we can do a whole lot more - even parallelization ("parallelStream()") */
    }

    /**
     * Groups without Java 8
     */
    public Map<Integer, Long> groupByStrength7()
    {
        if(getPlayerList() == null || getPlayerList().size() <= 0)
            return null;

        Map<Integer, Long> playerGroup = new HashMap<>();

        for(Player player : getPlayerList()) //this is a Java5 for-loop
        {
            if(playerGroup.containsKey(player.strength))
                playerGroup.put(player.strength, playerGroup.get(player.strength) + 1);
            else
                playerGroup.put(player.strength, 1l);
        }

        return playerGroup;
    }

    /**
     * Groups with Java 8
     */
    public Map<Integer, Long> groupByStrength8()
    {
        //NOTE: THIS DOES NOT FULLY FULFILL THE REQUIREMENTS OF THE TASK
        //That's why I didn't check the 1c :)

        Map<Integer, Long> playerGroup = new HashMap<>();

        getPlayerList().stream().forEach((player) ->
        {
            if(playerGroup.containsKey(player.strength))
                playerGroup.put(player.strength, playerGroup.get(player.strength) + 1);
            else
                playerGroup.put(player.strength, 1l);
        });

        return playerGroup;
    }
}
