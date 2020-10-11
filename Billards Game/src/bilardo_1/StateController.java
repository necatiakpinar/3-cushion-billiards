package bilardo_1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class StateController implements ActionListener{

	public  SuperBall superball;
	public  MainMenu mainmenu;	
	public  Platform platform;

	
	public enum STATE{
		MENU,
		PLAY,
		MULTIPLAYER;
		
	};
	
	public static STATE current_state;
	
	public boolean menu;
	public boolean play;
	public boolean multiplayer;
	
	
	
	Timer t=new Timer(10,this);	
	
	
	public StateController(){
		t.start();
		

		current_state=STATE.MENU;
	

		menu=false;
		play=true;
		multiplayer=false;
		
	}
	

	public void actionPerformed(ActionEvent e) {
		

		System.out.println(mainmenu.practise_pressed);
		
	}

	
	
	
	
}
