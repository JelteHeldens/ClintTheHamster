//Previously intended as settings page, turned in to info page later
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.io.IOException;
import java.util.ArrayList;

public class Settings extends JPanel implements Subject, MouseListener{

    private ArrayList<Observer> observers;
    GameStatus gameStatus;
	Image settingsBg;
	Image backMainMenu;
	
	public Settings(){
		observers = new ArrayList<Observer>();
		gameStatus = GameStatus.SETTINGS;
		addMouseListener(this);
		try {
			settingsBg  = ImageIO.read(ResourceLoader.load("SettingsBg.png"));
			backMainMenu  = ImageIO.read(ResourceLoader.load("BackButton.png"));
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public Rectangle playButton = new Rectangle(1000/2 +120, 150,100,50);
	
	public void paint(Graphics g){
		super.paint(g);
		
		Font titel = new Font("arial", Font.BOLD, 50);
		g.setFont(titel);
		g.setColor(Color.BLACK);
		g.drawString("Clint The Hampster", 300, 100);

		g.drawImage(settingsBg,0,0,getFocusCycleRootAncestor());
		g.drawImage(backMainMenu, 25,839,getFocusCycleRootAncestor());
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
		
		if(mx > 25 && mx < 116) {
			if(my > 839 && my < 935) {
				gameStatus = GameStatus.MENU;
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