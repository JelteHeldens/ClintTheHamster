import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gm.ApiBoard;

import java.io.IOException;
import java.util.ArrayList;

public class LossScreen extends JPanel implements Subject, MouseListener{

	JTextField textField;
	JButton jButton;
	ApiBoard apiBoard;
    private ArrayList<Observer> observers;
    GameStatus gameStatus;
	Image lossBg;
	Image respawn;
	Image mainMenu;
	protected int scoreResult;
	boolean uploaded;
	
	public LossScreen(){
		apiBoard = new ApiBoard();
		observers = new ArrayList<Observer>();
		gameStatus = GameStatus.LOSS;
		addMouseListener(this);
		scoreResult = BoardPanel.highScore;
		uploaded = false;
		try {
			lossBg  = ImageIO.read(ResourceLoader.load("LossScreen.png"));
			respawn  = ImageIO.read(ResourceLoader.load("Respawn.png"));
			mainMenu  = ImageIO.read(ResourceLoader.load("TitleScreen.png"));
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		textField = new JTextField(20);
		jButton = new JButton("Upload Highscore");
		jButton.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            if(textField.getText().length() != 0 && !uploaded) {
	            	apiBoard.addResult(scoreResult, textField.getText(), "Clint The Hamster");
	            	uploaded = true;																		//limits highscore upload to 1
	            }
	         }
	      });
		
		textField.setBounds(67, 100, 50, 24);
		this.add(textField);
		this.add(jButton);
		
		
	}
	
	public Rectangle playButton = new Rectangle(1000/2 +120, 150,100,50);
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Font titel = new Font("Serif", Font.BOLD, 50);
		g.setFont(titel);
		g.setColor(Color.GRAY);		
		//851 x85
		
		g.drawImage(lossBg,0,0,getFocusCycleRootAncestor());
		
		g.drawString("Score = " + String.valueOf(scoreResult), 365, 248);
		g.setColor(Color.WHITE);
		g.drawString("Score = " + String.valueOf(scoreResult), 362, 245);
		
		g.drawImage(respawn, 67,300,getFocusCycleRootAncestor());
		g.drawImage(mainMenu, 67,400,getFocusCycleRootAncestor());
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
		
		if(mx > 67 && mx < 918) {
			if(my > 300 && my < 385) {
				gameStatus = GameStatus.GAME;
				notifyObservers();
			}
			else if(my > 400 && my < 485) {
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