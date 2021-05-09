import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hamster{
	int x,y;
	double s;
	boolean isVisibleshooting;
	BufferedImage playerImage;
	BufferedImage playershootingImage;
	
	public Hamster() { 						
		x = 500;                            
		y = 25;
		s = 10;
		isVisibleshooting = false;
		try {
    		playerImage = ImageIO.read(ResourceLoader.load("clint.png"));
    		playershootingImage = ImageIO.read(ResourceLoader.load("Hamstershooting.png"));
    	}
    	catch(IOException ex) {
    		ex.printStackTrace();
    	}
	}
	
	public void draw(Graphics g) {			
    	g.drawImage(playerImage, x-10, y-10, null);    				 //adjust hamster image to screen
    	if (isVisibleshooting == true) {
    		g.drawImage(playershootingImage, x-10, y-10, null);
    		}
    	}
	
	public void updateright(int width,Bullet bullet) {				//retain hamster within screen
		if (this.x <width) {				
			this.x += this.s ;										// the hamster  moves right 
			bullet.x +=this.s;
		}
	}
	public void updateleft(int width,Bullet bullet) {		
		if (this.x >0) {
			this.x -= this.s ;										// the hamster  moves left 		
			bullet.x -=this.s;
		}
											
	}
	
}
