import java.awt.Color;

public class Player 
{
	//This is the main player class
	//It contains all of the variables and methods associates with a player
	
	int playerNumber;
	Color blockColor;
	String blockChar;
	boolean blocksAvailable[];
	final boolean allBlocksAvailable[] = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
	int Score = 0;
	DifficultyAI ai;
	boolean firstTryFlag = false;
	
	Player(int playerNumParam, Color colorParam, String charParam)
	{
		blockColor = colorParam;
		blockChar = charParam;
		playerNumber = playerNumParam;
		blocksAvailable = allBlocksAvailable;
	}
	
	public void setAI(DifficultyAI aiParam) 
	{
		ai = aiParam;
	}
	
	public void pieceUsed(int index) 
	{
		blocksAvailable[index] = false;
	}
	
	public boolean noBlocksRemaining() 
	{
		boolean hasPiecesFlag = false;
		for (int i = 0; i < blocksAvailable.length; i++) {
			if (blocksAvailable[i]) 
			{
				hasPiecesFlag = true;
			}
		}
		return !hasPiecesFlag;
	}
	
	public Color getColor() 
	{
		return blockColor;
	}
	
	public String getChar() 
	{
		return blockChar;
	}
	
	public String getAvailablePiecesString() 
	{
		String tempString = "";
		for (int i = 0; i < 21; i++) 
		{
			if (blocksAvailable[i]) 
			{
				tempString = tempString + "1";
			}
			else 
			{
				tempString = tempString + "0";
			}
		}
		return tempString;
	}
	
	public boolean[] getAvailablePieces() 
	{
		return blocksAvailable;
	}
	public String getAvailablePiecesAsString() 
	{
		String tempOutputString = "";
		for (int i = 0; i < blocksAvailable.length; i++) {
			if (blocksAvailable[i]) 
			{
				tempOutputString = tempOutputString + "1";
			}
			else 
			{
				tempOutputString = tempOutputString + "0";
			}
		}
		return tempOutputString;
	}
	
	public void setAvailablePiecesFromString(String availablePiecesString) 
	{
		for (int i = 0; i < blocksAvailable.length; i++) 
		{
			if (availablePiecesString.substring(i, i+1).equals("1")) 
			{
				blocksAvailable[i] = true;
			}
			else 
			{
				blocksAvailable[i] = false;
			}
		}
	}
	
	public int getPlayerNumber() 
	{
		return playerNumber;
	}
	public void setColor(Color colorParam) 
	{
		blockColor = colorParam;
	}
	public void setColor(String charParam) 
	{
		blockChar = charParam;
	}
	public int getScore() 
	{
		return Score;
	}
	public void addScore(int scoreToBeAdded) 
	{
		Score = Score + scoreToBeAdded;
	}
	
	//This is the logic to properly get the piece number generations from the AI
	public int decideOnPiece() 
	{
		if (!firstTryFlag) 
		{
			return ai.firstMove((this.getAvailablePieces()));
		}
		else 
		{
			return ai.subsequentMove();
		}
		
	}
	public void placed() 
	{
		firstTryFlag = false;
	}
}
