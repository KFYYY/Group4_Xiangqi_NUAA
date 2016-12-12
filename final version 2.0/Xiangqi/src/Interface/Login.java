package Interface;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import data.Image;

public class Login extends JFrame{
	private JPanel accountPanel,passwordPanel,buttonPanel;
	public JTextField account;
	public JPasswordField password;
	private JButton loginButton,registerButton;
	private Container containerPane;
	public Login() {
		super("Login");
		setSize(400, 300);
		
		containerPane = getContentPane();
		setLayout(new GridBagLayout());
		
		accountPanel = CreateAccountPanel();
		containerPane.add(accountPanel,Constraint(0, 0, 0, 0, GridBagConstraints.BOTH));
		
		passwordPanel = CreatePasswordPanel();
		containerPane.add(passwordPanel);
		containerPane.add(passwordPanel,Constraint(0, 1, 0, 0, GridBagConstraints.BOTH));
		
		buttonPanel = CreateButtonPanel();
		containerPane.add(buttonPanel);
		containerPane.add(buttonPanel,Constraint(0, 2, 1.0, 0, GridBagConstraints.CENTER));
	}
	private Object Constraint(int i, int j, double d, double k, int horizontal) {
		// TODO Auto-generated method stub
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = i;
		c.gridy = j;
		c.weightx = d;
		c.weighty = k;
		c.fill = horizontal;
		return c;
	}
	private JPanel CreateButtonPanel() {
		// TODO Auto-generated method stub
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane , BoxLayout.X_AXIS));
		
		loginButton = new JButton("login");
		LoginButtonHandle loginHandler = new LoginButtonHandle();
		loginButton.addActionListener(loginHandler);
		pane.add(loginButton);
		
		pane.add(Box.createHorizontalStrut(10));
		registerButton = new JButton("register");
		pane.add(registerButton);
		
		return pane;
	}
	private class LoginButtonHandle implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String pass = new String(password.getPassword());
			
			//*****************modify needed************
			if ((account.getText().equals("1")) && (pass.equals("2"))){
				Menu menu = new Menu();
				menu.setVisible(true);
				
				dispose();//close current interface
			}
		}
		
	}
	private JPanel CreatePasswordPanel() {
		// TODO Auto-generated method stub
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
		
		JLabel label = new JLabel("password:");
		pane.add(label);
		
		password = new JPasswordField("");
		pane.add(password);
		return pane;
	}
	private JPanel CreateAccountPanel() {
		// TODO Auto-generated method stub
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
		
		JLabel label = new JLabel("account:");
		pane.add(label);
		
		account = new JTextField("");
		pane.add(account);
		return pane;
	}
}
