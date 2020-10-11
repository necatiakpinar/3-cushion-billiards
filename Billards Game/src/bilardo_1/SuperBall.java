package bilardo_1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;



public class SuperBall extends JPanel implements ActionListener,MouseMotionListener,MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Vector2d velocity=new Vector2d(0,0);
	public Vector2d velocity2=new Vector2d(0,0);
	public Vector2d velocity3=new Vector2d(0,0);
	public Vector2d position=new Vector2d(100,200);
	public Vector2d position2=new Vector2d(200,300);
	public Vector2d position3=new Vector2d(240,200);
	
	private float r=27;
	public double f=0.9911;


	public GameAI game_AI;
	
	private static int mouse_x;
	private static int mouse_y;
	
	public static Vector2d cue_position;
	public static boolean increase_speed;
	public static double  cue_speed=0;
	public static double  cue_rotation;
	public AffineTransform rotation;

	public static BufferedImage image;
	public static BufferedImage green_table;
	public static BufferedImage background;
	
	public boolean can_move;
	public boolean show_cue;
	
	public Ellipse2D.Double cue_ball=new Ellipse2D.Double(position.getX(),position.getY(),getRadius(),getRadius());
	public Ellipse2D.Double yellow_ball=new Ellipse2D.Double(position2.getX(),position2.getY(),getRadius(),getRadius());
	public Ellipse2D.Double blue_ball=new Ellipse2D.Double(position3.getX(),position3.getY(),getRadius(),getRadius());


	public boolean is_practise; 
	
	//Multiplayer Variables
	public boolean turn=false;
	Socket client;
    DataOutputStream dout;
    ServerSocket ss;
    Socket server;
    DataInputStream dis;

    String velocities;
	
    
    
	//Score 
	public static boolean yellow_touched;
	public static boolean blue_touched;

	
	//GUI
	public static JLabel cue_speed_label;
	
	
	Timer t=new Timer(10,this);
	
	public SuperBall() {			
	
		start();

		//game_AI=new GameAI();
		addMouseMotionListener(this);
		addMouseListener(this);
		
		
		increase_speed=false;
		cue_position=new Vector2d(cue_ball.x-200,cue_ball.y+10);
		can_move=false;		
		show_cue=true;
		
		//Score
		yellow_touched=false;
		blue_touched=false;
		
	}
	
	public void start() {
		t.start();
		assets();
		
		
	}
	
	public void assets() {
		try {
			image=ImageIO.read(SuperBall.class.getResource("/cue.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		try {
			green_table=ImageIO.read(SuperBall.class.getResource("/bilardo_table.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			background=ImageIO.read(SuperBall.class.getResource("/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		is_scored();
		movement();
		ball_collision();
		friction();
		//cue_rotation=Math.toDegrees(getRotation());
		repaint();
		
		if (increase_speed) {
			cue_speed=cue_speed+0.15;
			if (cue_speed>30)
			{
				cue_speed=30;
			}
		}
		System.out.println(is_practise);
		
	}
	
	//Score control
	
	public void is_scored() {
		
		
		if (yellow_touched && blue_touched)
		{
			System.out.println("YOU SCORED!!");
		}
		
	}
	
	
	public void friction() {			
		velocity=velocity.multiply(f);
		velocity2=velocity2.multiply(f);
		velocity3=velocity3.multiply(f);
	}
	
	public void movement() {
	
		if (can_move) {
			cue_position=new Vector2d(cue_ball.x-200,cue_ball.y+10);
			
			cue_ball.x+=(velocity.getX());
			
			cue_ball.y+=(velocity.getY());
			
			yellow_ball.x+=(velocity2.getX());
			
			yellow_ball.y+=(velocity2.getY());
			
			blue_ball.x+=(velocity3.getX());
			
			blue_ball.y+=(velocity3.getY());
			
			//köþelerden sekmesi
			if(cue_ball.x<95 || cue_ball.x>860) {
				velocity.setX(-(velocity.getX()));
			}				
			if(cue_ball.y<80|| cue_ball.y>365) {
				velocity.setY(-(velocity.getY()));
			}		
			if(yellow_ball.x<95 || yellow_ball.x>860) {
				velocity2.setX(-(velocity2.getX()));
			}				
			if(yellow_ball.y<80 || yellow_ball.y>365) {
				velocity2.setY(-(velocity2.getY()));
			}	
			if(blue_ball.x<95 || blue_ball.x>860) {
				velocity3.setX(-(velocity3.getX()));
			}				
			if(blue_ball.y<80 || blue_ball.y>365) {
				velocity3.setY(-(velocity3.getY()));

			}
		}
		
			
			
			if(velocity.getLength()<0.01)
			{
//				cue_rotation=Math.toDegrees(getRotation());
				//cue_position=new Vector2d(cue_ball.x-200,cue_ball.y+10);
				can_move=false;
				show_cue=true;

				
				
				//Score 
			}
			
			if (can_move==false)
			{
				blue_touched=false;
				yellow_touched=false;
			}
		
			
	}
	public void ball_collision() {

		double xd12=(cue_ball.getCenterX())-(yellow_ball.getCenterX());
		double yd12=(cue_ball.getCenterY())-(yellow_ball.getCenterY());
		
		double distance12= (xd12 * xd12) + (yd12 * yd12);
		
		double xd13=(cue_ball.getCenterX())-(blue_ball.getCenterX());
		double yd13=(cue_ball.getCenterY())-(blue_ball.getCenterY());
		double distance13= (xd13 * xd13) + (yd13 * yd13);
		
		double xd23=(yellow_ball.getCenterX())-(blue_ball.getCenterX());
		double yd23=(yellow_ball.getCenterY())-(blue_ball.getCenterY());
		double distance23= (xd23 * xd23) + (yd23 * yd23);
		
		if(distance23<r*r) {
			Vector2d normal= new Vector2d((yellow_ball.x-blue_ball.x),(yellow_ball.y-blue_ball.y));
			
			Vector2d mtd=normal.multiply((r-distance23)/distance23);
			yellow_ball.x=yellow_ball.x+(mtd.multiply(1/2).getX());
			yellow_ball.y=yellow_ball.y+(mtd.multiply(1/2).getY());
			blue_ball.x=blue_ball.x-(mtd.multiply(1/2).getX());
			blue_ball.y=blue_ball.y-(mtd.multiply(1/2).getY());
			
			Vector2d un=normal.multiply(1/normal.getLength());
			
			//birim tanjant vektör
			Vector2d ut=new Vector2d(-un.getY(),un.getX());
			//hýzlarý birim vektörlere aktarma // yine typecast sonra deðiþcez
			float v1n=(float) un.dot(velocity2);
			float v1t=(float) ut.dot(velocity2);
			float v2n=(float) un.dot(velocity3);
			float v2t=(float) ut.dot(velocity3);
		
			//normal hýz ile tanjant vektörünü son vektöre aktarma
			Vector2d v1nTag=un.multiply(v2n);
			Vector2d v1tTag=ut.multiply(v1t);		
			Vector2d v2nTag=un.multiply(v1n);
			Vector2d v2tTag=ut.multiply(v2t);
			
			velocity2=v1nTag.add(v1tTag);
			velocity3=v2nTag.add(v2tTag);
		}
		if(distance13<r*r) {
			blue_touched=true;
			Vector2d normal= new Vector2d((cue_ball.x-blue_ball.x),(cue_ball.y-blue_ball.y));
			float dist=normal.getLength();
			
			Vector2d mtd=normal.multiply((r-distance13)/distance13);
			cue_ball.x=cue_ball.x+(mtd.multiply(1/2).getX());
			cue_ball.y=cue_ball.y+(mtd.multiply(1/2).getY());
			blue_ball.x=blue_ball.x-(mtd.multiply(1/2).getX());
			blue_ball.y=blue_ball.y-(mtd.multiply(1/2).getY());
			
			Vector2d un=normal.multiply(1/normal.getLength());
			
			//birim tanjant vektör
			Vector2d ut=new Vector2d(-un.getY(),un.getX());
			//hýzlarý birim vektörlere aktarma // yine typecast sonra deðiþcez
			float v1n=(float) un.dot(velocity);
			float v1t=(float) ut.dot(velocity);
			float v2n=(float) un.dot(velocity3);
			float v2t=(float) ut.dot(velocity3);
		
			//normal hýz ile tanjant vektörünü son vektöre aktarma
			Vector2d v1nTag=un.multiply(v2n);
			Vector2d v1tTag=ut.multiply(v1t);		
			Vector2d v2nTag=un.multiply(v1n);
			Vector2d v2tTag=ut.multiply(v2t);
			
			velocity=v1nTag.add(v1tTag);
			velocity3=v2nTag.add(v2tTag);
		}
		if(distance12<r*r) {
			yellow_touched=true;	
			Vector2d normal= new Vector2d((cue_ball.x-yellow_ball.x),(cue_ball.y-yellow_ball.y));
			float dist=normal.getLength();
			
			Vector2d mtd=normal.multiply((r-distance12)/distance12);
			cue_ball.x=cue_ball.x+(mtd.multiply(1/2).getX());
			cue_ball.y=cue_ball.y+(mtd.multiply(1/2).getY());
			yellow_ball.x=yellow_ball.x-(mtd.multiply(1/2).getX());
			yellow_ball.y=yellow_ball.y-(mtd.multiply(1/2).getY());
			
			Vector2d un=normal.multiply(1/normal.getLength());
			
			//birim tanjant vektör
			Vector2d ut=new Vector2d(-un.getY(),un.getX());
			//hýzlarý birim vektörlere aktarma // yine typecast sonra deðiþcez
			float v1n=(float) un.dot(velocity);
			float v1t=(float) ut.dot(velocity);
			float v2n=(float) un.dot(velocity2);
			float v2t=(float) ut.dot(velocity2);
		
			//normal hýz ile tanjant vektörünü son vektöre aktarma
			Vector2d v1nTag=un.multiply(v2n);
			Vector2d v1tTag=ut.multiply(v1t);		
			Vector2d v2nTag=un.multiply(v1n);
			Vector2d v2tTag=ut.multiply(v2t);
			
			velocity=v1nTag.add(v1tTag);
			velocity2=v2nTag.add(v2tTag);
		}
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D bg=(Graphics2D)g;
		Graphics2D balls=(Graphics2D)g;
		Graphics2D cue=(Graphics2D)g;

		bg.drawImage(background,0,0,null);
		bg.drawImage(green_table,40,35, null);
		balls.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		balls.setPaint(Color.white);
		balls.fill(cue_ball);
		balls.setPaint(Color.yellow);
		balls.fill(yellow_ball);
		balls.setPaint(Color.blue);
		balls.fill(blue_ball);
	
		
		
		
	if (show_cue) {
		rotation=AffineTransform.getRotateInstance(cue_rotation, cue_ball.getX()+15,cue_ball.getY()+15);
		cue.setTransform(rotation);
		cue.drawImage(image,(int) cue_position.getX(),(int)cue_position.getY(), null);	
	}
		
		
	}
	
	
	
	

	public void mouseClicked(MouseEvent e) {
		
	}


	public void mousePressed(MouseEvent e) {
		
		if(show_cue==true)
		{
			increase_speed=true;
		}
		

	}
	
	
	public void Client(String address, int port) 
    { 
     
       
        try{      
            client=new Socket(address,port); 
            dout=new DataOutputStream(client.getOutputStream());  
            velocities=Integer.toString((int)velocity.getX())+","+Integer.toString((int)velocity.getY());
            System.out.println(client.isConnected());  
            System.out.println(velocities);  
            dout.writeUTF(velocities);                
            dout.flush();  
            dout.close();  
            client.close(); 
            }catch(Exception e){System.out.println(e+"client");} 
       
    } 
 
 public void  Server(int port) 
    { 
    
      while(true) {
          if(!turn) {
        try{  
            ss=new ServerSocket(port);  
            server=ss.accept();//establishes connection   
            System.out.println("connection established");
            
            dis=new DataInputStream(server.getInputStream());  
            String  str=(String)dis.readUTF();  
            System.out.println("incoming message= "+str);
            velocity=new Vector2d(Character.getNumericValue(str.charAt(0)),Character.getNumericValue(str.charAt(2)));
            System.out.println("message= "+velocity2);  
            ss.close();  
            }catch(Exception e){System.out.println(e+"server");} 
          }
      }
      
     }

 
	public void mouseReleased(MouseEvent e) {
		
		
		if (show_cue==true) {
			can_move=true;
			show_cue=false;
			velocity=new Vector2d(Math.cos(cue_rotation)*cue_speed,Math.sin(cue_rotation)*cue_speed);
		}
			cue_speed=0;
			increase_speed=false;
			//Client("localhost",2222);
	}


	public void mouseEntered(MouseEvent e) {
	
	}


	public void mouseExited(MouseEvent e) {
	
	}


	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		
		
		if (show_cue==true) {
			mouse_x=e.getX();
			mouse_y=e.getY();
			
			double opposite=e.getY()-cue_ball.getY();
			double adjacent=e.getX()-cue_ball.getX();
			cue_rotation=Math.atan2(opposite, adjacent);
		
		}
		
		
	}

	public double getRotation() {
		
		int absx=(int) Math.abs((this.getMouseX()-cue_ball.getX()));
		int absy=(int)Math.abs((this.getMouseY()-cue_ball.getY()));
		return Math.atan2(absy, absx);
	}
	void setRadius(float r) {
		this.r = r;
	}
	public float getRadius() {
		return r;
	}

	public static int getMouseX() {
		return mouse_x;
	}
	
	public static int getMouseY() {
		return mouse_y;
	}
	


}
