package bilardo_1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameAI extends JPanel implements ActionListener {

	public SuperBall balls;
	
	
	Timer t=new Timer(10,this);

	public GameAI(){
		start();
	
	}
	
	public void start() {
		t.start();
	}


	public void actionPerformed(ActionEvent e) {
		cue_ball_hits();
		
		
	}

	
	//To gain score cue ball must hit the other two balls atleast one time during one shot.
	public void cue_ball_hits() {
		System.out.println(balls.cue_ball.getX());
		
	}
	
	
}
