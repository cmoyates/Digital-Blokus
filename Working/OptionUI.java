import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class OptionUI extends JPanel implements ActionListener 
{
    private JFrame optionScreen;
    private String[] chosenColours, chosenSymbols;
    private Color[] chosenColours2;
    private JComboBox colourListP1,colourListP2,colourListP3,colourListP4,symbolListP1,symbolListP2,symbolListP3,symbolListP4;
    private JLabel p1,p2,p3,p4;
    private JButton apply;
    private Game game;

    public OptionUI(Game gameObject)
    {
    	String[] colours = {"","Blue","Green","Red","Yellow"};
    	colourListP1 = new JComboBox(colours);
    	colourListP1.setPreferredSize(new Dimension(200,50));
    	colourListP1.setFont(new Font("Dialog", Font.PLAIN, 40));
    	colourListP2 = new JComboBox(colours);
    	colourListP2.setPreferredSize(new Dimension(200,50));
    	colourListP2.setFont(new Font("Dialog", Font.PLAIN, 40));
    	colourListP3 = new JComboBox(colours);
    	colourListP3.setPreferredSize(new Dimension(200,50));
    	colourListP3.setFont(new Font("Dialog", Font.PLAIN, 40));
    	colourListP4 = new JComboBox(colours);
    	colourListP4.setPreferredSize(new Dimension(200,50));
    	colourListP4.setFont(new Font("Dialog", Font.PLAIN, 40));
    	colourListP1.addActionListener(this);
    	colourListP2.addActionListener(this);
    	colourListP3.addActionListener(this);
    	colourListP4.addActionListener(this);
    	
    	String[] symbols = {"","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
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
    	symbolListP1.addActionListener(this);
    	symbolListP2.addActionListener(this);
    	symbolListP3.addActionListener(this);
    	symbolListP4.addActionListener(this);
    	
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
    	
    	chosenColours = new String[5];
    	chosenSymbols = new String[27];
    	
        this.game = gameObject;
        optionScreen = new JFrame("Change Options");
        setup();
    }

    public void setup()
    {
        optionScreen.setSize(500,325);
        optionScreen.setLayout(new FlowLayout());
        optionScreen.add(p1);
        optionScreen.add(colourListP1);
        optionScreen.add(symbolListP1);
        optionScreen.add(p2);
        optionScreen.add(colourListP2);
        optionScreen.add(symbolListP2);
        optionScreen.add(p3);
        optionScreen.add(colourListP3);
        optionScreen.add(symbolListP3);
        optionScreen.add(p4);
        optionScreen.add(colourListP4);
        optionScreen.add(symbolListP4);
        optionScreen.add(apply);
        
        optionScreen.setLocationRelativeTo(null);
        optionScreen.setResizable(false);
        optionScreen.setVisible(true);    
    }
    
    public Color[] getColours()
    {
    	chosenColours2 = new Color[4];
    	for(int i=0;i<4;i++)
    	{
    		if(chosenColours[i].equals("Blue"))
    		{
    			chosenColours2[i] = Color.BLUE;
    		}
    		else if(chosenColours[i].equals("Green"))
    		{
    			chosenColours2[i] = Color.GREEN;
    		}
    		else if(chosenColours[i].equals("Red"))
    		{
    			chosenColours2[i] = Color.RED;
    		}
    		else if(chosenColours[i].equals("Yellow"))
    		{
    			chosenColours2[i] = Color.YELLOW;
    		}
    	}
    	return chosenColours2;
    }
    
    public String[] getSymbols()
    {
    	return chosenSymbols;
    }
    
    public void actionPerformed(ActionEvent e) 
    {
    	if(e.getSource() instanceof JButton)
    	{
    		JButton button = (JButton)e.getSource();
    		if(button.equals(apply))
    		{
    			optionScreen.dispose();
    		}
    	}
    	else if(e.getSource() instanceof JComboBox)
    	{
    		JComboBox cb = (JComboBox)e.getSource();
    		if(cb.equals(colourListP1))
    		{
    			chosenColours[0] = cb.getSelectedItem().toString();
    		}
    		else if(cb.equals(colourListP2))
    		{
    			chosenColours[1] = cb.getSelectedItem().toString();
    		}
    		else if(cb.equals(colourListP3))
    		{
    			chosenColours[2] = cb.getSelectedItem().toString();
    		}
    		else if(cb.equals(colourListP4))
    		{
    			chosenColours[3] = cb.getSelectedItem().toString();
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
    }
}
