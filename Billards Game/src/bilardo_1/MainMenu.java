package bilardo_1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;



public class MainMenu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	public Platform plt=new Platform();
	public SuperBall superball=new SuperBall();
	public BufferedImage menubackground;
	//public SuperBall multiplayer_superball=new SuperBall();
	public JButton practise;
	public JButton multiplayer;
	public JButton quit;
	public JButton player1;
	public JButton player2;
	
	
	public boolean is_player_1;
	
	
	public boolean practise_pressed;
	public boolean multiplayer_pressed;
	public boolean quit_pressed;
	
	//public JPanel buttons;

	Timer t=new Timer(10,this);
	public MainMenu() {
		t.start();
		setLayout(null);
		
		assets();
		gui();
		
	   is_player_1=false;
	   practise_pressed=false;
	   multiplayer_pressed=false;
	   quit_pressed=false;
		
	}
	
	public void assets() {
		try {
			menubackground=ImageIO.read(SuperBall.class.getResource("/mainmenubackground.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	public void gui() {
		//Buttons
		practise=new JButton("PLAY PRACTISE");
		multiplayer=new JButton("MULTIPLAYER");
		quit=new JButton("QUIT");
		player1=new JButton("PLAYER 1");
		player2=new JButton("PLAYER 2");
		
		
		//Buttons ActionListeners
		practise.addActionListener(this);
		multiplayer.addActionListener(this);
		quit.addActionListener(this);
		player1.addActionListener(this);
		player2.addActionListener(this);
			
		
		//Buttons Layout
		practise.setBounds(200,200,150,60);
		multiplayer.setBounds(400,200,150,60);
		quit.setBounds(600,200,150,60);
		player1.setBounds(300,200,150,60);
		player2.setBounds(500,200,150,60);
		
		
		//Add buttons to a panel
		add(practise);
		add(multiplayer);
		add(quit);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(practise)) {
			plt.mainmenu.setVisible(false);
			plt.platform.add(superball);
			superball.is_practise=true;
		}
		
		if (e.getSource().equals(multiplayer))
		{
			this.practise.setVisible(false);
			this.multiplayer.setVisible(false);
			this.quit.setVisible(false);
			add(player1);
			add(player2);
			
			
		}
		 
		if (e.getSource().equals(player1)) {
			plt.multiplayer_superball.turn=true;
			plt.mainmenu.setVisible(false);
			plt.platform.add(plt.multiplayer_superball);
			superball.is_practise=false;
			
		}
		
		if (e.getSource().equals(player2)) {
			plt.multiplayer_superball.turn=false;
			plt.mainmenu.setVisible(false);
			plt.platform.add(plt.multiplayer_superball);
			superball.is_practise=false;
			
		}
		
		
		if (e.getSource().equals(quit)) {
			System.exit(0);
		}
		
		
	}
	


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D buttons=(Graphics2D)g;
		
		g.drawImage(menubackground, 0, 0, null);
		Font title=new Font("tahoma",Font.BOLD,50);
		Font button=new Font("tahome",Font.CENTER_BASELINE,30);
		g.setFont(title);
		g.setColor(Color.black);
		g.drawString("Billiards Game", 300, 75);
		
	
		
	}
	
	
}
