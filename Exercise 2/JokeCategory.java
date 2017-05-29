/**
 * Created by user on 29.05.2017.
 */
public enum JokeCategory
{
    NERDY("nerdy"),
    EXPLICIT("explicit");

    private String name;
    private int id; //I didn't get what the id should be used for, or what the id actually is...

    JokeCategory(String categoryName)
    {
        this.name = categoryName;
        this.id = (categoryName == "nerdy") ? 0 : 1; //again, I have no clue what the id should be
    }

    /**
     * Get's the actual name of the category
     * @return
     */
    public String getName()
    {
        return name;
    }
}
