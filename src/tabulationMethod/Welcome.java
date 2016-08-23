/**
 * The welcome panel of the GUIFrame
 */
package tabulationMethod;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Welcome extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private Image bg;
	private JLabel w,td,q,s,v,cr, progs;
	private ImageIcon ss;
	private ImageIcon ssh;
	private ImageIcon vi;
	private ImageIcon vih;
	
	/**
	 * Constructor for the Welcome Panel
	 */
	public Welcome(){
	setLayout(null); //absolute positioning
	setVisible(true);	
	ImageIcon binary = new ImageIcon(this.getClass().getResource("binarybackground.jpg")); //background image
	bg = binary.getImage();
	
	ImageIcon welcome = new ImageIcon(this.getClass().getResource("welcome.png")); //Welcome text
	w = new JLabel();
	w.setBounds(45,20,500,150);
	w.setIcon(welcome);
	add(w);
	
	ImageIcon tothe = new ImageIcon(this.getClass().getResource("tothe.png")); //to the text
	td = new JLabel();
	td.setIcon(tothe);
	td.setBounds(205,145,150,100);
	add(td);
	
	ImageIcon qms = new ImageIcon(this.getClass().getResource("qm.png"));//QuineMccluskey text
	q = new JLabel();
	q.setIcon(qms);
	q.setBounds(145, 210, 300, 100);
	add(q);
	
	
	ss = new ImageIcon(this.getClass().getResource("s.png")); //start button
	s = new JLabel();
	s.setIcon(ss);
	s.setBounds(90,290,200,150);
	s.addMouseListener(this);
	add(s);
	
	ssh = new ImageIcon(this.getClass().getResource("sh.png")); //start hover
	
	vi = new ImageIcon(this.getClass().getResource("vi.png")); //view instruction button
	v = new JLabel();
	v.setIcon(vi);
	v.setBounds(305, 290, 200, 150);
	v.addMouseListener(this);
	add(v);
	
	vih = new ImageIcon(this.getClass().getResource("vih.png")); //view instruction hover
	//copy rights
	cr = new JLabel();
	cr.setText("Copyright 2014 CS130 University of the Philippines Manila");
	cr.setForeground(Color.WHITE);
	cr.setBounds(10,540,500,20);
	
	progs = new JLabel();
	progs.setText("Developed by Anter Aaron Custodio & Carl Zachery Viernes");
	progs.setForeground(Color.WHITE);
	progs.setBounds(10,550,500,20);
	
	add(cr);
	add(progs);
	}
	
	@Override
	public void paintComponent(Graphics g){
	g.drawImage(bg,0,0,null);
		
	}

	/**
	 * Action listener for the welcome page.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource() == s){
			GUIFrame.cl.show(GUIFrame.panels, "SimulationPanel");
		}
		else if(arg0.getSource() == v){
			GUIFrame.cl.show(GUIFrame.panels, "InstructionsPanel");
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == s){
			s.setIcon(ssh);
		}
		else if(arg0.getSource() == v){
			v.setIcon(vih);
		}
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == s){
			s.setIcon(ss);
		}
		else if(arg0.getSource() == v){
			v.setIcon(vi);
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
