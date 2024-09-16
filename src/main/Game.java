package main;

import java.awt.Graphics;

import audio.AudioPlayer;
import gamestates.Credits;
import gamestates.GameOptions;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import ui.AudioOptions;


public class Game implements Runnable {

	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120; // xử lý tốc độ hiện thị các khung hình trong 1 khoảng thời gian
	private final int UPS_SET = 200; // xử lý logic game trong 1 giây

	private Playing playing;
	private Menu menu;	
	private Credits credits;
	private GameOptions gameOptions;
	private AudioOptions audioOptions;
	private AudioPlayer audioPlayer;

	public final static int TILES_DEFAULT_SIZE = 32;      // kích thước mặc định của mỗi ô (tile) trong trò chơi, được đo bằng đơn vị pixel.
	public final static float SCALE = 1f;                 /* sử dụng để tính toán kích thước của các ô (tiles) hoặc các phần tử khác của trò chơi,
	                                                      đảm bảo rằng chúng sẽ hiển thị đúng kích thước trên màn hình, không phụ thuộc vào độ phân giải của màn hình. */
	public final static int TILES_IN_WIDTH = 26;          // chiều rộng ô tile
	public final static int TILES_IN_HEIGHT = 14;         //chiều cao
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);  // hằng số được sử dụng để đại diện cho kích thước cuối cùng của mỗi ô (tiles) trong trò chơi
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;         //kích thước tổng cộng của các ô trên chiều rộng.
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;       //kích thước tổng cộng của các ô trên chiều cao.

	private final boolean SHOW_FPS_UPS = true;   // hiện thị thông số FPS và UPS

	public Game() {
		System.out.println("size: " + GAME_WIDTH + " : " + GAME_HEIGHT);
		initClasses();
		gamePanel = new GamePanel(this);
		new GameWindow(gamePanel);              //cửa sổ trò chơi sẽ chứa bảng vẽ trò chơi và hiển thị nó trên màn hình.
		gamePanel.requestFocusInWindow();       // nhận các focus từ bàn phím
		startGameLoop();                        // Phương thức chứa một vòng lặp vô hạn (while (true)) để duy trì trạng thái của trò chơi.
	}

	private void initClasses() {
		audioOptions = new AudioOptions(this);
		audioPlayer = new AudioPlayer();
		menu = new Menu(this);
		playing = new Playing(this);
		credits = new Credits(this);
		gameOptions = new GameOptions(this);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		switch (Gamestate.state) {
		case MENU -> menu.update();
		case PLAYING -> playing.update();
		case OPTIONS -> gameOptions.update();
		case CREDITS -> credits.update();
		case QUIT -> System.exit(0);
		}
	}

	@SuppressWarnings("incomplete-switch")
	public void render(Graphics g) {
		switch (Gamestate.state) {
		case MENU -> menu.draw(g);
		case PLAYING -> playing.draw(g);
		case OPTIONS -> gameOptions.draw(g);
		case CREDITS -> credits.draw(g);
		}
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {

			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {

				update();
				updates++;
				deltaU--;

			}

			if (deltaF >= 1) {

				gamePanel.repaint();
				frames++;
				deltaF--;

			}

			if (SHOW_FPS_UPS)
				if (System.currentTimeMillis() - lastCheck >= 1000) {

					lastCheck = System.currentTimeMillis();
					System.out.println("FPS: " + frames + " | UPS: " + updates);
					frames = 0;
					updates = 0;

				}

		}
	}

	public void windowFocusLost() {
		if (Gamestate.state == Gamestate.PLAYING)
			playing.getPlayer().resetDirBooleans();
	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}

	public Credits getCredits() {
		return credits;
	}

	public GameOptions getGameOptions() {
		return gameOptions;
	}

	public AudioOptions getAudioOptions() {
		return audioOptions;
	}

	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
}