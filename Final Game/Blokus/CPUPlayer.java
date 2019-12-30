import java.awt.Color;

public class CPUPlayer extends Player{

	//this is the CPU version of the player class
	//the only real difference is that it is passed a difficulty in it's constructor
	
	
	int difficulty;
	
	CPUPlayer(int playerNumParam, Color colorParam, String charParam, int difficultyParam) {
		super(playerNumParam, colorParam, charParam);
		// TODO Auto-generated constructor stub
		difficulty = difficultyParam;
		setAI(new DifficultyAI(difficultyParam, true));
	}
	
	//getter and setter for the aforementioned difficulty
	public int GetDifficulty() 
	{
		return difficulty;
	}
	public void setDifficulty(int diff) 
	{
		difficulty = diff;
		this.ai.setDifficulty(diff);
	}
}
