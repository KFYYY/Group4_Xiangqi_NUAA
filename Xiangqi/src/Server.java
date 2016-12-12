import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import Interface.board;
import Interface.boardFrame;
import rule.rule;

public class Server extends JFrame{
	private String[] MARK = {"","red","black"};
	private final static int PLAYER_RED = 1;
	private final static int PLAYER_BLACK = 2;
	private int currentPlayer = 1;
	private JTextArea outputArea;
	private ExecutorService runGame;
	private Lock gameLock;
	private Condition otherPlayerConnected;
	private Condition otherPlayerTurn;
	private ServerSocket server;
	private boardFrame myBoardFrame;
	private static int online = 1;
	
	private Player[] players;
	private int red = 1;
	
	public Server() throws IOException
	{
		super( "Xiangqi Server" );
		runGame = Executors.newFixedThreadPool( 2 );
		gameLock = new ReentrantLock();
		
		otherPlayerConnected = gameLock.newCondition();
		otherPlayerTurn = gameLock.newCondition();
		
		players = new Player[3];
		myBoardFrame = new boardFrame("Server", online,red );//
		myBoardFrame.setVisible(false);
		server = new ServerSocket( 12345 , 2);
		
		outputArea = new JTextArea();
		
		add( outputArea,BorderLayout.CENTER);
		outputArea.setEditable(false);
		outputArea.setText("Server awaiting connection\n");
		
		
		setSize(300,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void execute() throws IOException
	{
		for (int i = 1;i <= 2 ;i++){
			players[i] = new Player( server.accept() , i);
			runGame.execute( players[i] );
		}
		gameLock.lock();
		players[ PLAYER_RED ].setSuspended(false);
		otherPlayerConnected.signal();
		gameLock.unlock();
	}
	private class Player implements Runnable{
		private Socket connection;
		private Scanner input;
		private Formatter output;
		private int playerNumber;//1 red ;2 black
		private boolean suspended = true;
		private String mark;
		private String name;
		
		Player(Socket socket,int number) throws IOException{
			playerNumber = number;
			mark = MARK[number];
			connection = socket;
			input = new Scanner( connection.getInputStream() );
			output = new Formatter( connection.getOutputStream() );
		}
		public void otherPlayerMove(int[] move){
			output.format("Opponent moved\n");
			output.format("%d %d %d %d\n",move[0],move[1],move[2],move[3]);
			output.flush();
		}
		public void setName(String s){
			name = s;
		}
		public String getOtherPlayerName(){
			return players[3-playerNumber].name;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			setName(input.nextLine());
			displayMessage("Player " +name+" connected\n");
			output.format("%s\n", mark);
			output.flush();
			
			if (playerNumber == PLAYER_RED){
				gameLock.lock();
				
				try{
					while (suspended){
							otherPlayerConnected.await();
					}
				}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					gameLock.unlock();
				}
				
				output.format("Other Player connected.You move\n");
				output.format("You move\n");//let player move
				output.flush();
			}
			else {
				output.format("Other Player connected,please wait.\n");
				output.flush();
			}
			
			while (isGameOver() == 0){
				if (input.hasNext()){
					String message = input.nextLine();
					//displayMessage("*******"+message+"*********\n");
					if (message.equals("move")){
						int[] move =new int[4];
						for (int i = 0 ;i < 4; i++) move[i] = input.nextInt();
						
						try {
							if (validateAndMove(move,playerNumber)){
								move = getCorrectMove(playerNumber,move);
								displayMessage("move:"+move[0]+" "+move[1]+" "+move[2]+" "+move[3]+"\n");
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
						}				
					}else
					if (message.equals("Tim")){
						message = input.nextLine();
						//displayMessage("*******"+message+"*********\n");
						displayMessage(MARK[playerNumber]+" : "+message+"\n");
						final String myMessage = "Opponent : "+message+"\n";

						//otherPlayerTurn.signal();
						//otherPlayerConnected.signal();
						
						players[3-playerNumber].output.format(myMessage);
						players[3-playerNumber].output.flush();
						/*try {
							gameLock.lock();
							otherPlayerTurn.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							gameLock.unlock();
						}*/
					}					
				}
			}
			try {
				connection.close();
				displayMessage(name+" disconnected\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void setSuspended(boolean status){
			suspended = status;
		}
	}
	public boolean validateAndMove(int[]move,int player) throws InterruptedException{
		while (player != currentPlayer){
			gameLock.lock();			
			otherPlayerTurn.await();
			gameLock.unlock();
		}
		move = getCorrectMove(player,move);
		
		if (rule.isCorrect(myBoardFrame.myBoard.board,move[0],move[1],move[2],move[3])){
			myBoardFrame.myBoard.AllMove(move[0],move[1],move[2],move[3]);
			//displayMessage(isGameOver()+"\n");
			//assert false:"not game over";
			currentPlayer = 3 - currentPlayer;
			
			move = getCorrectMove(player,move);
			
			players[ currentPlayer ].otherPlayerMove(move);
			gameLock.lock();
			otherPlayerTurn.signal();
			gameLock.unlock();
			
			return true;
		}
		return false;
	}
	private int[] getCorrectMove(int player,int[] move){
		if (player == PLAYER_BLACK){
			move[0] = 8 - move[0];move[2] = 8 - move[2];
			move[1] = 9 - move[1];move[3] = 9 - move[3];
		}
		return move;
	}
	private void displayMessage(final String messageToDisplay){
		SwingUtilities.invokeLater(
			new Runnable()
			{
				@Override
				public void run() {
					// TODO Auto-generated method stub
					outputArea.append(messageToDisplay);
				}
			});
	}
	public int isGameOver(){
		return rule.GameOver(myBoardFrame.myBoard.board);
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Server application = new Server();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.execute();
	}

}
