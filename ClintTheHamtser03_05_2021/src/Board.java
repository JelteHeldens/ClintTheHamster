import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Board {
	protected enum BoardStatus{			//our board is made out of empty places and cacti
		EMPTY,		
		CACTUS,
	}
	
	private BoardStatus[][] cellen;
	private int BOARD_WIDTH;
	private int BOARD_HEIGHT;
	private BufferedImage cactusImage;
	private BufferedImage bgImage;
	private BufferedImage borderImage;
	private BufferedImage railImage;
	
	public Board() {
		this.BOARD_HEIGHT=900;
		this.BOARD_WIDTH=1000;
		try {
    		cactusImage = ImageIO.read(ResourceLoader.load("cactus.png"));
    		bgImage = ImageIO.read(ResourceLoader.load("red_sand.png"));
    		borderImage = ImageIO.read(ResourceLoader.load("sand.png"));
    		railImage = ImageIO.read(ResourceLoader.load("rail.png"));
		}
		catch(IOException ex) {
    		ex.printStackTrace();
    	}
		//Creates an empty field
    	this.cellen = new BoardStatus[BOARD_WIDTH][BOARD_HEIGHT];
        for (int y = 0; y < BOARD_HEIGHT; ++y) {									
            for (int x = 0; x < BOARD_WIDTH; ++x) {
                cellen[x][y] = BoardStatus.EMPTY;
            }
        }
        
        //Adds 45 randomly generated cacti (objects) on the field between y=4 and y=25
        for (int i = 0; i < 45 ; ++i) {										
	        Random r= new Random();
	        int x = r.nextInt(50);
	        int y = r.nextInt(39)+4;	
	        while (y == 24 || y == 32) {
	        	y = r.nextInt(39)+4;
	        }
			cellen[x][y] = BoardStatus.CACTUS;
//		System.out.println("cactus x: "+x+", y: "+ y );
        }
		
	}
	
	public void draw(Graphics g) {
		//Draw background
    	for(int y = 0; y < BOARD_HEIGHT/20; y++) {							
    		for(int x = 0; x < BOARD_WIDTH/20; x++) {
    			g.drawImage(bgImage, x*20,y*20,null);
    		}
    	}
    	//Draw cacti
        for(int y = 0; y < BOARD_HEIGHT;  y++) {							
            for(int x = 0; x < BOARD_WIDTH; x++) {
                if (this.cellen[x][y] ==  BoardStatus.CACTUS) {
                	g.drawImage(cactusImage, x*20, y*20, null);
                }  
            }
        }    
        //Draw centipede border & rails
        for(int x = 0; x < BOARD_WIDTH/20; x++) {
        	g.drawImage(borderImage, x*20, 60, null);
        	g.drawImage(railImage, x*20, 480, null);
        	g.drawImage(railImage, x*20, 640, null);
        }
	}
	
	public BoardStatus[][] getCellen(){
		return cellen;
	}
}
