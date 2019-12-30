import java.awt.*;
import javax.swing.*;

//This is the class for the squares that appear on the board
public class BlockSquare extends JButton {
	
	//These lines of code initialize and declare a couple of variables
	public int playerUsing = 0;
	public Color playerColors[] = new Color[5];
	public String playerChars[] = new String[5];
	public int coords[];
	public BlockSquare fiveSurround[][] = null;
	boolean showingHint = false;
	
	//This is a method that is used to change the player that has placed a piece on this particular square
	public void changePlayerUsing(int playerNum) 
	{
		playerUsing = playerNum;
		this.setBackground(playerColors[playerNum]);
		this.setText(playerChars[playerNum]);
	}
	
	//these are overloaded constructors for this class
	public BlockSquare(int xCoord, int yCoord) 
	{
		coords = new int[] {xCoord, yCoord};
	}
	public BlockSquare(int xCoord, int yCoord, Color[] playerColorsParam, String[] playerCharsParam) 
	{
		coords = new int[] {xCoord, yCoord};
		this.setFont(new Font("Dialog", Font.BOLD, 20));
		playerColors[0] = Color.WHITE;
		playerChars[0] = "";
		for (int i = 1; i < 5; i++) 
		{
			playerColors[i] = playerColorsParam[i-1];
			playerChars[i] = playerCharsParam[i-1];
		}
		this.setBackground(Color.WHITE);
	}
	
	
	//These are a lot of getters and setters 
	public int[] getCoords() 
	{
		return coords;
	}
	
	public BlockSquare[][] getFiveSurround() 
	{
		return fiveSurround;
	}
	public BlockSquare getFiveSurroundIndex(int xCoord, int yCoord) 
	{
		return fiveSurround[xCoord][yCoord];
	}
	
	public void setStyles(Color[] colorArray, String[] stringArray) 
	{
		playerColors[0] = Color.WHITE;
		playerChars[0] = "";
		for (int i = 1; i < 5; i++) 
		{
			playerColors[i] = colorArray[i-1];
			playerChars[i] = stringArray[i-1];
		}
		this.setBackground(playerColors[playerUsing]);
		this.setText(playerChars[playerUsing]);
	}
	
	public void setFiveSurround(BlockSquare[][] squares) 
	{
		fiveSurround = squares;
	}
	public void setFiveSurround(BlockSquare square, int xCoord, int yCoord) 
	{
		fiveSurround[xCoord][yCoord] = square;
	}
	
	public Color getPlayerColor() 
	{
		return playerColors[playerUsing];
	}
	
	public int getPlayerUsing() 
	{
		return playerUsing;
	}
	
	public boolean getShowingHint() 
	{
		return showingHint;
	}
	
	public void setShowingHint(boolean isShowing) 
	{
		showingHint = isShowing;
	}
}
