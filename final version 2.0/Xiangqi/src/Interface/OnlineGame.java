package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Interface.Menu;
import Interface.board;
import Interface.boardFrame;
import rule.rule;

public class OnlineGame extends boardFrame implements Runnable{
	
	protected static final String[] MARK = {"","red","black"};
	private String myHost;
	private Socket connection;
	private Scanner input;
	private Formatter output;
	private String myMark;
	private String RED_MARK = "red";
	private boolean myTurn;
	private JTextArea displayArea;
	private JTextArea chatArea;
	private int red = 1;
	private JButton chatSubmit;
	private boolean connected = true;
	public OnlineGame(String host,String account, int type) throws IOException, InterruptedException {
		super(account, type);
		// TODO Auto-generated constructor stub
		 setTitle(account);
		 myHost = host;
		 
		 setVisible(true);
		 setSize(750,500);

		 c2.setVisible(false);
		 c3.setVisible(false);
		 c5.setVisible(false);
		 
		 c1.setVisible(true);
		 c1.setText("²Ëµ¥");
		 c1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Menu menu = new Menu(account);
				menu.setVisible(true);
				dispose();
			}
		 });
		 displayArea = new JTextArea();
		 displayArea.setEditable(false);
		 JScrollPane myJScrollPane = new JScrollPane(displayArea);
		 myJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		 myJScrollPane.setBounds(390, 0, 300, 300);
		 myJScrollPane.setOpaque(false);
		 
		 chatArea = new JTextArea();
		 chatArea.setText("chat place");
		 chatArea.setBounds(390, 300, 300, 20);
		 //chatArea.setSize(300,20);
		 chatSubmit = new JButton("submit");
		 chatSubmit.setBounds(390,330,100,30);
		 chatSubmit.addActionListener(new ChatActionListener());

		 add(myJScrollPane); 
		 add(chatArea);
		 add(chatSubmit);
		 add(c1);
		 add(c2);
		 add(c3);
		 add(myBoard);

		 startClient();
		 setBoardPlayerNumber(2 - (myTurn?1:0) );
		 setBoardCurrentPlayer( red );
	}
	private class ChatActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			output.format("Tim\n"+chatArea.getText()+"\n");
			output.flush();
			displayMessage("I : "+chatArea.getText()+"\n");
			chatArea.setText("");
		}
	 }
	private void setBoardPlayerNumber(int playerNumber) {
		// TODO Auto-generated method stub
		myBoard.playerNumber = playerNumber;
	}
	private void setBoardCurrentPlayer(int currentPlayer){
		myBoard.currentPlayer = currentPlayer;
	}
	private void startClient() throws UnknownHostException, IOException, InterruptedException {
		connection = new Socket(InetAddress.getByName(myHost),12345);
		
		input = new Scanner( connection.getInputStream());
		output = new Formatter( connection.getOutputStream());
		
		output.format(myAccount+"\n");//output my name
		output.flush();
		
		myMark = input.nextLine();
		myTurn = myMark.equals(RED_MARK);
		
		//displayMessage(myTurn +"\n");
		if (!myTurn) myBoard.AllFlip();
		
		ExecutorService worker = Executors.newFixedThreadPool(1);
		worker.execute(this);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(
				new Runnable()
				{
					public void run(){
						displayArea.setText("You are player \""+myMark+"\"\n");
					}
				}
		);
		
		try{
			while (true){
				if (input.hasNextLine()){
					try {
						processMessage( input.nextLine());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (rule.GameOver(myBoard.board) != 0) break;
				}
			}
		}finally{
			try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	private void processMessage(String message) throws InterruptedException {
		//if (rule.GameOver(myBoard.board) != 0) return ;
		//displayMessage("******"+message+"***********\n");
		if ( message.equals("You move")){
			int[] move = new int[4];
			move = getValidMoveAndMove();
			
			output.format("move\n%d %d %d %d\n", move[0],move[1],move[2],move[3]);
			output.flush();
			
			displayMessage("Valid move, please wait.\n");
			setBoardCurrentPlayer(3 - myBoard.currentPlayer);
		}else
		if (message.equals("Opponent moved")){
			int[] move = new int[4];
			for (int i = 0 ;i < 4;i++)move[i] = input.nextInt();
			input.nextLine();
			
			move[0] = 8 - move[0]; move[2] = 8 - move[2];
			move[1] = 9 - move[1]; move[3] = 9 - move[3];
			
			setMove(move);
			/*displayMessage("other' move:"+move[0]+" "+move[1]+" "+move[2]+" "+move[3]+"\nboard:\n");
			
			
			for (int i = 0 ; i < 9; i++){
				for (int j = 0; j < 10;j++){
					displayMessage(myBoard.board[i][j]+" ");
				}
				displayMessage("\n");
			}*/
			if (rule.GameOver(myBoard.board) != 0){
				JOptionPane.showMessageDialog(null, "You lost!");
				return ;
			}
			displayMessage("Opponent moved. Your turn.\n");
			setBoardCurrentPlayer(3 - myBoard.currentPlayer);
		
			move = getValidMoveAndMove();
			
			output.format("move\n%d %d %d %d\n", move[0],move[1],move[2],move[3]);
			output.flush();
			
			displayMessage("Valid move, please wait.\n");
			setBoardCurrentPlayer(3 - myBoard.currentPlayer);
		}else
			displayMessage(message + "\n");
	}
	private int[] getValidMoveAndMove() throws InterruptedException {
		// TODO Auto-generated method stub
		int[] move = new int[4];
		do{
			move = myBoard.getThisMove();
			Thread.sleep(100);
		}while (move[0] == -1);
		return move;
	}
	private void displayMessage(final String messageToDisplay){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						displayArea.append(messageToDisplay);
					}
				}
		);
	}
	private void setMove(final int[] move){
		myBoard.AllMove(move[0], move[1], move[2], move[3]);
	}

}
