import javax.swing.JFrame;


public class Main implements Observer {

	GameStatus gameStatus;
	JFrame jFrame;
	Menu menu;
	BoardPanel boardPanel;
	LossScreen lossScreen;
	Settings settings;
	Music muzik;
	Music gameOverMusic;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		gameStatus = GameStatus.MENU;
		muzik = new Music("The HampsterDance Song.wav", true);
		gameOverMusic = new Music("GameOver.wav", false);
		jFrame = new JFrame();
		updateUI();
	}
	
	public void updateUI() {													//Display jFrame depending on the game's current state
		switch(gameStatus) {
			case MENU:
				if (lossScreen != null) {
					jFrame.remove(lossScreen);
					lossScreen = null;
					gameOverMusic.stop();
				}
				if (settings != null) {
					jFrame.remove(settings);
					settings = null;
				}
				if (boardPanel != null) {
					jFrame.remove(boardPanel);
					boardPanel = null;
				}
				muzik.stop();
				muzik.play();
				menu = new Menu(gameStatus);
				menu.addObserver(this);
				jFrame.add(menu);
				break;
			case GAME:
				if (lossScreen != null) {
					jFrame.remove(lossScreen);
					lossScreen = null;
					gameOverMusic.stop();
					muzik.play();
				}
				else {
					jFrame.remove(menu);
					menu = null;
				}
				boardPanel = new BoardPanel(gameStatus);
				boardPanel.addObserver(this);
				jFrame.add(boardPanel);
				break;
			case LOSS:
				muzik.stop();
				gameOverMusic.play();
				jFrame.remove(boardPanel);
				boardPanel = null;
				lossScreen = new LossScreen();
				lossScreen.addObserver(this);
				jFrame.add(lossScreen);
				break;
			case SETTINGS:
				jFrame.remove(menu);
				settings = new Settings();
				settings.addObserver(this);
				jFrame.add(settings);
				break;
			case QUIT:
				System.exit(0);
				break;
			default:
				new Exception("Wrong use of internal GameStatus Enum");
		}
		
		jFrame.setSize(1015, 995);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setTitle("Clint The Hamster");
		jFrame.setLocation(100, 20);
		jFrame.setVisible(true);
		jFrame.setResizable(false);
		
	}
	
	@Override
    public void update() { 														//subscribe to game status changes in jFrames
		switch(gameStatus) {
			case MENU:
				gameStatus = menu.gameStatus;
				break;
			case GAME:
				gameStatus = boardPanel.gameStatus;
				break;
			case LOSS:
				gameStatus = lossScreen.gameStatus;
				break;
			case SETTINGS:
				gameStatus = settings.gameStatus;
				break;
			default:
				new Exception("Could not retrieve GameStatus based on current GameStatus Enum");
		}
        updateUI();
    }
}
