import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Tumbleweed {
	int x, y;
	double s;
	private BufferedImage tumbleweedImage;
	private boolean moveRight;       						//these booleans will give us information about the direction of the cowboyheads                  
	private boolean moveLeft;
	boolean IsVisible;
	
	public Tumbleweed(int x,int y) {
		this.x=x;	//the  tumbleweed on the first rail will start moving from the left side and the other on the second rail from the right side 
		this.y=y;  //300 or 640 (positions of rails)
		this.s=10;
		this.moveRight=false;
		this.moveLeft=false;
		this.IsVisible=true;
		try {
			tumbleweedImage  = ImageIO.read(ResourceLoader.load("tumbleweed4.png"));
    	}
    	catch(IOException ex) {
    		ex.printStackTrace();
    	}	
	}
	public void draw(Graphics g) {			
		//Draw tumbleweed
		if (IsVisible == true) {
			g.drawImage(tumbleweedImage, x, y, null);
		}
		
		
//		g.drawImage(tumbleweedImage, x, y, null);
		}
	public void updateright(int width,ArrayList<Cowboyhead>cowboys,Bullet bullet) {
		if (moveRight==true) {
			x += s; //right
			}	
		if (x<0) {
			moveRight=true;		//right
		}
		if (x>width){
			Random r = new Random();
			int a = r.nextInt(1000); //we generate a random number so that when the tumbleweed reaches the width, it turns back to a random xposition under the leftborder of the screen
			x=-a;				//right
		}

	}
	
	public void updateleft(int width,ArrayList<Cowboyhead>cowboys,Bullet bullet) {
		if (moveLeft==true) {         
			x -= s; //left
			}
		if(x>width) {
			moveLeft = true;
		}
		if (x<0) {
			Random r = new Random();//we generate a random number so that when the tumbleweed reaches the left border of the screen, it turns back to a random xposition behind the width of the screen
			int a = r.nextInt(1000);
			x=width+a;
		}
	}
	
}
