import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoardPanel extends JPanel implements Observer, Subject, KeyListener{
    private ArrayList<Observer> observers;
	protected GameStatus gameStatus;
	private Hamster clint;
	private ArrayList<Cowboyhead> cowboys;
	private Board board;
	private Bullet bullet;
	private Tumbleweed tumbleweed1;
	private Tumbleweed tumbleweed2;
	private int health;
	private BufferedImage onderkant;
	private BufferedImage hartje;
	private Timer timer;
	private TimerTask timerTask;
	private Double timeScore;
	protected static int highScore;
	
	public BoardPanel(GameStatus gameStatus) {
		timeScore = 0d;
		try {
    		onderkant = ImageIO.read(ResourceLoader.load("onderkant.png"));
    		hartje = ImageIO.read(ResourceLoader.load("hartje.png"));
		}
		catch(IOException ex) {
    		ex.printStackTrace();
    	}
		
		observers = new ArrayList<Observer>();
		this.gameStatus = gameStatus;
		clint = new Hamster();
		cowboys = new ArrayList<Cowboyhead>();
		generateCowboys(20);
		health = 4;

		timer = new Timer();                      						//The Timer is an object  that calls the UpdateTimerTask (here) every 20 milliseconds
		bullet = new Bullet(clint);
		tumbleweed1 = new Tumbleweed(980,480);
		tumbleweed2 = new Tumbleweed(-500,640);
		this.addKeyListener(this);										//this ensures that the KeyListener from "this" the pressing keys catches and sends to the Panel
		
		timerTask = new TimerTask(){     								//TimerTask is going to update our objects            
			@Override
			public void run() {
				timeScore += .05;
				for (int i = 0; i < cowboys.size(); i++) {
		    		Cowboyhead cowboy = cowboys.get(i);
					cowboy.update(970, 80, board.getCellen(),tumbleweed1,tumbleweed2);				//for every cowboyhead, TimerTask is going to update them so when their position is x=970, they'll go up and change direction
				}
				if (bullet.isVisible()) {
					bullet.update(900, board.getCellen(), cowboys,clint,tumbleweed1,tumbleweed2);	//if you press on the space key, the bullet will go down until it hits the end/a cactus/ a cowboyhead 
				}
				tumbleweed1.updateleft(970,cowboys,bullet);
				tumbleweed2.updateright(1000,cowboys,bullet);
			
				repaint();
			
			}
		
		};
		timer.schedule(timerTask, 0l, 20l);

	}
	
	private void generateCowboys(int amount) {
		for (int i = 0; i < amount; ++i) {								//we are going to make 20 cowboys 
			Cowboyhead c = new Cowboyhead(i*20);						//If we don't put '0+i*20', we'll only see one cowboyhead(= 20 cowboyheads on the same position), 		
			c.addObserver(this);
			cowboys.add(c);
		}																//so to put each cowboyhead next to eachother we'll have to put 'i*20'
		board= new Board();
	}
	
	@Override
    public void update() {
		cowboys.clear();
		generateCowboys(20);
		Music muzik = new Music("CentipedeAttack.wav", false);
		muzik.play();
		health--;
		if(health < 1) {
			gameStatus = GameStatus.LOSS;
			//Clean-up object
			timerTask.cancel();
			timerTask = null;
			timer.cancel();
			timer.purge();
			notifyObservers();
		}
    }
	
	@Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        //System.out.println("Registered observer: " + observer.getClass().getSimpleName());
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
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);						//we implemented a draw method for each object 
		if(cowboys.size() < 1) {
			generateCowboys(20);
		}
		board.draw(g);
		clint.draw(g);
		for (int i=0; i<cowboys.size();i++) {
    		Cowboyhead cowboy = cowboys.get(i);
    		cowboy.draw(g);
    	}
    	if (bullet.isVisible()) {					//only if you have pressed on the space bar, you'll see the bullet 
    		bullet.draw(g);
    	}
    	tumbleweed1.draw(g);
    	tumbleweed2.draw(g);
    	g.drawImage(onderkant, 0, 900,getFocusCycleRootAncestor());
    	for(int i = 0; i < health; i++) {
    		g.drawImage(hartje, 935 - (i * 39), 913,getFocusCycleRootAncestor());
    	}
    	Font font = new Font("Serif", Font.PLAIN, 36);
        g.setFont(font);
        int temp = bullet.scoreFromHits;
        temp += timeScore;
        highScore = temp;
        g.drawString("Score: " + temp, 90, 940);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		this.requestFocusInWindow();
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 39) {								//right key
			clint.updateright(970,bullet);

		}
		if (e.getKeyCode() == 37) {								//left key
			clint.updateleft(1000,bullet);

		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {				// if you press on the space bar, the bullet will be shown and it will go down 
			if (bullet.isVisible() ==false){
				Music muzik = new Music("ShootSound.wav", false);
				muzik.play();
				clint.isVisibleshooting=true;
			}
			bullet.setVisible(true);								//until it hits the border/a cactus/a cowboyhead 
			bullet.update(900, board.getCellen(), cowboys, clint,tumbleweed1,tumbleweed2);		//--> It means that for for each action (pressing on spare bar), you'll be able to shoot only 1 bullet
	
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)				//escape key
	    {
			gameStatus = GameStatus.MENU;
			//Clean-up object
			timerTask.cancel();
			timerTask = null;
			timer.cancel();
			timer.purge();
			notifyObservers();
	    }
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			clint.isVisibleshooting=false;
		}
	}
}
