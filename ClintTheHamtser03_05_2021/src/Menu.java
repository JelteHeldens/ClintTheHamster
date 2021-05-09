import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gm.ApiBoard;
import gm.Score;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JPanel implements Subject, MouseListener{

    private ArrayList<Observer> observers;
    ApiBoard apiBoard;
    GameStatus gameStatus;
	Image menuBg;
	Image play;
	Image settings;
	Image quitt;
	Image menuBg2;
	List<Score> highscores;
	
	
	public Menu(GameStatus gameStatus){
		observers = new ArrayList<Observer>();
		apiBoard = new ApiBoard();
		this.gameStatus = gameStatus;
		addMouseListener(this);
		try {
			menuBg  = ImageIO.read(ResourceLoader.load("MenuTijdelijk.png"));
			play  = ImageIO.read(ResourceLoader.load("PlayGraphic.png"));
			settings  = ImageIO.read(ResourceLoader.load("SettingsGraphic.png"));
			quitt  = ImageIO.read(ResourceLoader.load("Quit.png"));
			menuBg2  = ImageIO.read(ResourceLoader.load("MenuTijdelijkTitel.png"));
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		highscores = apiBoard.getResults("Clint The Hamster");
	}
	
	public Rectangle playButton = new Rectangle(1000/2 +120, 150,100,50);
	
	public void paint(Graphics g){
		super.paint(g);
		
		Font titel = new Font("arial", Font.BOLD, 50);
		g.setFont(titel);
		g.setColor(Color.BLACK);
		g.drawString("Clint The Hampster", 300, 100);
		
		//g.drawRect(357, 300, 300, 100);
		//g.drawRect(367, 450, 280, 93);
		//g.drawRect(387, 590, 240, 80);
		
		g.drawImage(menuBg,0,0,getFocusCycleRootAncestor());
		g.drawImage(play, 357,300,getFocusCycleRootAncestor());
		g.drawImage(settings, 367,450,getFocusCycleRootAncestor());
		g.drawImage(quitt, 387,590,getFocusCycleRootAncestor());
		g.drawImage(menuBg2,0,0,getFocusCycleRootAncestor());
		
		Font scorekes = new Font("Serif", Font.BOLD, 20);
		g.setFont(scorekes);
		g.setColor(Color.BLACK);
		for(int i = 0 ; i < highscores.size() && i < 10; i++) {
			g.drawString(highscores.get(i).score + " - " + highscores.get(i).player , 35, i*25+475);
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(mx > 357 && mx < 657) {					
			if(my > 300 && my < 400) {
				gameStatus = GameStatus.GAME;
				notifyObservers();
			}
		}
		if(mx > 387 && mx < 627) {					
			if(my > 590 && my < 680) {
				gameStatus = GameStatus.QUIT;
				notifyObservers();
			}
		}
		if(mx > 367 && mx < 647) {					
			if(my > 450 && my < 543) {
				gameStatus = GameStatus.SETTINGS;
				notifyObservers();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}