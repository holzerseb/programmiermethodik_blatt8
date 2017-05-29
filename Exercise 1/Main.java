import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 29.05.2017.
 */
public class Main
{
    public static void main(String args[])
    {
        PlayerManager playerManager = new PlayerManager("players.txt");
        Iterator<Player> iterator;
        boolean success;
        Player current;


        /* TESTING INCREASING */

        //Check for Pre-Java8-ForEach
        System.out.println("[TEST] Check if increaseStrength7 works");
        playerManager.increaseStrength7();
        iterator = playerManager.getPlayerList().listIterator();
        success = true;
        while(iterator.hasNext())
        {
            current = iterator.next();
            if(current.strength < 2 || current.strength > 4)
            {
                success = false;
                break;
            }
        }
        if(success)
            System.out.println("[RESULT] Everything worked :)");
        else
            System.out.println("[RESULT] NOOOOO, IncreaseStrength7 didn't work");

        //Check for Java8-ForEach
        System.out.println("[TEST] Check if increaseStrength8 works");
        playerManager.increaseStrength7();
        iterator = playerManager.getPlayerList().listIterator();
        success = true;
        while(iterator.hasNext())
        {
            current = iterator.next();
            if(current.strength < 3 || current.strength > 5)
            {
                success = false;
                break;
            }
        }

        if(success)
            System.out.println("[RESULT] Everything worked :)");
        else
            System.out.println("[RESULT] NOOOOO, IncreaseStrength8 didn't work");


        /* TESTING GROUPS */

        System.out.println("[TEST] Check if Grouping works identical");
        success = true;
        System.out.println("[CHECK] Number of Players in Pre-Java8-Group: " +
                playerManager.groupByStrength7().get(3) + " vs " +
                playerManager.groupByStrength8().get(3) + " in Java8-Group with strength 3.");
        if(playerManager.groupByStrength7().get(3) != playerManager.groupByStrength8().get(3))
            success = false;
        System.out.println("[CHECK] Number of Players in Pre-Java8-Group: " +
                playerManager.groupByStrength7().get(4) + " vs " +
                playerManager.groupByStrength8().get(4) + " in Java8-Group with strength 4.");
        if(playerManager.groupByStrength7().get(4) != playerManager.groupByStrength8().get(4))
            success = false;
        System.out.println("[CHECK] Number of Players in Pre-Java8-Group: " +
                playerManager.groupByStrength7().get(5) + " vs " +
                playerManager.groupByStrength8().get(5) + " in Java8-Group with strength 5.");
        if(playerManager.groupByStrength7().get(5) != playerManager.groupByStrength8().get(5))
            success = false;

        if(success)
            System.out.println("[RESULT] They are identical :)");
        else
            System.out.println("[RESULT] NOOOOO, they don't match :(");
    }
}
