package Interface;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Engine.UserAccount;

public class LoadGame extends JFrame{
	private String thisAccount;
	private board myBoard ;
	private JButton backtoMenu,pre,next;
	private int[][] myMove ;
	private int moveAllNumber ;
	private int online = 1;
	private int load = 2;
	LoadGame(String account,String number) throws IOException {
		super(account);
		thisAccount = account;
		myBoard = new board(account,load);
		moveAllNumber = UserAccount.getManualMoveNumber(account, number);
		
		myMove = new int[moveAllNumber][4];
		myMove = UserAccount.getManualMove(account, number);
		
		//System.out.println(moveAllNumber);
		//for (int i = 0 ;i < moveAllNumber;i++)
		//	System.out.println(myMove[i][0]+" "+myMove[i][1]+" "+myMove[i][2]+" "+myMove[i][3]);
		// TODO Auto-generated constructor stub
		LoadGameKeyListener loadGameKeyListener = new LoadGameKeyListener();
		addKeyListener(loadGameKeyListener);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		setLayout(null);
		setSize(395, 475);
		
		myBoard.setBounds(0, 0, 380, 417);
		add(myBoard);
		
		backtoMenu = new JButton("Menu");
		backtoMenu.setBounds( 0,myBoard.getHeight()+1,100,20);
		backtoMenu.setVisible(true);
		add(backtoMenu);
		BacktoMenuButtonHandle backtomenuHandle = new BacktoMenuButtonHandle();
		backtoMenu.addActionListener(backtomenuHandle);
		
		pre = new JButton("<<");
		pre.setBounds(110,myBoard.getHeight()+1,100,20);
		pre.setVisible(true);
		add(pre);
		pre.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					myBoard.LastBoard();
				}
			}
		);
		
		next = new JButton(">>");
		next.setBounds(220,myBoard.getHeight()+1,100,20);
		next.setVisible(true);
		add(next);
		next.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if (myBoard.moveNumber < moveAllNumber){
						int moveNowNumber = myBoard.moveNumber;
						myBoard.AllMove(myMove[moveNowNumber][0], myMove[moveNowNumber][1], myMove[moveNowNumber][2], myMove[moveNowNumber][3]);
					}else 
					if (myBoard.moveNumber == moveAllNumber){
						JOptionPane.showMessageDialog(null, "the end");
					}
				}
			}
		);
	}
	private class LoadGameKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_LEFT){
				myBoard.LastBoard();
			}else
			if (e.getKeyCode() == KeyEvent.VK_RIGHT){
				if (myBoard.moveNumber < moveAllNumber){
					int moveNowNumber = myBoard.moveNumber;
					myBoard.AllMove(myMove[moveNowNumber][0], myMove[moveNowNumber][1], myMove[moveNowNumber][2], myMove[moveNowNumber][3]);
				}else 
				if (myBoard.moveNumber == moveAllNumber){
					JOptionPane.showMessageDialog(null, "the end");
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
		
	}
	private class BacktoMenuButtonHandle implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Menu menu = new Menu(thisAccount);
			menu.setVisible(true);
			dispose();			
		}
	}

}
