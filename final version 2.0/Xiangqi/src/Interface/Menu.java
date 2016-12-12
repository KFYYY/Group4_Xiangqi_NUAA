package Interface;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Menu extends JFrame{
	private Container containerPanel;
	private JButton singleGame,onlineGame,showRule,loadGame,back;
	public Menu(){
		super("Menu");
		containerPanel = getContentPane();
		setLayout(new BoxLayout(containerPanel,BoxLayout.Y_AXIS));	
		setSize(400,300);
		
		JLabel title = new JLabel("Xiangqi");
		containerPanel.add(title);
		
		singleGame = createSingleGame();
		containerPanel.add(singleGame);
		
		onlineGame = createOnlineGame();
		containerPanel.add(onlineGame);
		
		showRule = createShowRule();
		containerPanel.add(showRule);
		
		loadGame = createLoadGame();
		containerPanel.add(loadGame);
		
		back = createBack();
		containerPanel.add(back);
	}
	private JButton createBack() {
		// TODO Auto-generated method stub
		JButton button = new JButton("back");
		backActionListener backHandler = new backActionListener();
		button.addActionListener(backHandler);
		return button;
	}
	private class backActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Login login = new Login();
			login.setVisible(true);
			dispose();
		}
	}
	private JButton createLoadGame() {
		// TODO Auto-generated method stub
		JButton button = new JButton("load game");
		return button;
	}
	private JButton createShowRule() {
		// TODO Auto-generated method stub
		JButton button = new JButton("show rule");
		return button;
	}
	private JButton createOnlineGame() {
		// TODO Auto-generated method stub
		JButton button = new JButton("online game");
		return button;
	}
	private JButton createSingleGame() {
		// TODO Auto-generated method stub
		JButton button = new JButton("single game");
		return button;
	}
}
