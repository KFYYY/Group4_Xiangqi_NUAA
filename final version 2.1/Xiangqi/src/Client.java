import java.io.IOException;

import javax.swing.JFrame;

import Interface.Login;

public class Client {
	public static void main(String[] args) throws IOException{
		Login login =new Login();
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setVisible(true);
	}
}
