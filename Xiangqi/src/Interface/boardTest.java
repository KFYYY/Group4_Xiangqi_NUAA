package Interface;

import java.io.IOException;

import javax.swing.JFrame;

public class boardTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		board myBoard = new board();
		JFrame x = new JFrame();
		x.setSize(395, 455);
		x.add(myBoard);
		x.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		x.setVisible(true);
	}

}
