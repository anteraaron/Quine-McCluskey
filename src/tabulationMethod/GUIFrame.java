/**
 * The Main Frame for the GUI of QuineMcCluskey
 */
package tabulationMethod;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.*;

public class GUIFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	Welcome firstPanel = new Welcome();
	Simulator secondPanel = new Simulator();
	Instructions thirdPanel = new Instructions();
	
	public Container container = getContentPane();
	public static JPanel panels;
	public static CardLayout cl;
	
	GUIFrame(){
		//frame's properties
	setResizable(false);
	setVisible(true);
	setTitle("Quine-McClusky Algorithm");
	setSize(600,600);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	//panels for the frame
	panels = new JPanel(new CardLayout());
	panels.add(firstPanel, "WelcomePanel");
	panels.add(secondPanel, "SimulationPanel");
	panels.add(thirdPanel, "InstructionsPanel");
	
	cl = (CardLayout)(panels.getLayout());
	container.add(panels);
	
	cl.show(panels, "WelcomePanel");
	
	}
	
	public static void main(String[] args){
		
		@SuppressWarnings("unused")
		GUIFrame f = new GUIFrame(); //Opens the GUIFrame
		
	}
	
}
