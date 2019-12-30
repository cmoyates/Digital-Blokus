import java.awt.Color;

//This is the Game class, it handles most of the things that need to be accessed by multiple other classes
public class Game
{
	//These lines declare all of the necessary variables
	private int numPlayers;
	private int difficulty;
	private boolean colorDef;
	private boolean hints;
	private MenuUI menuUI;
	private BoardUI boardUI;
	private Player players[];
	private Color playerColors[];
	private String playerChars[];
	Color[] availableColors = {Color.black,Color.blue,Color.cyan,Color.green,Color.magenta,Color.orange,Color.pink,Color.red,Color.white,Color.yellow};

	//This is the constructor for this class
	public Game()
	{
		//These line initialize a few of the variables
		menuUI = new MenuUI(this);
		hints = true;
		boardUI = new BoardUI(this);
		players = new Player[4];
		Color tempColors[] = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
		playerColors = tempColors;
		String tempChars[] = {" ", " ", " ", " "};
		playerChars = tempChars;
	}

	//These are all of the setters for the variables that need them
	public void setNumPlayers(int num)
	{
		numPlayers = num;
	}
	public void setDifficulty(int diff)
	{
		difficulty = diff;
		for (int i = 0; i < 4; i++) {
			if (getPlayer(i) instanceof CPUPlayer) 
			{
				((CPUPlayer)getPlayer(i)).setDifficulty(diff);
			}
		}
	}
	public void setHints(boolean hint)
	{
		hints = hint;
	}

	//This is a special setter that takes a single string and sets several other variables accordingly
	public void setSettings(String set) 
	{
		setNumPlayers(Integer.valueOf((set.substring(0,1))));
		setDifficulty(Integer.valueOf((set.substring(1,2))));
		setHints(Integer.valueOf((set.substring(2,3))) == 1);
	}

	//These are all of the getters for the variables that need them
	public int getNumPlayers()
	{
		return this.numPlayers;
	}
	public int getDifficulty()
	{
		return this.difficulty;
	}
	public boolean getColour()
	{
		return this.colorDef;
	}
	public boolean getHints()
	{
		return this.hints;
	}

	//This is a special getter that returns the value of the difficulty variable as a string that states what the int corresponds to
	public String getDifficultyString() 
	{
		if (difficulty == 1) 
		{
			return "Easy";
		}
		else if (difficulty == 2)
		{
			return "Medium";
		}
		else 
		{
			return "Hard";
		}
	}

	//This is a special getter that gets a string that represents the current values of multiple variables
	public String getSettings()
	{
		String temp = String.valueOf(numPlayers) + String.valueOf(difficulty);
		if(getHints()) 
		{
			temp = temp + "1";
		}
		else 
		{
			temp = temp + "0";
		}
		return temp;
	}

	public void setScores(String[] scoreStrs) 
	{
		for (int i = 0; i < 4; i++) 
		{
			getPlayer(i).addScore(Integer.valueOf(scoreStrs[i]));
			//System.out.println(getPlayer(i).getScore());
		}
	}

	public String[] getAllPlayersAvailablePieces()
	{
		String tempOutputStrings[] = new String[4];
		for (int i = 0; i < 4; i++) 
		{
			tempOutputStrings[i] = getPlayer(i).getAvailablePiecesAsString();
		}
		return tempOutputStrings;
	}

	public Player getPlayer(int index) 
	{
		return players[index];
	}
	public Player[] getPlayer() 
	{
		return players;
	}

	public Color[] getPlayerColors() 
	{
		return playerColors;
	}
	public String[] getPlayerChars() 
	{
		return playerChars;
	}

	//This is a method that opens the UI for the main menu
	public void startGame()
	{
		menuUI.setupGUI();
	}

	//This is a method that opens the UI for the actual game board
	public void DisplayBoard(boolean resuming) 
	{
		for (int i = 0; i < numPlayers; i++) 
		{
			players[i] = new HumanPlayer(i, playerColors[i], playerChars[i]);
		}
		for (int i = numPlayers; i < 4; i++) 
		{
			players[i] = new CPUPlayer(i, playerColors[i], playerChars[i], difficulty);
		}
		boardUI.SetUp(resuming);
	}

	public void setPlayerColors(Color paramColors[]) 
	{
		for (int i = 0; i < 4; i++) 
		{
			if (paramColors[i] != null) 
			{
				playerColors[i] = paramColors[i];
				//getPlayer(i).setColor(paramColors[i]);
				
			}
		}
	}
	public void setPlayerChars(String paramChars[]) 
	{
		playerChars = paramChars;
		for (int i = 0; i < 4; i++) 
		{
			playerChars[i] = paramChars[i];
			//getPlayer(i).setColor(paramChars[i]);
		}
	}

	public void setAllPlayersAvailablePieces(String[] strs) 
	{
		for (int i = 0; i < strs.length; i++) {
			getPlayer(i).setAvailablePiecesFromString(strs[i]);
		}
	}

	public String[] getStylesStrings()
	{
		String tempReturnStrings[] = new String[2];
		tempReturnStrings[0] = "";

		for (int i = 0; i < 4; i++) 
		{
			for (int j = 0; j < availableColors.length; j++) 
			{
				if (playerColors[i].equals(availableColors[j])) 
				{
					tempReturnStrings[0] = tempReturnStrings[0] + String.valueOf(j) + " ";
				}
			}
		}
		tempReturnStrings[1] = "" + playerChars[0] + playerChars[1] + playerChars[2] + playerChars[3];
		return tempReturnStrings;
	}
	public void setStylesStrings(String colorStrings[], String chars) 
	{
		Color tempPlayerColors[] = new Color[4];
		String tempPlayerChars[] = new String[4];
		for (int i = 0; i < 4; i++) 
		{
			tempPlayerColors[i] = availableColors[Integer.valueOf(colorStrings[i])];
			System.out.println(Integer.valueOf(colorStrings[i]));
			tempPlayerChars[i] = chars.substring(i, i+1);
		}
		setPlayerColors(tempPlayerColors);
		setPlayerChars(tempPlayerChars);
	}
}