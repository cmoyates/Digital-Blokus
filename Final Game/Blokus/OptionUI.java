import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class OptionUI extends JPanel implements ActionListener 
{
    //
	//
	//This is a GUI class, it's exactly what you'd expect
	//
	//
	
	private JFrame optionScreen;
    private String[] chosenColors, chosenSymbols;
    private Color[] chosenColors2;
    private JComboBox colorListP1,colorListP2,colorListP3,colorListP4,symbolListP1,symbolListP2,symbolListP3,symbolListP4;
    private JLabel p1,p2,p3,p4;
    private JButton apply;
    private Game game;
    
    Dictionary colorLookUp = new Hashtable();
    
    public OptionUI(Game gameObject)
    {
    	colorLookUp.put("Black", Color.black);
    	colorLookUp.put("Blue", Color.blue);
    	colorLookUp.put("Cyan", Color.cyan);
    	colorLookUp.put("Green", Color.green);
    	colorLookUp.put("Magenta", Color.magenta);
    	colorLookUp.put("Orange", Color.orange);
    	colorLookUp.put("Pink", Color.pink);
    	colorLookUp.put("Red", Color.red);
    	colorLookUp.put("White", Color.white);
    	colorLookUp.put("Yellow", Color.yellow);
    	
    	chosenColors = new String[5];
    	chosenSymbols = new String[27];
    	
    	String[] colors = {"Black","Blue","Cyan","Green","Magenta","Orange","Pink","Red","White","Yellow"};
    	colorListP1 = new JComboBox(colors);
    	colorListP1.setPreferredSize(new Dimension(200,50));
    	colorListP1.setFont(new Font("Dialog", Font.PLAIN, 40));
    	colorListP1.setSelectedItem("Red");
    	colorListP2 = new JComboBox(colors);
    	colorListP2.setPreferredSize(new Dimension(200,50));
    	colorListP2.setFont(new Font("Dialog", Font.PLAIN, 40));
    	colorListP2.setSelectedItem("Blue");
    	colorListP3 = new JComboBox(colors);
    	colorListP3.setPreferredSize(new Dimension(200,50));
    	colorListP3.setFont(new Font("Dialog", Font.PLAIN, 40));
    	colorListP3.setSelectedItem("Green");
    	colorListP4 = new JComboBox(colors);
    	colorListP4.setPreferredSize(new Dimension(200,50));
    	colorListP4.setFont(new Font("Dialog", Font.PLAIN, 40));
    	colorListP4.setSelectedItem("Yellow");
    	colorListP1.addActionListener(this);
    	colorListP2.addActionListener(this);
    	colorListP3.addActionListener(this);
    	colorListP4.addActionListener(this);
    	UpdateComboBoxStatus(colorListP1);
    	UpdateComboBoxStatus(colorListP2);
    	UpdateComboBoxStatus(colorListP3);
    	UpdateComboBoxStatus(colorListP4);
    	
    	String[] symbols = {" ","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    	symbolListP1 = new JComboBox(symbols);
    	symbolListP1.setPreferredSize(new Dimension(200,50));
    	symbolListP1.setFont(new Font("Dialog", Font.PLAIN, 40));
    	symbolListP2 = new JComboBox(symbols);
    	symbolListP2.setPreferredSize(new Dimension(200,50));
    	symbolListP2.setFont(new Font("Dialog", Font.PLAIN, 40));
    	symbolListP3 = new JComboBox(symbols);
    	symbolListP3.setPreferredSize(new Dimension(200,50));
    	symbolListP3.setFont(new Font("Dialog", Font.PLAIN, 40));
    	symbolListP4 = new JComboBox(symbols);
    	symbolListP4.setPreferredSize(new Dimension(200,50));
    	symbolListP4.setFont(new Font("Dialog", Font.PLAIN, 40));
    	symbolListP1.setSelectedIndex(0);
    	symbolListP2.setSelectedIndex(0);
    	symbolListP3.setSelectedIndex(0);
    	symbolListP4.setSelectedIndex(0);
    	symbolListP1.addActionListener(this);
    	symbolListP2.addActionListener(this);
    	symbolListP3.addActionListener(this);
    	symbolListP4.addActionListener(this);
    	UpdateComboBoxStatus(symbolListP1);
    	UpdateComboBoxStatus(symbolListP2);
    	UpdateComboBoxStatus(symbolListP3);
    	UpdateComboBoxStatus(symbolListP4);
    	
    	p1 = new JLabel("P1: ");
    	p1.setFont(new Font("Dialog", Font.BOLD, 40));
    	p2 = new JLabel("P2: ");
    	p2.setFont(new Font("Dialog", Font.BOLD, 40));
    	p3 = new JLabel("P3: ");
    	p3.setFont(new Font("Dialog", Font.BOLD, 40));
    	p4 = new JLabel("P4: ");
    	p4.setFont(new Font("Dialog", Font.BOLD, 40));
    	
    	apply = new JButton("Apply");
    	apply.setPreferredSize(new Dimension(100,40));
    	apply.setFont(new Font("Dialog", Font.PLAIN, 20));
    	apply.addActionListener(this);
    	
        this.game = gameObject;
        optionScreen = new JFrame("Alter the look for your pieces!");
        setup();
    }

    public void setup()
    {
        optionScreen.setSize(550,325);
        optionScreen.setLayout(new FlowLayout());
        optionScreen.add(p1);
        optionScreen.add(colorListP1);
        optionScreen.add(symbolListP1);
        optionScreen.add(p2);
        optionScreen.add(colorListP2);
        optionScreen.add(symbolListP2);
        optionScreen.add(p3);
        optionScreen.add(colorListP3);
        optionScreen.add(symbolListP3);
        optionScreen.add(p4);
        optionScreen.add(colorListP4);
        optionScreen.add(symbolListP4);
        optionScreen.add(apply);
        
        optionScreen.setLocationRelativeTo(null);
        optionScreen.setResizable(false);
        optionScreen.setVisible(true);    
    }
    
    public Color[] getColors()
    {
    	chosenColors2 = new Color[4];
    	for(int i = 0; i < 4; i++)
    	{
    		chosenColors2[i] = (Color) colorLookUp.get(chosenColors[i]);
    	}
    	return chosenColors2;
    }
    
    public String[] getSymbols()
    {
    	return chosenSymbols;
    }
    
    //this is bunch of if statements that contain the logic for this class
    private void UpdateComboBoxStatus(JComboBox cb) 
    {
    	if(cb.equals(colorListP1))
		{
			chosenColors[0] = cb.getSelectedItem().toString();
		}
		else if(cb.equals(colorListP2))
		{
			chosenColors[1] = cb.getSelectedItem().toString();
		}
		else if(cb.equals(colorListP3))
		{
			chosenColors[2] = cb.getSelectedItem().toString();
		}
		else if(cb.equals(colorListP4))
		{
			chosenColors[3] = cb.getSelectedItem().toString();
		}
		else if(cb.equals(symbolListP1))
		{
			chosenSymbols[0] = cb.getSelectedItem().toString();
		}
		else if(cb.equals(symbolListP2))
		{
			chosenSymbols[1] = cb.getSelectedItem().toString();
		}
		else if(cb.equals(symbolListP3))
		{
			chosenSymbols[2] = cb.getSelectedItem().toString();
		}
		else if(cb.equals(symbolListP4))
		{
			chosenSymbols[3] = cb.getSelectedItem().toString();
		}
    }
    
    public void actionPerformed(ActionEvent e) 
    {
    	if(e.getSource() instanceof JButton)
    	{
    		JButton button = (JButton)e.getSource();
    		if(button.equals(apply))
    		{
    			game.setPlayerColors(getColors());
    			game.setPlayerChars(getSymbols());
    			for (int i = 0; i < 4; i++) 
    			{
    				System.out.println(getSymbols()[i]);
				}
    			optionScreen.dispose();
    		}
    	}
    	else if(e.getSource() instanceof JComboBox)
    	{
    		JComboBox cb = (JComboBox)e.getSource();
    		UpdateComboBoxStatus(cb);
    	}
    }
}