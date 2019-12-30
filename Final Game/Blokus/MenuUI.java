import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

//This is the class for the UI of the main menu of the game
public class MenuUI extends JFrame implements ActionListener
{
	//
	//
	//This is a GUI class, it's exactly what you'd expect
	//
	//
	
	
	//These lines of code declare several variables
	private JFrame setupScreen;
    private JButton one, two, three, four, yes, no, on, off, easy, medium, hard, start, resume;
    private Game game;
    
    
    public MenuUI(Game gameObject)
    {
        //This line initializes the game variable to the gameObject parameter
    	this.game = gameObject;
        //This line initializes the setupScreen variable
    	this.setupScreen = new JFrame("New Game");
        
    	//These lines initialize various buttons
        one = new JButton("1");
        two = new JButton("2");
        three = new JButton("3");
        four = new JButton("4");
        off = new JButton("Off");
        on  = new JButton("On");
        yes = new JButton("Yes");
        no = new JButton("No");
        easy = new JButton("Easy");
        medium = new JButton("Medium");
        hard = new JButton("Hard");
        start = new JButton("Start");
        resume = new JButton("Resume");
    }

  
    public void setupGUI()
    {
        //This line sets the size of the window
    	setupScreen.setSize(500, 600);
        
        //These lines declare and initialize various JLabel variables
        JLabel ins = new JLabel("Choose how many human players down below, and the difficulty to get started!");
        JLabel numof = new JLabel("Number of Players:");
        JLabel diff = new JLabel("Difficulty:");
        JLabel toggle = new JLabel("Use Hints?:");
        JLabel color = new JLabel("<html>Do you have any<br>colour deficiencies?");
        JLabel imgLabel = new JLabel(new ImageIcon(getClass().getResource("Blokus.jpg")));
        
        //This line sets the layout of the window to "null"
        setupScreen.setLayout(null);
        //This line sets the background color of the window to white
        setupScreen.getContentPane().setBackground(Color.WHITE);
        //These lines set the colors of the start and resume buttons to white
        start.setBackground(Color.WHITE);
        resume.setBackground(Color.WHITE);
        //This line makes it so that the window will will appear in the middle of the screen
        setupScreen.setLocationRelativeTo(null);
        
        //These lines of code add all of the labels and buttons to the window
        setupScreen.add(start);
        setupScreen.add(resume);
        setupScreen.add(yes);
        setupScreen.add(no);
        setupScreen.add(color);
        setupScreen.add(on);
        setupScreen.add(off);
        setupScreen.add(toggle);
        setupScreen.add(easy);
        setupScreen.add(medium);
        setupScreen.add(hard);
        setupScreen.add(diff);
        setupScreen.add(one);
        setupScreen.add(two);
        setupScreen.add(three);
        setupScreen.add(four);
        setupScreen.add(numof);
        setupScreen.add(ins);
        setupScreen.add(imgLabel);
        
        //These lines set the locations and sizes of the buttons
        imgLabel.setBounds(165,0, 160,88);
        start.setBounds(179, 375, 100, 80);
        resume.setBounds(181, 467, 95, 60);
        ins.setBounds(25, 68, 450, 51);
        numof.setBounds(187, 105, 125, 25);
        diff.setBounds(210, 220, 125, 25);
        one.setBounds(65, 140, 75, 75);
        two.setBounds(150, 140, 75, 75);
        three.setBounds(235, 140, 75, 75);
        four.setBounds(320, 140, 75, 75);
        easy.setBounds(65,245, 100, 75);
        medium.setBounds(180, 245, 100, 75);
        hard.setBounds(295, 245, 100, 75);
        toggle.setBounds(70, 340, 125, 25);
        on.setBounds(65,375, 75,75);
        off.setBounds(65, 460, 75,75);
        color.setBounds(300, 340, 225, 25);
        yes.setBounds(315,375, 75, 75);
        no.setBounds(315, 460, 75, 75);
        
        //These lines set borders for a few of the buttons
        on.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
        no.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
        start.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        
        //These lines set the colors of all of the buttons
        one.setBackground(Color.RED);
        two.setBackground(Color.GREEN);
        three.setBackground(new Color(255, 240, 77));
        four.setBackground(Color.BLUE);
        one.setForeground(Color.WHITE);
        two.setForeground(Color.WHITE);
        three.setForeground(Color.WHITE);
        four.setForeground(Color.WHITE);
        easy.setBackground(Color.RED);
        medium.setBackground(Color.GREEN);
        hard.setBackground(Color.BLUE);
        easy.setForeground(Color.WHITE);
        medium.setForeground(Color.WHITE);
        hard.setForeground(Color.WHITE);
        on.setForeground(Color.WHITE);
        off.setForeground(Color.WHITE);
        yes.setForeground(Color.WHITE);
        no.setForeground(Color.WHITE);
        on.setBackground(Color.RED);
        off.setBackground(Color.BLUE);
        yes.setBackground(Color.BLACK);
        no.setBackground(Color.BLACK);
        
        //These lines set all of the buttons to not focusable
        one.setFocusable(false);
        two.setFocusable(false);
        three.setFocusable(false);
        four.setFocusable(false);
        on.setFocusable(false);
        off.setFocusable(false);
        yes.setFocusable(false);
        no.setFocusable(false);
        easy.setFocusable(false);
        medium.setFocusable(false);
        hard.setFocusable(false);
        start.setFocusable(false);
        resume.setFocusable(false);
        
        //These lines set the fonts and font sizes of the buttons and labels
        one.setFont(new Font("Arial", Font.PLAIN, 40));
        two.setFont(new Font("Arial", Font.PLAIN, 40));
        three.setFont(new Font("Arial", Font.PLAIN, 40));
        four.setFont(new Font("Arial", Font.PLAIN, 40));
        easy.setFont(new Font("Arial", Font.BOLD, 15));
        medium.setFont(new Font("Arial", Font.BOLD, 17));
        hard.setFont(new Font("Arial", Font.BOLD, 15));
        yes.setFont(new Font("Arial", Font.BOLD, 20));
        no.setFont(new Font("Arial", Font.BOLD, 20));
        on.setFont(new Font("Arial", Font.BOLD, 20));
        off.setFont(new Font("Arial", Font.BOLD, 20));
        ins.setFont(new Font("Arial", Font.BOLD, 12));
        start.setFont(new Font("Arial", Font.BOLD, 20));
        
        //These lines add action listeners to all of the buttons
        one.addActionListener(this);
        two.addActionListener(this);
        three.addActionListener(this);
        four.addActionListener(this);
        on.addActionListener(this);
        off.addActionListener(this);
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        yes.addActionListener(this);
        no.addActionListener(this);
        start.addActionListener(this);
        resume.addActionListener(this);
        
        //This line makes the window visible
        setupScreen.setVisible(true);
    }
    
