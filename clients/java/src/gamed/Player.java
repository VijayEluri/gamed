package gamed;

public class Player
{
	public String name;
	public int id;
	public String theme;
	private final String[] THEMES = new String[]
	{
		"starwars_empire", "portal", "chess", "starwars_rebel", "circuitboard", "duck", "mario", "trees", "gmaps", "zach", "radioactive", "biohazard", "batman", "despicable_me"
	};

	public Player(int id, String name)
	{
		this.name = name;
		this.id = id;
		this.theme = THEMES[id];
	}
}
