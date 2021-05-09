import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Bullet{
	protected int x, y;
	private double s;
	private BufferedImage bulletImage;
	private boolean visible;
	protected int scoreFromHits;
	
	public Bullet(Hamster clint) {      //parameters x and y will be the coordinates of the hamster
		this.x=clint.x;
		this.y=clint.y;
		this.s=20;
		scoreFromHits = 0;
		try {
    		bulletImage = ImageIO.read(ResourceLoader.load("bulletimage2.png"));
    	}
    	catch(IOException ex) {
    		ex.printStackTrace();
    	}		
		this.visible = false;			//the bullet will only be seen if you press on the space bar
	}
	
	public void draw(Graphics g) {			
		//Draw bullet
		if (visible==true) {			//if you press the space bar, isVisible will be true (see Main)
			g.drawImage(bulletImage, x, y, null);
		}
	}
	public void updateposition(Hamster clint) {
		this.x=clint.x;
		this.y=clint.y;
	}

	public void update(int height, Board.BoardStatus[][] cellen,ArrayList<Cowboyhead> cowboys,Hamster clint, Tumbleweed tumbleweed1, Tumbleweed tumbleweed2 ) {  //still working on it
//    	this.x=clint.x;		don't put this, because it means that when you shoot and you move to the left/right, the bullet will go down and also move to the left/right
		if (y < height) {
    		y += s;
    	}
    	for(int y = 0; y < 900;  y++) {		//if the bullet hits a cactus, the cactus will disappear 					
            for(int x = 0; x < 1000; x++) {
                if (cellen[x][y] ==  Board.BoardStatus.CACTUS) {
                	if ((this.x>=(x*20)-10 & this.x<=(x*20)+10) && (this.y>(y-1)*20)) {   // a cactus takes space of [(x*20)-10,(x*20)+10]
                		scoreFromHits += 10;
                		Music muzik = new Music("Hit.wav", false);
        				muzik.play();
                		cellen[x][y] = Board.BoardStatus.EMPTY;
                		resetBullet(clint);
                	}
	
                }
            }
    	}
    	if(y>=height) {resetBullet(clint);};
    	
    	for (int i=0; i<cowboys.size();i++) {		//if the bullet hits a cowboyhead, the cowboyhead disappear and on this position, we place a cactus so the "centipede will split"
    		Cowboyhead cowboy = cowboys.get(i);
    		if ((this.x>=(cowboy.x)-5 & this.x<=(cowboy.x)+5) && this.y>cowboy.y) {
    			cowboys.remove(i);
        		resetBullet(clint);
        		//System.out.println("# hoofden: "+cowboys.size());
        		scoreFromHits += 50;
        		Music muzik = new Music("CentipedeHit.wav", false);
				muzik.play();
				if( cowboy.y/20 != 24 && cowboy.y/20 != 32 && cowboy.y/20 != 4) { //prevents cacti spawn on the rails and right below the player on the last line.
					cellen[cowboy.x/20][cowboy.y/20]=Board.BoardStatus.CACTUS;
				}
			}
		}
    	if (((this.x>=(tumbleweed1.x)-5 & this.x<=(tumbleweed1.x)+5)&& this.y>tumbleweed1.y)) {
    		tumbleweed1.x = -300;
    		resetBullet(clint);
    		scoreFromHits += 10;
    	}
    	if (((this.x>=(tumbleweed2.x)-5 & this.x<=(tumbleweed2.x)+5)&& this.y>tumbleweed2.y) ) {
    		tumbleweed2.x = 1500;
    		resetBullet(clint);
    		scoreFromHits += 10;
    	}
    }
    
    public void resetBullet(Hamster clint) {    //still working on it
    	visible = false;
    	this.x=clint.x;							//the bullet isn't visible and goes back to the position of the hamster
    	this.y=clint.y;
    }
    
    public boolean isVisible() {
    	return visible;
    }
    
    public void setVisible(boolean visible) {
    	this.visible = visible;
    }
}