    //This function that hides this window, and opens the board window
    public void openBoardUI(boolean resuming)
    {
        //This if statement makes it so that the player cannot start the game without selecting the necessary settings
    	if((this.game.getNumPlayers() == 0 || (this.game.getDifficulty() == 0 && this.game.getNumPlayers() != 4)) && !resuming)
        {
        	JOptionPane.showMessageDialog(null, "You have to pick a number of players and a difficulty to start", "Warning!", JOptionPane.ERROR_MESSAGE);
        }
        else{
        
        	
        	
        	setupScreen.setVisible(false);
        	game.DisplayBoard(resuming);
        }
    }
    
    //This function is activated when one of the buttons are clicked
    public void actionPerformed(ActionEvent e)
    {
        //This line declares and initializes the variable "button" to the button that had been clicked
    	JButton button = (JButton) e.getSource();
        
    	//These if statements set the numPlayers variable in the Game object and update the looks of the buttons accordingly
        if(button.getText() == "1")
        {
        	this.game.setNumPlayers(1);
        	button.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            this.two.setBorder(BorderFactory.createEmptyBorder());
            three.setBorder(BorderFactory.createEmptyBorder());
            four.setBorder(BorderFactory.createEmptyBorder());
        }
        else if(button.getText() == "2")
        {
            this.game.setNumPlayers(2);
            button.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            one.setBorder(BorderFactory.createEmptyBorder());
            three.setBorder(BorderFactory.createEmptyBorder());
            four.setBorder(BorderFactory.createEmptyBorder());
        }
        else if(button.getText() == "3")
        {
            this.game.setNumPlayers(3);
            button.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            two.setBorder(BorderFactory.createEmptyBorder());
            one.setBorder(BorderFactory.createEmptyBorder());
            four.setBorder(BorderFactory.createEmptyBorder());
        }
        else if(button.getText() == "4")
        {
            this.game.setNumPlayers(4);
            button.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            two.setBorder(BorderFactory.createEmptyBorder());
            three.setBorder(BorderFactory.createEmptyBorder());
            one.setBorder(BorderFactory.createEmptyBorder());
        }
        
        //These if statements set the difficulty variable in the Game object and update the looks of the buttons accordingly
        else if(button.getText() == "Easy")
        {
            this.game.setDifficulty(1);
            button.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            medium.setBorder(BorderFactory.createEmptyBorder());
            hard.setBorder(BorderFactory.createEmptyBorder());
        }
        else if(button.getText() == "Medium")
        {
            this.game.setDifficulty(2);
            button.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            easy.setBorder(BorderFactory.createEmptyBorder());
            hard.setBorder(BorderFactory.createEmptyBorder());
        }
        else if(button.getText() == "Hard")
        {
            this.game.setDifficulty(3);
            button.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            medium.setBorder(BorderFactory.createEmptyBorder());
            easy.setBorder(BorderFactory.createEmptyBorder());
        }
        
        //These if statements set the hints variable in the Game object and update the looks of the buttons accordingly
        else if(button.getText() == "On")
        {
            this.game.setHints(true);
            button.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            off.setBorder(BorderFactory.createEmptyBorder());
        }
        else if(button.getText() == "Off")
        {
            this.game.setHints(false);
            button.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            on.setBorder(BorderFactory.createEmptyBorder());
        }
        
        //These if statements set the colorDef variable in the Game object and update the looks of the buttons accordingly
        else if(button.getText() == "Yes")
        {
            //this.game.setColorDef(true);
            button.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            no.setBorder(BorderFactory.createEmptyBorder());
            OptionUI optionUI = new OptionUI(game);
        }
        else if(button.getText() == "No")
        {
            //this.game.setColorDef(false);
            button.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
            yes.setBorder(BorderFactory.createEmptyBorder());
            //These lines set the color all of the buttons to to their normal values
            one.setBackground(Color.RED);
            two.setBackground(Color.GREEN);
            three.setBackground(new Color(255, 240, 77));
            four.setBackground(Color.BLUE);
            on.setBackground(Color.RED);
            off.setBackground(Color.BLUE);
            easy.setBackground(Color.RED);
            medium.setBackground(Color.GREEN);
            hard.setBackground(Color.BLUE);
        }
        
        //This if statement runs the openBoardUI function with the parameter "resuming" as false
        else if(button.getText() == "Start")
        {
            openBoardUI(false);
        }
        //This if statement runs if the "Resume" button is clicked
        else if (button.getText() == "Resume") 
        {
        	//This try catch statement makes it so that if there is no saved game, the resume button will only inform the user of this
        	try {
				FileReader reader = new FileReader("Save.txt");
				reader.close();
				//This line runs the openBoardUI function with the parameter "resuming" as true
				openBoardUI(true);
			} catch (FileNotFoundException e1) {
				//This line shows the user a warning that there is now game currently saved
				JOptionPane.showMessageDialog(null, "There is no saved game to resume!", "Warning!", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
}