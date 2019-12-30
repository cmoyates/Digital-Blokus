import java.awt.*;
import java.util.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

//This is the class for the UI of the game board
public class BoardUI 
{
	static final int[][] emptyPiece = {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
	static final int[][] emptyCorners = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
	
	//These lines of code initialize all of the needed variables
	JFrame BoardWindow;
	JPanel BoardPanel;
	JPanel PiecesPanel;
	JPanel OptionsPanel;
	JButton CWRotate;
	JButton CCWRotate;
	JButton Flip;
	JToggleButton HintButton;
	JLabel activePlayerLabel;
	BlockSquare tiles[][];
	Game game;
	Pieces pieces;
	int ActivePiece[][];
	int ActivePieceCorners[][];
	Player activePlayer;
	JButton pieceButtons[];
	JButton activePieceButton;
	BlockSquare hintBlockSquare;
	boolean startHintFlag = true;
	boolean[] skipPlayers = {false,false,false,false};
	boolean isResumed = false;

	//This function is the constructor for this class
	public BoardUI(Game gameObject) 
	{
		//This line initializes one of the variables to the parameter that was passed to the constructor
		game = gameObject;
		pieces = new Pieces();
		//This line initializes the BoardWindow variable to a new JFrame 
		BoardWindow = new JFrame("The Game");

		//These line of code make it so that when the user attempts to close the game, they are presented with an option to save
		BoardWindow.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(BoardWindow, 
						"Would you like to save the game?", "Save?", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					RecordBoard();
				}
				System.exit(0);
			}
		});

		//These lines initialize several variables
		BoardPanel = new JPanel();
		tiles = new BlockSquare[20][20];
		PiecesPanel = new JPanel();
		OptionsPanel = new JPanel();
		HintButton = new JToggleButton("");
		CWRotate = new JButton("⤾");
		CCWRotate = new JButton("⤿");
		Flip = new JButton("↔");
		ActivePiece = emptyPiece;
		ActivePieceCorners = emptyCorners;
		pieceButtons = new JButton[21];
		activePlayerLabel = new JLabel();
	}

	//This function is the one that sets up the window, and gets it ready for play
	public void SetUp(boolean resuming) 
	{
		activePlayer = game.getPlayer(0);
		isResumed = resuming;
		//These lines set the layout and size of the window
		BoardWindow.setLayout(new BorderLayout());
		BoardWindow.setSize(1600, 900);

		//These lines set the layout and size of the panel that will contain the actual board
		BoardPanel.setSize(900,900);
		BoardPanel.setLayout(new GridLayout(20,20));

		//This loop creates a 2D matrix of BlockSquare objects
		for	(int i = 0; i < 400; i++) 
		{

			int index1 = ((i)/20);
			int index2 = ((i)%20);
			BlockSquare temp = new BlockSquare(index1, index2, game.getPlayerColors(), game.getPlayerChars());
			//This line sets the size of the BlockSquare object
			temp.setSize(45, 45);
			//This line makes it so that the cyclePlayerUsing function of the BlockSquare object gets run if it gets clicked on
			temp.addActionListener(e -> {
				if (validatePlacement(temp)) 
				{
					//System.out.println("Human move");
					squareClick(temp);
				}
			});


			//This is part of a mouse listener that creates the light gray highlight of the piece when hovering over a square

			temp.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(java.awt.event.MouseEvent evt) {
					for (int x = 0; x < 5; x++) 
					{
						for (int y = 0; y < 5; y++) 
						{
							if (temp.getFiveSurroundIndex(x, y) != null && ActivePiece[x][y] != 0) 
							{
								temp.getFiveSurroundIndex(x, y).setBackground(Color.LIGHT_GRAY);
							}
						}
					}
				}

				//This is the other part of the mouse listener, it gets rid of the light gray highlight when the cursor moves off of the square
				public void mouseExited(java.awt.event.MouseEvent evt) {
					for (int x = 0; x < 5; x++) 
					{
						for (int y = 0; y < 5; y++) 
						{
							if (temp.getFiveSurroundIndex(x, y) != null && ActivePiece[x][y] != 0) 
							{
								if(temp.getFiveSurroundIndex(x, y).getShowingHint()) 
								{
									temp.getFiveSurroundIndex(x, y).setBackground(Color.DARK_GRAY);
								}
								else 
								{
									temp.getFiveSurroundIndex(x, y).setBackground(temp.getFiveSurroundIndex(x, y).getPlayerColor());
								}
							}
						}
					}
				}
			});
			tiles[index1][index2] = temp;
			BoardPanel.add(temp);
		}

		
		//This gives every tile on the board a reference to the tiles surrounding it in a 5x5 grid
		for (int i = 0; i < 20; i++) 
		{
			for (int j = 0; j < 20; j++) 
			{
				BlockSquare temp = tiles[i][j];
				int centerCoords[] = temp.getCoords();

				if (temp.getFiveSurround() == null) 
				{
					BlockSquare tempFiveSurround[][] = {{null,null,null,null,null},{null,null,null,null,null},{null,null,null,null,null},{null,null,null,null,null},{null,null,null,null,null}};
					temp.setFiveSurround(tempFiveSurround);
					for (int x = 0; x < 5; x++) 
					{
						for (int y = 0; y < 5; y++) 
						{
							if ((centerCoords[0] + (x-2) > -1 && centerCoords[0] + (x-2) < 20) && (centerCoords[1] + (y-2) > -1 && centerCoords[1] + (y-2) < 20)) 
							{
								temp.setFiveSurround(tiles[centerCoords[0] + (x-2)][centerCoords[1] + (y-2)], x, y);
							}
						}
					}
				}
			}
		}

		//This line runs the LoadBoard function if the resuming parameter of this function is true
		if(resuming) {LoadBoard();}

		//These lines set up the panel that will contain the pieces of the game
		PiecesPanel.setSize(350,9000);
		PiecesPanel.setLayout(new FlowLayout(FlowLayout.CENTER,50,25));
		PiecesPanel.setPreferredSize(new Dimension(350, 2800));
		//These lines add labels that will state the values of various variables to the PiecesPanel
		activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
		activePlayerLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		PiecesPanel.add(activePlayerLabel);
		if (game.getDifficulty() != 0) 
		{
			PiecesPanel.add(new JLabel("<html>The difficulty of the CPU players<br>has been set to: " + game.getDifficultyString() + "."));
		}
		
		//This adds the buttons to pick the pieces
		for (int i = 0; i < 21; i++) 
		{
			JButton button = new JButton();
			try {
				Image img = ImageIO.read(getClass().getResource(pieces.pieceNameStrings[i]));
				button.setIcon(new ImageIcon(img));
			} catch (Exception ex) {
				//System.out.println(ex);
			}

			button.setText(pieces.pieceNameStrings[i]);
			button.setFont(new Font("Dialog", Font.PLAIN, -0));

			button.addActionListener(e -> {
				JButton thisButton = (JButton)e.getSource();
				ActivePiece = pieces.getPiece(thisButton.getText());
				ActivePieceCorners = pieces.getCornerInfo(thisButton.getText());
				activePieceButton = thisButton;
			});

			button.setPreferredSize(new Dimension(200,100));
			pieceButtons[i] = button;
			PiecesPanel.add(button);
		}


		//These lines set up the panel that will contain the options that will be presented to the player
		OptionsPanel.setSize(350,900);
		OptionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 14));
		OptionsPanel.setPreferredSize(new Dimension(350, 150));

		//This line declares and initializes a font that will be used for the buttons
		Font buttonFont = new Font("Dialog", Font.PLAIN, 140);

		//These lines set up the hint button
		HintButton.setPreferredSize(new Dimension(200,200));
		HintButton.setFont(new Font("Dialog", Font.BOLD, 27));
		HintButton.addActionListener(e -> UpdateHintButton());
		HintButton.setSelected(game.getHints());
		UpdateHintButton();

		//These lines set up the clockwise rotate, counterclockwise rotate, and flip buttons
		CWRotate.setPreferredSize(new Dimension(200,200));
		CWRotate.setFont(buttonFont);
		CWRotate.addActionListener(e -> {
			cwRotate();
		});
		CCWRotate.setPreferredSize(new Dimension(200,200));
		CCWRotate.setFont(buttonFont);
		CCWRotate.addActionListener(e -> {
			ccwRotate();
		});
		Flip.setPreferredSize(new Dimension(200,200));
		Flip.setFont(buttonFont);
		Flip.addActionListener(e -> {
			flip();
		});

		//These lines add the four buttons to the options panel
		OptionsPanel.add(HintButton);
		OptionsPanel.add(CWRotate);
		OptionsPanel.add(CCWRotate);
		OptionsPanel.add(Flip);

		//These lines add the three panels to the window, in their appropriate places
		BoardWindow.add(BoardPanel, BorderLayout.CENTER);
		BoardWindow.add(OptionsPanel, BorderLayout.EAST);
		//These lines add the PiecesPanel to the window, while being inside of a JScrollPane 
		JScrollPane PiecesScrollPane = new JScrollPane(PiecesPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		PiecesScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		BoardWindow.getContentPane().add(BorderLayout.WEST, PiecesScrollPane);

		//This line makes it so that the window will appear in the center of the screen
		BoardWindow.setLocationRelativeTo(null);

		BoardWindow.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		//This line makes the window visible
		BoardWindow.setVisible(true);
	}



	//This is the general function that is called to make a CPU player make a move
	public void GenerateMove() 
	{
		//
		//
		//BRUTE FORCE!!!
		//
		//

		//this makes an arraylist of available pieces
		ArrayList<Integer> pieceSelectionArray = new ArrayList<Integer>();
		for (int i = 0; i < 21; i++) 
		{
			if (activePlayer.getAvailablePieces()[i]) 
			{
				pieceSelectionArray.add(i);
			}
		}


		//This is the loop for trying to find a place for the CPU to place the piece
		BlockSquare bs = null;
		int loopCounter = 0;
		while((bs == null) && (loopCounter < 21)) 
		{
			loopCounter++;
			if (activePlayer instanceof CPUPlayer) 
			{	
				//this picks a piece that isn't already placed
				int pieceInt = ((CPUPlayer)activePlayer).decideOnPiece();
				if (pieceInt == -1) {
					
					//if none are found this happens
					skipPlayers[activePlayer.getPlayerNumber()] = true;
					System.out.println("Only in GenerateMove");
					System.out.println(String.valueOf(skipPlayers[0]));
					System.out.println(String.valueOf(skipPlayers[1]));
					System.out.println(String.valueOf(skipPlayers[2]));
					System.out.println(String.valueOf(skipPlayers[3]));
					
					//moves play to the next available player
					playerCycle();
					return;
				}
				else 
				{
					//valid piece is found
					ActivePiece = pieces.pieceInfo[pieceInt];
					ActivePieceCorners = pieces.cornerInfo[pieceInt];
					activePieceButton = pieceButtons[pieceInt];
					bs = testPlacement();
					//valid place is found, place the piece
					squareClick(bs);
				}
			}
		}
		if (loopCounter == 21) 
		{
			return;
		}
	}


	//This function tries a given piece in every possible orientation for every single tile
	//It picks the starting corner at random
	public BlockSquare testPlacement()
	{
		Random rand = new Random(System.currentTimeMillis());
		int corner = rand.nextInt(4);
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				boolean flipFlag = false;
				for (int k = 0; k < 8; k++) 
				{
					if(corner == 0)
					{
						if(validatePlacement(tiles[i][j]))
						{
							return tiles[i][j];
						}
					}
					else if(corner == 1)
					{
						if(validatePlacement(tiles[i][19-j]))
						{
							return tiles[i][19-j];
						}
					}
					else if(corner == 2)
					{
						if(validatePlacement(tiles[19-i][19-j]))
						{
							return tiles[19-i][19-j];
						}
					}
					else if(corner == 3)
					{
						if(validatePlacement(tiles[19-i][j]))
						{
							return tiles[19-i][j];
						}
					}

					cwRotate();
					if (k >= 3 && !flipFlag) 
					{
						flip();
					}
				}
			}
		}
		return null;
	}







	//places the active piece on tile temp
	void squareClick(BlockSquare temp)
	{
		if (temp == null) {
			playerCycle();
			return;
		}

		for (int x = 0; x < 5; x++) 
		{
			for (int y = 0; y < 5; y++) 
			{
				if (temp.getFiveSurroundIndex(x, y) != null && ActivePiece[x][y] != 0) 
				{
					temp.getFiveSurroundIndex(x, y).changePlayerUsing(activePlayer.getPlayerNumber()+1);
					temp.getFiveSurroundIndex(x, y).setShowingHint(false);
				}
			}
		}
		activePlayer.addScore(ActivePiece[2][2]);

		ActivePiece = emptyPiece;
		ActivePieceCorners = emptyCorners;
		activePlayer.placed();
		System.out.println("Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + ": "+ activePlayer.getAvailablePiecesAsString());
		for (int j = 0; j < 21; j++) 
		{
			if (pieceButtons[j].equals(activePieceButton)) 
			{
				activePlayer.pieceUsed(j);
			}
		}
		//checks to see if a player has placed all of their pieces
		if (activePlayer.noBlocksRemaining()) 
		{
			endGame(true);
		}
		
		playerCycle();

		//removes current hint
		clearHint();
		if (activePlayer instanceof HumanPlayer && game.getHints()) 
		{
			//tests to see if any valid moves can be made, if so generates new hint
			if (!generateHint(true)) 
			{
				skipPlayers[activePlayer.getPlayerNumber()] = true;
				System.out.println("Second in SquareClick");
				System.out.println(String.valueOf(skipPlayers[0]));
				System.out.println(String.valueOf(skipPlayers[1]));
				System.out.println(String.valueOf(skipPlayers[2]));
				System.out.println(String.valueOf(skipPlayers[3]));

				playerCycle();
			}

		}
		clearHint();
		UpdatePieceSelection();
		HintButton.setSelected(false);
		UpdateHintButton();
		HintButton.setSelected(true);
		UpdateHintButton();
		
	}

	//cycles to next available player
	//makes them try to make a move
	public void playerCycle() 
	{
		if (activePlayer.getPlayerNumber() == 0) 
		{
			if (!skipPlayers[1]) 
			{
				activePlayer = game.getPlayer(1);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");;
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[2]) 
			{
				activePlayer = game.getPlayer(2);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[3]) 
			{
				activePlayer = game.getPlayer(3);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[0]) 
			{
				//Do nothing
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else 
			{
				endGame(false);
			}
		}
		else if (activePlayer.getPlayerNumber() == 1) 
		{
			if (!skipPlayers[2]) 
			{
				activePlayer = game.getPlayer(2);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[3]) 
			{
				activePlayer = game.getPlayer(3);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[0]) 
			{
				activePlayer = game.getPlayer(0);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[1]) 
			{
				//Do nothing
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else 
			{
				endGame(false);
			}
		}
		else if (activePlayer.getPlayerNumber() == 2) 
		{
			if (!skipPlayers[3]) 
			{
				activePlayer = game.getPlayer(3);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[0]) 
			{
				activePlayer = game.getPlayer(0);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[1]) 
			{
				activePlayer = game.getPlayer(1);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[2]) 
			{
				//Do nothing
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else 
			{
				endGame(false);
			}
		}
		else if (activePlayer.getPlayerNumber() == 3) 
		{
			if (!skipPlayers[0]) 
			{
				activePlayer = game.getPlayer(0);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[1]) 
			{
				activePlayer = game.getPlayer(1);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[2]) 
			{
				activePlayer = game.getPlayer(2);
				activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else if (!skipPlayers[3]) 
			{
				//Do nothing
				if (activePlayer.getPlayerNumber() > game.getNumPlayers()-1) 
				{
					GenerateMove();
				}
			}
			else 
			{
				endGame(false);
			}
		}
		else  
		{
			endGame(true);
		}
	}

	
	//ends the game
	public void endGame(boolean allPieces) 
	{
		if (allPieces) 
		{
			JOptionPane.showMessageDialog(BoardWindow, "The winner (by placing all pieces) is: Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "!!!");
		}
		else 
		{
			Player tempWinner = game.getPlayer(0);
			if (game.getPlayer(1).getScore() > tempWinner.getScore()) 
			{
				tempWinner = game.getPlayer(1);
			}
			if (game.getPlayer(2).getScore() > tempWinner.getScore()) 
			{
				tempWinner = game.getPlayer(2);
			}
			if (game.getPlayer(3).getScore() > tempWinner.getScore()) 
			{
				tempWinner = game.getPlayer(3);
			}

			JOptionPane.showMessageDialog(BoardWindow, "The winner (by score) is: Player " + String.valueOf(tempWinner.getPlayerNumber() + 1) + "!!!");
		}

		System.exit(0);
	}

	//this is what the clockwise rotate button does
	void cwRotate()
	{
		int tempPiece[][] = {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
		int tempCorners[][] = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
		for (int x = 0; x < 5; x++) 
		{
			for (int y = 0; y < 5; y++) 
			{
				tempPiece[x][y] = ActivePiece[4-y][x];
			}
		}
		for (int x = 0; x < 7; x++) 
		{
			for (int y = 0; y < 7; y++) 
			{
				tempCorners[x][y] = ActivePieceCorners[6-y][x];
			}
		}
		ActivePiece = tempPiece;
		ActivePieceCorners = tempCorners;
	}
	
	//this is what the counterclockwise rotate button does
	void ccwRotate()
	{
		int tempPiece[][] = {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
		int tempCorners[][] = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
		for (int x = 0; x < 5; x++) 
		{
			for (int y = 0; y < 5; y++) 
			{
				tempPiece[x][y] = ActivePiece[y][4-x];
			}
		}
		for (int x = 0; x < 7; x++) 
		{
			for (int y = 0; y < 7; y++) 
			{
				tempCorners[x][y] = ActivePieceCorners[y][6-x];
			}
		}
		ActivePiece = tempPiece;
		ActivePieceCorners = tempCorners;
	}
	//this is what the flip button does
	void flip()
	{
		int tempPiece[][] = {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
		int tempCorners[][] = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
		for (int x = 0; x < 5; x++) 
		{
			for (int y = 0; y < 5; y++) 
			{
				tempPiece[x][y] = ActivePiece[x][4-y];
			}
		}
		for (int x = 0; x < 7; x++) 
		{
			for (int y = 0; y < 7; y++) 
			{
				tempCorners[x][y] = ActivePieceCorners[x][6-y];
			}
		}
		ActivePiece = tempPiece;
		ActivePieceCorners = tempCorners;
	}






	//This function makes it so that the text of the button and the corresponding variable in the Game class reflect the state of the hint button
	public void UpdateHintButton() 
	{
		if (HintButton.isSelected()) 
		{
			HintButton.setText("<html>Hints are being shown.");
			game.setHints(true);
			generateHint(true);
		}
		else 
		{
			HintButton.setText("<html>Hints are not being shown.");
			clearHint();
			game.setHints(false);

		}
	}

	//this does what the name says, is for local use only
	boolean isInArray(Object obj, Object[] objArray) 
	{
		for (Object element : objArray) {
			if (element == obj) {
				return true;
			}
		}
		return false;
	}
	
	//this refreshes the piece panel
	public void UpdatePieceSelection() 
	{
		for (int i = 0; i < 21; i++) 
		{
			if (pieceButtons[i].getParent() != null) 
			{
				PiecesPanel.remove(pieceButtons[i]);
			}
		}
		for (int i = 0; i < 21; i++) 
		{
			if (activePlayer.getAvailablePieces()[i]) 
			{
				PiecesPanel.add(pieceButtons[i]);
			}
		}
		PiecesPanel.setVisible(false);
		PiecesPanel.setVisible(true);
	}
	
	//this function tests to see if active piece can be placed on a given tile
	public boolean validatePlacement(BlockSquare square) 
	{
		//this stuff gets the necessary references to other things
		int tempCount = 0;
		int squareCoords[] = square.getCoords();
		BlockSquare activeSquares[] = new BlockSquare[ActivePiece[2][2]];
		for (int x = 0; x < 5; x++) 
		{
			for (int y = 0; y < 5; y++) 
			{
				if ((square.getFiveSurroundIndex(x, y) == null || square.getFiveSurroundIndex(x, y).getPlayerUsing() != 0) && ActivePiece[x][y] != 0) 
				{
					return false;
				}
				if (ActivePiece[x][y] != 0) 
				{
					activeSquares[tempCount] = square.getFiveSurroundIndex(x, y);
					tempCount++;
				}
			}
		}

		//this part checks to see if any of the squares in the piece are in the current players starting corner
		int playerCorner[][] = {{0,19},{19,0},{0,0},{19,19}};
		for (int i = 0; i < activeSquares.length; i++) 
		{
			if (activeSquares[i].getCoords()[1] == playerCorner[activePlayer.getPlayerNumber()][0] && activeSquares[i].getCoords()[0] == playerCorner[activePlayer.getPlayerNumber()][1]) 
			{
				return true;
			}
		}

		//This part checks to see if the piece is connected on the diagonal to another piece of the same color
		boolean atLeastOneGoodCorner = false;
		for (int x = 0; x < 7; x++) 
		{
			for (int y = 0; y < 7; y++) 
			{
				boolean possibleCorner = false;
				boolean activeCorner = false;

				if  (!(squareCoords[0]+(3-x) < 0 || squareCoords[0]+(3-x) > 19 || squareCoords[1]+(3-y) < 0 || squareCoords[1]+(3-y) > 19))
				{
					if (tiles[squareCoords[0]+(3-x)][squareCoords[1]+(3-y)].getPlayerUsing() == activePlayer.getPlayerNumber()+1) 
					{
						possibleCorner = true;
					}
					if (ActivePieceCorners[6-x][6-y] == 1) 
					{
						activeCorner = true;
					}
					if (possibleCorner && activeCorner)
					{
						atLeastOneGoodCorner = true;
					}
				}
			}

		}
		if (!atLeastOneGoodCorner) 
		{
			return false;
		}


		//This part makes sure that none of the squares in the piece are adjacent to any tiles of the same color
		for (int i = 0; i < activeSquares.length; i++) 
		{
			int tempSquareCoords[] = activeSquares[i].getCoords();
			BlockSquare adjSquares[] = new BlockSquare[4];
			adjSquares[0] = tempSquareCoords[1]+1 > 19 ? null : tiles[tempSquareCoords[0]][tempSquareCoords[1]+1];
			adjSquares[1] = tempSquareCoords[0]+1 > 19 ? null : tiles[tempSquareCoords[0]+1][tempSquareCoords[1]];
			adjSquares[2] = tempSquareCoords[1]-1 < 0 ? null : tiles[tempSquareCoords[0]][tempSquareCoords[1]-1];
			adjSquares[3] = tempSquareCoords[0]-1 < 0 ? null : tiles[tempSquareCoords[0]-1][tempSquareCoords[1]];
			for (int j = 0; j < 4; j++) 
			{

				if ((adjSquares[j] != null && !isInArray(adjSquares[j], activeSquares)) && adjSquares[j].getPlayerUsing() == activePlayer.getPlayerNumber()+1) 
				{
					return false;
				}
			}
		}
		return true;
	}

	//this generates a hint and shows it
	//it's pretty much the same as GenerateMove, but it dsiplays that a move can be made rather than making it
	public boolean generateHint(boolean display) 
	{
		ArrayList<Integer> pieceSelectionArray = new ArrayList<Integer>();
		for (int i = 0; i < 21; i++) 
		{
			if (activePlayer.getAvailablePieces()[i]) 
			{
				pieceSelectionArray.add(i);
			}
		}
		BlockSquare bs = null;
		int loopCounter = 0;
		while((bs == null) && (loopCounter < 21)) 
		{
			loopCounter++;
			int pieceInt;
			if (activePlayer instanceof HumanPlayer) 
			{	
				pieceInt = ((HumanPlayer)activePlayer).decideOnPiece();
			}
			else 
			{
				pieceInt = ((CPUPlayer)activePlayer).decideOnPiece();
			}

			if (pieceInt != -1) 
			{
				ActivePiece = pieces.pieceInfo[pieceInt];
				ActivePieceCorners = pieces.cornerInfo[pieceInt];
				activePieceButton = pieceButtons[pieceInt];
				bs = testPlacement();
			}
			else 
			{
				return false;
			}
		}

		if (loopCounter == 21) 
		{
			return false;
		}

		activePlayer.placed();
		if (display) 
		{
			hintBlockSquare = bs;

			for (int x = 0; x < 5; x++) 
			{
				for (int y = 0; y < 5; y++) 
				{
					if (bs.getFiveSurroundIndex(x, y) != null && ActivePiece[x][y] != 0) 
					{
						bs.getFiveSurroundIndex(x, y).setBackground(Color.DARK_GRAY);
						bs.getFiveSurroundIndex(x, y).setShowingHint(true);
					}
				}
			}
		}
		return true;
	}


	//this clears the currently displayed hint
	public void clearHint() 
	{
		for (int x = 0; x < 20; x++) 
		{
			for (int y = 0; y < 20; y++) 
			{
				if (tiles[x][y].getBackground().equals(Color.DARK_GRAY)) 
				{
					//System.out.println("hi");
					tiles[x][y].setBackground(Color.WHITE);
					if (tiles[x][y].getShowingHint()) 
					{
						tiles[x][y].setShowingHint(false);
					}
				}
			}
		}
	}



	//This function records the current state of the board to a text file
	public void RecordBoard()
	{
		//This line declares the boardStrings variable and initializes it with 20 empty strings
		String[] boardStrings = {"","","","","","","","","","","","","","","","","","","",""};

		//This try catch statement sets the text file blank
		try {
			FileWriter writer = new FileWriter("Save.txt", false);
			writer.write("");
			writer.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		//These nested for loops add the value of the player using each of the tiles to the correct string in boardStrings
		for (int x = 0; x < 20; x++) 
		{
			for (int y = 0; y < 20; y++) 
			{
				boardStrings[x] = boardStrings[x] + Integer.toString(tiles[x][y].playerUsing); 
			}

			//This try catch statement writes the values of boardStrings to the text file, line by line
			try {
				FileWriter writer = new FileWriter("Save.txt", true);
				writer.write(boardStrings[x]);
				writer.write("\r\n");
				writer.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		//This try catch statement writes the settings of the game to a new line in the text file
		try {
			FileWriter writer = new FileWriter("Save.txt", true);
			writer.write(game.getSettings());
			writer.write("\r\n");
			for (int i = 0; i < 4; i++) 
			{
				writer.write(game.getAllPlayersAvailablePieces()[i]);
				writer.write("\r\n");
			}
			for (int i = 0; i < 2; i++) 
			{
				writer.write(game.getStylesStrings()[i]);
				writer.write("\r\n");
			}
			for (int i = 0; i < 4; i++) 
			{
				if (i == 3) 
				{
					writer.write(String.valueOf(game.getPlayer(i).getScore()));
				}
				else 
				{
					writer.write(String.valueOf(game.getPlayer(i).getScore()) + " ");
				}
			}
			writer.write("\r\n");
			writer.write(String.valueOf(activePlayer.getPlayerNumber()));
			writer.write("\r\n");
			writer.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}



	//This function loads the saved state of the board from a text file
	public void LoadBoard() 
	{
		//This line declares and initializes an array of 21 strings
		String[] saveString = new String[29];
		//This line declares an integer variable called lineCounter, and initializes it to 0
		int lineCounter = 0;

		//This try catch statement tries to open a buffered reader on the text file Save.txt
		try {
			BufferedReader br = new BufferedReader(new FileReader("Save.txt"));
			String st; 
			//This try catch statement reads the data from a text file into saveString
			try {
				while ((st = br.readLine()) != null) {
					saveString[lineCounter] = st;
					lineCounter++;}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			//This try catch statement runs if there is no text file to read from, and it creates a new text file
			try {
				FileWriter writer = new FileWriter("Save.txt", false);
				writer.write("");
				writer.close();
			} 
			catch (IOException a) {
				e.printStackTrace();
			}
		} 

		//This if statement runs if the first element of the saveString array is not null
		if (saveString[0] != null) 
		{
			activePlayer = game.getPlayer(Integer.valueOf(saveString[28]));
			activePlayerLabel.setText("It is currently Player " + String.valueOf(activePlayer.getPlayerNumber() + 1) + "'s turn.");
			String derrr[] = saveString[25].split("\\s+");
			game.setStylesStrings(derrr,saveString[26]);
			//These nested for loops set the player using all of the tiles to their corresponding values in saveString
			for (int i = 0; i < 20; i++) 
			{
				for (int j = 0; j < 20; j++) 
				{
					tiles[i][j].changePlayerUsing(Integer.parseInt(saveString[i].substring(j, j+1)));
					tiles[i][j].setStyles(game.getPlayerColors(), game.getPlayerChars());
				}
			}
			//This line sets the settings of the game according to the strings in elements 20 through 27 
			//of saveString
			game.setSettings(saveString[20]);
			String[] tempStrings = {saveString[21],saveString[22],saveString[23],saveString[24]};
			game.setAllPlayersAvailablePieces(tempStrings);
			String derr[] = saveString[27].split("\\s+");
			game.setScores(derr);
			HintButton.setSelected(game.getHints());
			UpdateHintButton();
		}
	}
}
