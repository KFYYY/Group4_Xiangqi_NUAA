package Interface;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.applet.*;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import data.Image;

public class Menu extends JFrame{
	private String myHost = "127.0.0.1";
	private JButton singleGame,onlineGame,showRule,loadGame,back;
	
	private String thisAccount;
	public int online = 1;
	private final static int single = 0;
	
	public Menu(String account){
		super(account);
		thisAccount = account;
		
		setLayout(null);	
		setSize(400,300);
		
		try {
			new Image();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(Image.menuPic);
		JLabel title = new JLabel(icon);  add(title);
		title.setBounds(110, 10, 160, 80);
		
		singleGame = createSingleGame(); singleGame.setBounds(110,100,160,30); add(singleGame);
		onlineGame = createOnlineGame(); onlineGame.setBounds(110,130,160,30); add(onlineGame);
		showRule   = createShowRule();   showRule.setBounds(110,160,160,30);   add(showRule);
		loadGame   = createLoadGame();   loadGame.setBounds(110,190,160,30);   add(loadGame);
		back       = createBack();       back.setBounds(110,220,160,30);       add(back);
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
	private class ShowRuleListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				URI uri = new URI("https://en.wikipedia.org/wiki/Xiangqi");
				
				Desktop dtp = Desktop.getDesktop();
				if (Desktop.isDesktopSupported()&&dtp.isSupported(Desktop.Action.BROWSE)){
					dtp.browse(uri);
				}
			} catch (URISyntaxException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	private class LoadGameListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				LoadGamePre loadGamePre = new LoadGamePre(thisAccount);
				loadGamePre.setVisible(true);
				dispose();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	private class OnlineGameListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				try{
					OnlineGame client = new OnlineGame(myHost,thisAccount,online );//
					client.setVisible(true);
				}finally{
					dispose();
				}
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	private class SingleGameListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dispose();
			try {
				new boardFrame(thisAccount,single);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}
	private JButton createBack() {
		// TODO Auto-generated method stub
		JButton button = new JButton("back");
		backActionListener backHandler = new backActionListener();
		button.addActionListener(backHandler);
		return button;
	}
	private JButton createLoadGame() {
		// TODO Auto-generated method stub
		JButton button = new JButton("load game");
		LoadGameListener loadGameListener = new LoadGameListener();
		button.addActionListener(loadGameListener);
		
		button.setToolTipText("need online");
		return button;
	}
	private JButton createShowRule() {
		// TODO Auto-generated method stub
		JButton button = new JButton("show rule");
		ShowRuleListener showRuleListener = new ShowRuleListener();
		button.addActionListener(showRuleListener);		
		return button;
	}
	private JButton createOnlineGame() {
		// TODO Auto-generated method stub
		JButton button = new JButton("online game");
		OnlineGameListener onlineGameListener = new OnlineGameListener();
		button.addActionListener(onlineGameListener);
		return button;
	}
	private JButton createSingleGame() {
		// TODO Auto-generated method stub
		JButton button = new JButton("single game");
		SingleGameListener singleGameListener = new SingleGameListener();
		button.addActionListener(singleGameListener);
		return button;
	}
}
