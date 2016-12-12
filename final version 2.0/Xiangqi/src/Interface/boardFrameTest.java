package Interface;

import java.io.IOException;

import javax.swing.JFrame;

public class boardFrameTest {

	final private static int online = 1;
	final private static int single = 0;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		boardFrame myBoardFrame = new boardFrame("admin",single);
		myBoardFrame.setVisible(true);
		myBoardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myBoardFrame.setSize(390, 500);
	}

}
