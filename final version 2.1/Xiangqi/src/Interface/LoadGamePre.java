package Interface;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Engine.UserAccount;

public class LoadGamePre extends JFrame{
	private String thisAccount;
	private JTextField myTextField;
	private JPanel numberPanel;
	private JButton button;
	private JButton button2;
	private int maxNumber;
	LoadGamePre(String account) throws IOException{
		super(account);
		thisAccount = account;
		
		setLayout(new GridBagLayout());
		setSize(395,455);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

		UserAccount userAccount = new UserAccount();
		maxNumber = userAccount.getManualNumber(account);
		
		numberPanel = CreateNumberPanel(maxNumber);
		add(numberPanel,Constraint(0,0,0,0, GridBagConstraints.BOTH));
		
		button = new JButton("sumbit");
		add(button,Constraint(0, 2, 1.0, 0, GridBagConstraints.CENTER));
		
		button2 = new JButton("return menu");
		add(button2,Constraint(1, 2, 2.0, 0, GridBagConstraints.CENTER));
		
		ButtonListener buttonListener = new ButtonListener();
		button.addActionListener(buttonListener);
		button2.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0)
					{
						Menu menu = new Menu(account);
						menu.setVisible(true);
						dispose();
					}
				}
		);
	}
	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String inputNumber = myTextField.getText();
			int number = Integer.parseInt(inputNumber);
			if (number < 0 || number >maxNumber){
				JOptionPane.showMessageDialog(null,"out of the bound");
				return ;
			}
			LoadGame loadGame;
			try {
				loadGame = new LoadGame(thisAccount,inputNumber);
				loadGame.setVisible(true);
				dispose();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
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
	private Object Constraint(int Gridx,int Gridy){
		GridBagConstraints my = new GridBagConstraints();
		my.gridx = Gridx;
		my.gridy = Gridy;
		return my;
	}
	private JPanel CreateNumberPanel(int maxNumber) {
		// TODO Auto-generated method stub
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane,BoxLayout.X_AXIS));
		
		JLabel label = new JLabel("Please input record's ID("+maxNumber+"):");
		pane.add(label);
		
		myTextField = new JTextField("");
		pane.add(myTextField);
		return pane;
	}
}