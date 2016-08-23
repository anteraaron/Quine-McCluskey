/**
 * The functionalities and buttons of the GUIFrame
 */
package tabulationMethod;

import java.util.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Simulator extends JPanel implements MouseListener{

	/**
	 * 
	 */
	//Instantiates the components needed for the frame.
	private static final long serialVersionUID = 1L;
	private JTextField inputArea;
	public JLabel answer, begin, reset,back, ins, inputhere ,outputhere, choose;
	public JScrollPane scroll;
	public ButtonGroup group = new ButtonGroup();
	public JRadioButton deflt, custom;
	public JTextArea sagot;
	public JButton start;
	public Image bgSim, startAlgo;
	public String output = new String();
	private StringTokenizer varTokenizer;
	public static ArrayList<Integer> array;
	private ArrayList<String> baryaballs;
	public ImageIcon sA,rB,hoverImg1,hoverImg2,b,bh,i,ih;
	public int useVar = 0;
	@SuppressWarnings("unused")
	private JScrollPane pane;
	private String vars;
	
	/**
	 * Constructor
	 */
	public Simulator(){
	
	setLayout(null); //absolute positioning
	
	//Area for the user to input minterms
	inputArea = new JTextField("", 500);	
	inputhere = new JLabel();
	inputhere.setText("INPUT:");
	inputhere.setForeground(Color.WHITE);
	inputhere.setFont(new Font("Serif", Font.BOLD, 30));
	inputhere.setBounds(78, 115, 250, 50);
	
	//Radio buttons for the user to choose type of variables to be used
	choose = new JLabel();
	choose.setText("Variable Input:");
	choose.setForeground(Color.WHITE);
	choose.setFont(new Font("Serif", Font.BOLD, 30));
	choose.setBounds(10, 0, 240, 60);
	//Radio button for default variables
	deflt = new JRadioButton("Default Variables");
	deflt.setBounds(10, 60, 140, 20);
	deflt.setBorderPainted(false);
	deflt.setForeground(Color.WHITE);
	deflt.setFont(new Font("Serif", Font.BOLD, 15));
	deflt.setContentAreaFilled(false);
	deflt.setFocusable(false);
	deflt.addMouseListener(this);
	//Radio buttons for user defined variables
	custom = new JRadioButton("User Input Variables");
	custom.setBounds(10, 90, 190,20);
	custom.setBorderPainted(false);
	custom.setForeground(Color.WHITE);
	custom.setFont(new Font("Serif", Font.BOLD, 15));
	custom.setContentAreaFilled(false);
	custom.setFocusable(false);
	custom.addMouseListener(this);
	//adds the radio buttons to the radio group
	group.add(deflt);
	group.add(custom);
	
	deflt.setSelected(true); 
	//adds the component to the simulator panel.
	add(custom);
	add(deflt);
	add(choose);
	add(inputhere);
	add(inputArea);	
	inputArea.setBounds(78, 160, 450, 30);
	
	//output area for the simplified minterms
	sagot = new JTextArea("", 10, 450);
	sagot.setLineWrap(true);
	sagot.setEditable(false);
	sagot.setFont(new Font("Arial", Font.BOLD, 12));
	sagot.setForeground(Color.BLACK);
	sagot.setMargin(new Insets(15,15,15,15));
	//adds scrolling functionality for the outputarea
	scroll = new JScrollPane(sagot);
	scroll.setBounds(78, 290, 450, 200);
	
	//Label
	outputhere = new JLabel();
	outputhere.setText("OUTPUT:");
	outputhere.setForeground(Color.WHITE);
	outputhere.setFont(new Font("Serif", Font.BOLD, 30));
	outputhere.setBounds(78, 250, 250, 50);
	
	//Adds the component to the simulator panel.
	add(outputhere);
	add(scroll);
	
	//Gets images for the components
	ImageIcon binary = new ImageIcon(this.getClass().getResource("binarybackground.jpg"));
	bgSim = binary.getImage();
	
	sA = new ImageIcon(this.getClass().getResource("hover1.png"));
	rB = new ImageIcon(this.getClass().getResource("hover4.png"));
	
	hoverImg1 = new ImageIcon(this.getClass().getResource("hover2.png"));
	hoverImg2 = new ImageIcon(this.getClass().getResource("hover3.png"));
	//Button to start simulating QM
	begin = new JLabel();
	begin.setIcon(sA);
	begin.setVisible(true);
	begin.setBounds(177, 190, 118, 58);
	begin.addMouseListener(this);
	add(begin);
	//Resets user input and output
	reset = new JLabel();
	reset.setIcon(rB);
	reset.setVisible(true);
	reset.setBounds(293, 190, 120, 58);
	reset.addMouseListener(this);
	add(reset);
	
	//button properties
	b = new ImageIcon(this.getClass().getResource("b.png"));
	bh = new ImageIcon(this.getClass().getResource("bh.png"));
	i = new ImageIcon(this.getClass().getResource("i.png"));
	ih = new ImageIcon(this.getClass().getResource("ih.png"));
	
	back = new JLabel();
	back.setIcon(b);
	back.setVisible(true);
	back.setBounds(168, 510, 129, 58);
	back.addMouseListener(this);
	add(back);
	
	ins = new JLabel();
	ins.setIcon(i);
	ins.setVisible(true);
	ins.setBounds(285, 510, 131, 58);
	ins.addMouseListener(this);
	add(ins);
	
	
	}
	
	@Override
	public void paintComponent(Graphics g){
	g.drawImage(bgSim,0,0,null);
	}

	/**
	 * Action Listeners for the Simulator Panel
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource() == begin){
			InputHandler handler = new InputHandler(inputArea.getText());
			int i = 0;
			@SuppressWarnings("unused")
			int varNum = 0;
			if(handler.isCanProceed()){
				if(useVar == 0){
					array = handler.beginAlgo();
					output = new QuineMcCluskey(array).getAnswer(); //Instantiate QM Class with no user input variable
					sagot.setText(output);
				}
				else{
					array = handler.beginAlgo();
					Collections.sort(array);
					while(true){	
						if((int)Math.pow(2,i)-1 < array.get(array.size()-1)){
							i++;
						}else{
							varNum = i; //sets the number of variables
							break;
						}
					}
					if(i == 1){
					vars = JOptionPane.showInputDialog(null, "Input "+i+" variable", "Example: a", JOptionPane.PLAIN_MESSAGE);
					
					}
					else if(i > 1){
					vars = JOptionPane.showInputDialog(null, "Input "+i+" variable", "Example: a", JOptionPane.PLAIN_MESSAGE);	
					}
					vars = vars.replaceAll(" ", "");
					varTokenizer = new StringTokenizer(vars, ",");
					baryaballs = new ArrayList<String>();
					
					while(varTokenizer.hasMoreTokens()){
					baryaballs.add(varTokenizer.nextToken());	
					}
					
					output = new QuineMcCluskey(array,baryaballs).getAnswer(); //Instantiates QM Class with user defined variables as parameter
	
					sagot.setText(output);
					
				}
			}
			else{
			//Outputs this statement if the user inputted a wrong syntax
			sagot.setText("INVALID INPUT! Please check that: \n \n1. Your input should not be empty.\n2. Your input should not contain special characters.\n3. Your input should not contain duplicated minterms.\n4. Should not contain negative numbers.\n5. Should not exceed 67,108,863.");	
			}
			
		}
		else if(arg0.getSource() == reset){
			inputArea.setText("");
			sagot.setText("");
			useVar = 0;
			group.clearSelection();
			deflt.setSelected(true);
		}
		else if(arg0.getSource() == back){
			inputArea.setText("");
			sagot.setText("");
			useVar = 0;
			group.clearSelection();
			GUIFrame.cl.show(GUIFrame.panels, "WelcomePanel");
		}
		else if(arg0.getSource() == ins){
			GUIFrame.cl.show(GUIFrame.panels, "InstructionsPanel");
		}
		else if(arg0.getSource() == deflt){
			useVar = 0;
		}
		else if(arg0.getSource() == custom){
			useVar = 1;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(arg0.getSource() == begin){
			begin.setIcon(hoverImg1);
		}
		else if(arg0.getSource() == reset){
			reset.setIcon(hoverImg2);
		}
		else if(arg0.getSource() == back){
			back.setIcon(bh);
		}
		else if(arg0.getSource() == ins){
			ins.setIcon(ih);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if(arg0.getSource() == begin){
			begin.setIcon(sA);
		}
		else if(arg0.getSource() == reset){
			reset.setIcon(rB);
		}
		else if(arg0.getSource() == back){
			back.setIcon(b);
		}
		else if(arg0.getSource() == ins){
			ins.setIcon(i);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
