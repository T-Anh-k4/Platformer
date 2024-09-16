package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private Game game;

	public GamePanel(Game game) {
		mouseInputs = new MouseInputs(this); //xử lý sự kiện chuột
		this.game = game;
		setPanelSize(); //thiết lập size cho bảng 
		addKeyListener(new KeyboardInputs(this));  //xử lý sự kiện bàn phím
		addMouseListener(mouseInputs);    //đăng ký một bộ xử lý sự kiện chuột vào GamePanel
		addMouseMotionListener(mouseInputs); //Tương tự như addMouseListener(), nhưng sự kiện này chỉ xảy ra khi chuột di chuyển mà không có sự click.
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);        //Dimesion thuộc tính có độ size có sẵn dùng để thiết lập panel
		setPreferredSize(size);                                         //thiết lập kích thước ưu tiên cho một thành phần giao diện
	}

	public void updateGame() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);               
		game.render(g);                        //vẽ nội dung trò chơi lên panel
	}

	public Game getGame() {                   // xử lý các sự kiện trong panel
		return game;
	}

}