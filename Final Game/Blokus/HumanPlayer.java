import java.awt.Color;

public class HumanPlayer extends Player{
	
	//this is the human version of the player class
	//the only real difference is that it's not passed a "difficulty" in it's constructor
	
	
	HumanPlayer(int playerNumParam, Color colorParam, String charParam) {
		super(playerNumParam, colorParam, charParam);
		// TODO Auto-generated constructor stub
		setAI(new DifficultyAI(2, false));
	}
}
