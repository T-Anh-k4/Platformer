package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow {
	private JFrame jframe;

	public GameWindow(GamePanel gamePanel) {

		jframe = new JFrame();

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //tạo nút exit
		jframe.add(gamePanel); //Bảng trò chơi được thêm vào cửa sổ
		
		jframe.setResizable(false); // không thay đổi kích thước cửa sổ
		jframe.pack(); //để cửa sổ được điều chỉnh tự động để phù hợp với kích thước của bảng trò chơi.
		jframe.setLocationRelativeTo(null); // căn giữa
		jframe.setVisible(true); // hiện thị cửa sổ màn hình
		jframe.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().windowFocusLost();
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

}
