import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Cowboyhead implements Subject {
	private ArrayList<Observer> observers;
	protected int x, y;
	private double s;
	private int width,height;
	private boolean moveRight;
	private boolean moveLeft;
	private BufferedImage centipedeImage;
	
	public Cowboyhead(int x) {						
		observers = new ArrayList<Observer>();
		this.x = x;
		this.y = 900;
		this.s= 10;
		this.width=20;
		this.height=20;
		this.moveRight=true;				//centipede moves to right by default
		this.moveLeft=false;
		try {
    		centipedeImage = ImageIO.read(ResourceLoader.load("cowboyhead.png"));
    	}
    	catch(IOException ex) {
    		ex.printStackTrace();
    	}
	}
	
	public void draw(Graphics g) {				
    	g.drawImage(centipedeImage, x, y, null);
	}
	
	public void update(int width, int height,Board.BoardStatus[][] cellen,Tumbleweed tumbleweed1,Tumbleweed tumbleweed2) {	//those parameters are needed to limit the cowboyhead's movement
		if (moveLeft==true) {         
			x -= s; //left	
		}
		
		if (moveRight==true) {
			x += s; //right	
    	}
		  
		if (x >  width) {        //if the cowboyhead hits the right border of the screen, he's going up and changes  direction
			moveLeft=true;
			moveRight=false;  
			y -=this.height;
		}
		
		if (x < 0) { 			//if the cowboyhead hits the left border of the screen, he's going up and changes  direction
			moveRight=true;
			moveLeft=false;   
			y -=this.height;
		}
		
		if  (y < height ) {    //if the cowboyhead hits the the centipide borderwall on the screen or if there's no centipede left, then reset : he's back from the bottom
			notifyObservers();
		}
		
		for(int y = 0; y < 900;  y++) {							
	        for(int x = 0; x < 1000; x++) {
	            if (cellen[x][y] ==  Board.BoardStatus.CACTUS) { //if a cowboyhead hits a cactus, he's going up and changes  direction
	            	if (moveRight==true) {
	            		if (this.x>=((x-1)*20)-10 & this.x<=((x-1)*20)+10 && this.y==y*20) {
	            			moveLeft=true;
	    					moveRight=false;  
	    					this.y -=this.height;
	            		}
	            	}
	            	if (moveLeft==true) {
	            		if (this.x>=((x+1)*20)-10 & this.x<=((x+1)*20)+10 && this.y==y*20){
	            			moveRight=true;
	    					moveLeft=false; 
	    					this.y -=this.height;
	            		}
	            	}
	            }
	        }
		}
		if (moveRight==true) {								//if a cowboyhead hits a tumbleweed:
			if ((this.x>=tumbleweed1.x-20 & this.x<=tumbleweed1.x)&&this.y ==tumbleweed1.y) { //i have to put a range in the xcoordinates becuase the cowboyhead moves at a different speed than the tumbleweed
				if (tumbleweed2.x>0) { //if the second tumbleweed is on our screen, else nothing will happen
				moveRight=false;
				moveLeft=true;
				this.x=tumbleweed2.x-20;
    			this.y=tumbleweed2.y;
				
				}
				else {
					this.x=970;
					this.y=640;
				}
			}
			if ((this.x>=tumbleweed2.x-20 & this.x<=tumbleweed2.x)&&this.y ==tumbleweed2.y) {
				if (tumbleweed1.x<970) { //if the first tumbleweed is on our screen,the cowboyhead will teleport to the other tumbleweed; else the cowboyhead will teleport on the other traintrack
				this.x=tumbleweed1.x+20;
    			this.y=tumbleweed1.y;
				}
				else {
					this.x=0;
					this.y=480;
				}
		}
		}
		if (moveLeft==true) {
			if ((this.x>=tumbleweed1.x & this.x<=tumbleweed1.x+20)&&this.y ==tumbleweed1.y) {
				if (tumbleweed2.x>0) { ///if the second tumbleweed is on our screen,the cowboyhead will teleport to the other tumbleweed; else the cowboyhead will teleport on the other traintrack
					this.x=tumbleweed2.x-20;
					this.y=tumbleweed2.y;	
				}
				else {
					this.x=970;
					this.y=640;
				}
    		}
			if ((this.x>=tumbleweed2.x & this.x<=tumbleweed2.x+20)&&this.y ==tumbleweed2.y) {
				if (tumbleweed1.x<970) { 
				moveLeft=false;
				moveRight=true;
				this.x=tumbleweed1.x+20;
    			this.y=tumbleweed1.y;
    			
				}
				else {
					this.x=970;
					this.y=480;
				}
			}
    		}	
	}
	
	@Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer currObserver:
             observers) {
            currObserver.update();
        }
    }
}
