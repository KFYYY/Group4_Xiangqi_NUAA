package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Engine.UserAccount;

public class boardFrame extends JFrame{
	protected int myType;//游戏类型，对战电脑为0，对战人为1
	protected String myAccount;
	public board myBoard;
	//protected gameButton myButton;
	private boolean haveBack = false; //是否悔过棋
	public final static int single = 0;
	public final static int online = 1;
	
	public JButton c1=new JButton("悔棋");
	public JButton c2=new JButton("暂停");
	public JButton c3=new JButton("重赛");
	public JButton c5=new JButton("返回");
	public JButton c6=new JButton("存盘");
	public boardFrame(String account,int type) throws IOException{
		super(account);
		myBoard = new board(account,type);
		boardFrame(account,type);
	}
	public boardFrame(String account,int type,int number) throws IOException{
		super(account);
		myBoard = new board(account,type,number);
		boardFrame(account,type);
	}
	private void boardFrame(String account,int type) throws IOException
	{//number 为 red 或者 black
		myAccount = account;
		myType = type;
		setSize(390, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		create();
		c1.setBounds(0, 420, 70, 30);
		c2.setBounds(70, 420,70, 30);
		c3.setBounds(140,420,70,30);
		c5.setBounds(210,420,70,30);
		c6.setBounds(280,420,70,30);
		//类似的添加c2,c3,c4的事件，调用board中的ReStarting，Pause，GoingOn
		//当暂停按钮触发的时候，暂停按钮隐藏，继续按钮出现在暂停按钮的位置
		//c4为继续按钮，类似的
	}
	public void create(){
		
		c1.setFont(new java.awt.Font("华文行楷", 1, 15));
		c1.setBackground(Color.gray);
		add(c1);
	    c2.setFont(new java.awt.Font("华文行楷", 1, 15));
		c2.setBackground(Color.gray);
		add(c2);
		c3.setFont(new java.awt.Font("华文行楷", 1, 15));
		c3.setBackground(Color.gray);
		add(c3);
		c5.setFont(new java.awt.Font("华文行楷", 1, 15));
		c5.setBackground(Color.gray);
		add(c5);		
		c6.setFont(new java.awt.Font("华文行楷", 1, 15));
		c6.setBackground(Color.gray);
		add(c6);
		add(myBoard);
		 
		c1.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if (myType == single){
							if ( myBoard.LastBoardSingle() == false )
								JOptionPane.showMessageDialog(null, "can not back!");
						}else
						if (myType == online){
							//not do now
						}else
							assert false :"wrong";
					}
			
				}
		);
		c2.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if((c2.getText().equals("暂停")))
					{
						c2.setText("开始");
					    c2.setFont(new java.awt.Font("华文行楷", 1, 15));
					    myBoard.Pause();
					}
					else if(c2.getText().equals("开始"))
					{
						c2.setText("暂停");
					    c2 .setFont(new java.awt.Font("华文行楷", 1, 15));
					    myBoard.GoingOn();
					}
				}
			}
		);
		c3.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						myBoard.ReStarting();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		);
		c5.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					Menu menu = new Menu(myAccount);
					menu.setVisible(true);
					dispose();
				}
			}
		);
		c6.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
					//System.out.println(myAccount+" "+myBoard.moveNumber);
					try {
						
						if (myBoard.moveNumber != 0 && UserAccount.createNewManual(myAccount, myBoard.moveNumber, myBoard.move)){
							JOptionPane.showMessageDialog(null,"Save successfully!");
						}else{
							JOptionPane.showMessageDialog(null,"Save failed!");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		);		
	}
}
