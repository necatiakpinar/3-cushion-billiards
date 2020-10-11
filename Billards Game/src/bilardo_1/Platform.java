package bilardo_1;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Platform  extends JFrame {
	static final long serialVersionUID = 1L;

	public static int width=1000;
	public static int height=500;

	
	static Timer t=new Timer(10, null);
	
 
	public static SuperBall superball;
	public static SuperBall multiplayer_superball;
	public static MainMenu mainmenu;	
	public static StateController statecontroller;

	
	public static JPanel panel=new JPanel();
	
	public  JButton practise;
	public  JButton multiplayer;
	public  JButton quit;

	public static Platform platform;
	
	public Platform() {
		t.start();
//	
		
	}
	
	public static void main(String[] args) {
		platform=new Platform();
		superball= new SuperBall();
		multiplayer_superball=new SuperBall();
		mainmenu=new MainMenu();	
		
		platform.setVisible(true);
		platform.setSize(new Dimension(width,height));
		platform.setResizable(false);
		platform.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		platform.add(mainmenu);
		
		
		
	}



	
}
