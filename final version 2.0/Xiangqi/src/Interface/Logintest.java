package Interface;

import java.io.IOException;

import javax.swing.JFrame;

public class Logintest {
	public static void main(String[] args) throws IOException{
		Login login =new Login();
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setVisible(true);
	}
}
