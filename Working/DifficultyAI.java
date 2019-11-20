import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DifficultyAI {
	
	ArrayList<Integer> easy, medium, hard;
	
	public DifficultyAI(int difficulty)
	{
		if(difficulty == 1)
		{
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
							));
		}
		else if(difficulty == 2)
		{
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
						));
		}
		else if(difficulty == 3)
		{
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
		}
	}
	
	public int easyMove()
	{
		int piece;
		Random rand = new Random(System.currentTimeMillis());
		piece = easy.get(rand.nextInt(easy.size()));
		for(int i=0;i<easy.size();i++)
		{
			if(easy.get(i)==piece)
			{
				easy.remove(i);
			}
		}
		return piece;
	}
	
	public int mediumMove()
	{
		int piece;
		Random rand = new Random(System.currentTimeMillis());
		piece = medium.get(rand.nextInt(medium.size()));
		for(int i=0;i<medium.size();i++)
		{
			if(medium.get(i)==piece)
			{
				medium.remove(i);
			}
		}
		return piece;
	}
	
	public int hardMove()
	{
		int piece;
		Random rand = new Random(System.currentTimeMillis());
		piece = hard.get(rand.nextInt(hard.size()));
		for(int i=0;i<hard.size();i++)
		{
			if(hard.get(i)==piece)
			{
				hard.remove(i);
			}
		}
		return piece;
	}
}
