import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DifficultyAI {
	
	//this class is all logic
	
	final ArrayList<Integer> 
	easy = new ArrayList<Integer>(
			Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,
					1,1,1,1,1,1,1,1,1,1,1,1,1,
					2,2,2,2,2,2,
					3,3,3,3,3,3,
					4,
					5,
					6,
					7,
					8,
					9,
					10,
					11,
					12,
					13,
					14,
					15,
					16,
					17,
					18,
					19,
					20
					)), 
	medium = new ArrayList<Integer>(
			Arrays.asList(0,
					1,
					2,
					3,
					4,
					5,
					6,
					7,
					8,
					9,
					10,
					11,
					12,
					13,
					14,
					15,
					16,
					17,
					18,
					19,
					20
				)), 
	hard = new ArrayList<Integer>(
			Arrays.asList(
					0,
					1,
					2,
					3,
					4,4,
					5,5,
					6,6,
					7,7,
					8,8,
					9,9,9,
					10,10,10,
					11,11,11,
					12,12,12,
					13,13,13,
					14,14,14,
					15,15,15,
					16,16,16,
					17,17,17,
					18,18,18,
					19,19,19,
					20,20,20
					));
	ArrayList<Boolean> tempAvailablePieces;
	int difficultyInt;
	ArrayList<Integer> choices = null;
	boolean isCPU;
	
	public DifficultyAI(int difficulty, boolean isCPUParam)
	{
		isCPU = isCPUParam;
		Boolean availableOrientationsArray[] = {
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				true
		};
		
		
		tempAvailablePieces = new ArrayList<Boolean>(Arrays.asList(availableOrientationsArray));
		difficultyInt = difficulty;
	}
	
	public void setDifficulty(int diff) {
		difficultyInt = diff;
	}
	
	public int firstMove(boolean availablePieces[])
	{
		//it uses weighted randomness from the selected arraylist to generate pieces in better orders 
		//at higher difficulties
		
		if (difficultyInt == 1) 
		{
			choices = easy;
		}
		else if (difficultyInt == 2) 
		{
			choices = medium;
		}
		else if(difficultyInt == 3)
		{
			choices = hard;
		}
		
		
		for (int i = 0; i < availablePieces.length; i++) 
		{
			if (!availablePieces[i]) 
			{
				for (int j = 0; j < choices.size(); j++) 
				{
					if (choices.get(j) == i) 
					{
						choices.remove(j);
					}
				}
			}	
		}
		
		return subsequentMove();
	}
	
	public int subsequentMove() 
	{
		if (choices.size() == 0) 
		{
			return -1;
		}
		
		int pieceInfo;
		Random rand = new Random(System.currentTimeMillis());
		pieceInfo = choices.get(rand.nextInt(choices.size()));
		if (isCPU) 
		{
			for (int j = 0; j < choices.size(); j++) 
			{
				if (choices.get(j) == pieceInfo) 
				{
					choices.remove(j);
				}
			}
		}
		
		return pieceInfo;
	}
}