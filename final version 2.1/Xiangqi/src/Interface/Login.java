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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Engine.UserAccount;
import data.Image;

public class Login extends JFrame{
	private JPanel accountPanel,passwordPanel,buttonPanel;
	public JTextField account;
	public JPasswordField password;
	public JButton loginButton,registerButton;
	private Container containerPane;
	public Login() 
	{
		super("Login");
		setLocation(600,300);
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
		RegisterButtonHandle registerHandler = new RegisterButtonHandle();
		registerButton.addActionListener(registerHandler);
		pane.add(registerButton);
		
		return pane;
	}
	private class LoginButtonHandle implements ActionListener{
	
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String pass = new String(password.getPassword());
			
			try {
				 UserAccount myUserAccount = new UserAccount();
				
				 String textAccount = account.getText();
				 String textPassword = pass;
				 final int Input_Illogical = -2;  
				 final int Invalid_Username = -1;  
				 final int Password_Wrong = 0;  
				 final int Log_In_Successed = 1; 
				 switch (myUserAccount.Login(textAccount, textPassword))
				 {
				 	case Input_Illogical:
						JOptionPane.showMessageDialog(null, "illogical account or password!",
								"ERROR",JOptionPane.ERROR_MESSAGE);
						break;
					case Invalid_Username: 
						JOptionPane.showMessageDialog(null, "Username Invalid!",
								"WARNING",JOptionPane.WARNING_MESSAGE);
						break;
					case Password_Wrong: 
						JOptionPane.showMessageDialog(null, "Wrong Password!",
								"ERROR",JOptionPane.ERROR_MESSAGE);
						break;
					case Log_In_Successed: 
						Menu menu = new Menu(textAccount);
						menu.setVisible(true);
						dispose();//close current interface	
						break;
					default :
						break;
				 }
				
			   } 
			 catch (IOException e1) {
					e1.printStackTrace();
			 		}
		}
	}
	private class RegisterButtonHandle implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String pass = new String(password.getPassword());
			
			try {
				UserAccount myUserAccount = new UserAccount();
				
				String textAccount = account.getText();
				String textPassword = pass;
				
				if (myUserAccount.Register(textAccount, textPassword)==1){
					JOptionPane.showMessageDialog(null, "Register successfully!");
				}else
				if (myUserAccount.Register(textAccount, textPassword)==0){
					JOptionPane.showMessageDialog(null, "Account already exists!");
				}else
				if (myUserAccount.Register(textAccount, textPassword)==-1){
						JOptionPane.showMessageDialog(null, "Account and password can only contain "
								+ "number,character and underline");
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	private JPanel CreatePasswordPanel() {
		// TODO Auto-generated method stub
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
		
		JLabel label = new JLabel("password:");
		pane.add(label);
		
		password = new JPasswordField(5);
		pane.add(password);
		return pane;
	}
	private JPanel CreateAccountPanel() {
		// TODO Auto-generated method stub
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
		
		JLabel label = new JLabel("account:");
		pane.add(label);
		
		account = new JTextField(5);
		pane.add(account);
		return pane;
	}
}
