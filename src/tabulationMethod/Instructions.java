/**
 * The GUI Frame for the Instructions
 */
package tabulationMethod;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Instructions extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel ins;
	private Image bg;
	
	public Instructions(){
	
		setLayout(null);
		setVisible(true);	
		ImageIcon binary = new ImageIcon(this.getClass().getResource("binarybackground.jpg"));//gets background
		bg = binary.getImage();
		
		ImageIcon instructionpic = new ImageIcon(this.getClass().getResource("ins.png"));
		ins = new JLabel();
		ins.setIcon(instructionpic);
		ins.setBounds(10, 0, 600, 600);
		ins.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getSource() == ins){
					GUIFrame.cl.show(GUIFrame.panels, "WelcomePanel");
				}
			}
			
		});
		add(ins);
	}
	
	@Override
	public void paintComponent(Graphics g){
	g.drawImage(bg,0,0,null);
		
	}
	
}
