package Interface;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Engine.AI;
import Engine.UserAccount;
import data.Image;
import rule.rule;

/**
 * 棋子大小：41*41 棋盘大小：377*417 377=41*9+1*8
 * 
 */
public class board extends JPanel{
	private Image myImg = new Image();
	private final int baseWidth = 1;
	private final int baseHeight = 1;
	private final int imageWidth = 41;
	private final int imageHeight = 41;
	private final int boardWidth = 9;
	private final int boardHeight = 10;
	private final int[] win = new int[]{0, 1 ,2};
	private final int red = 1;
	private final int black = 2;
	public int currentPlayer = red;
	public int playerNumber = red;
	public final static int single = 0;
	public final static int online = 1;
	public final static int load = 2;
	private int myType;
	
	private JLabel[][] label = new JLabel[boardWidth][boardHeight];
	private BufferedImage[][] buffImage = new BufferedImage[boardWidth][boardHeight];
	
	private int touchCount = 0;
	public int[][] board = new int[boardWidth][boardHeight];
	
	private int originI = 0,originJ = 0;
	int[][] move = new int[10000][4];
	int moveNumber = 0;
	private boolean gameOver = false;
	private String thisAccount;
	
	private boolean haveMove = false;
	//0为初始模板，
	private JLabel[][][] labelRecord = new JLabel[10000][boardWidth][boardHeight];
	private int[][][] boardRecord = new int[10000][boardWidth][boardHeight];
	private BufferedImage[][][] buffImageRecord = new BufferedImage[10000][boardWidth][boardHeight];
	
	private boolean isPause = false;
	public board(String account,int type,int number) throws IOException{
		playerNumber = number;
		board(account,type);
		if (playerNumber != currentPlayer) AllFlip();
	}
	public board(String account,int type) throws IOException{
		board(account,type);
	}
	private void board(String account, int type) throws IOException {
		// TODO Auto-generated method stub
		thisAccount = account;
		myType = type;
		setSize(395,4);
		setLayout(null);
		setOpaque(false);
		ImageMouse imageMouse = new ImageMouse();
		addMouseListener(imageMouse);
		
		InitBoard();
		InitLableAndImage();
		
		AllRecord(0);		
	}

	private final int oo = 0;//空
	private final int ba = 1;//黑 士
	private final int bb = 2;
	private final int bc = 3;
	private final int bk = 4;
	private final int bn = 5;
	private final int bp = 6;
	private final int br = 7;
	
	private final int ra = 8;
	private final int rb = 9;
	private final int rc = 10;
	private final int rk = 11;
	private final int rn = 12;
	private final int rp = 13;
	private final int rr = 14;
	public void InitBoard(){
		board[0][0] = br;board[1][0] = bn;board[2][0] = bb;board[3][0] = ba;board[4][0] = bk;board[5][0] = ba;board[6][0] = bb;board[7][0] = bn;board[8][0] = br;
		board[0][3] = board[2][3] = board[4][3] = board[6][3] = board[8][3] = bp;
		board[1][2] = board[7][2] = bc;

		board[0][9] = rr;board[1][9] = rn;board[2][9] = rb;board[3][9] = ra;board[4][9] = rk;board[5][9] = ra;board[6][9] = rb;board[7][9] = rn;board[8][9] = rr;
		board[0][6] = board[2][6] = board[4][6] = board[6][6] = board[8][6] = rp;
		board[1][7] = board[7][7] = rc;
	}
	private void InitLableAndImage() throws IOException {
		// TODO Auto-generated method stub
		for (int i = 0 ; i < boardWidth ; i++){
			for (int j = 0; j < boardHeight ; j++){
				label[i][j] = new JLabel();
				AddImage(Image.oo,i,j);
			}
		}
		AddImage(Image.br,0,0);
		AddImage(Image.bn,1,0);
		AddImage(Image.bb,2,0);
		AddImage(Image.ba,3,0);
		AddImage(Image.bk,4,0);
		AddImage(Image.ba,5,0);
		AddImage(Image.bb,6,0);
		AddImage(Image.bn,7,0);
		AddImage(Image.br,8,0);
		AddImage(Image.bc,1,2);
		AddImage(Image.bc,7,2);
		AddImage(Image.bp,0,3);
		AddImage(Image.bp,2,3);
		AddImage(Image.bp,4,3);
		AddImage(Image.bp,6,3);
		AddImage(Image.bp,8,3);
		
		AddImage(Image.rp,0,6);
		AddImage(Image.rp,2,6);
		AddImage(Image.rp,4,6);
		AddImage(Image.rp,6,6);
		AddImage(Image.rp,8,6);
		AddImage(Image.rr,0,9);
		AddImage(Image.rn,1,9);
		AddImage(Image.rb,2,9);
		AddImage(Image.ra,3,9);
		AddImage(Image.rk,4,9);
		AddImage(Image.ra,5,9);
		AddImage(Image.rb,6,9);
		AddImage(Image.rn,7,9);
		AddImage(Image.rr,8,9);
		AddImage(Image.rc,1,7);
		AddImage(Image.rc,7,7);		
	}
	public class ImageMouse implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (gameOver || isPause) return ;
			if (currentPlayer != playerNumber) return ;//online
			if (myType == load) return ; // load game
			int i = (e.getX()-baseWidth)/imageWidth;
			int j = (e.getY()-baseHeight)/imageHeight;
			if (i>8 || j>9) return ;
			
			if (touchCount <=1 && board[i][j]!=0 && (board[i][j]-1)/7+currentPlayer == 2){
				touchCount = 1;
				originI=i;
				originJ=j;
				AbandonSelect();
				SelectImage(i,j);
			}else
			if (touchCount == 1 && rule.isCorrect(board,originI,originJ,i,j)){
				AllMove(originI,originJ,i,j);
				haveMove = true;
				
				if (rule.GameOver(board) == win[playerNumber]){
					gameOver = true;
					ShowMessageAndSave("You win!");
				}else 
				if (rule.GameOver(board) == win[3 - playerNumber]){
					gameOver = true;	
					ShowMessageAndSave("You lost!");	
				}
				if (gameOver) return ;
				
				if (myType == single){
					int[] thisMove = new int[4];
					thisMove = AI.Move(board, 3 - currentPlayer);
					AllMove(thisMove[0],thisMove[1],thisMove[2],thisMove[3]);
					
					if (rule.GameOver(board) == win[playerNumber]){
						gameOver = true;
						ShowMessageAndSave("You win!");
					}else 
					if (rule.GameOver(board) == win[3 - playerNumber]){
						gameOver = true;	
						ShowMessageAndSave("You lost!");	
					}
				}else 
				if (myType == online){
				}
				touchCount = 0;
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub	
		}
	}
	public void ShowMessageAndSave(String s){
		JOptionPane.showMessageDialog(null,s);
		
		try {
			UserAccount userAccount = new UserAccount();
			userAccount.createNewManual(thisAccount, moveNumber, move);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void AllMove(int originI,int originJ,int i,int j){
		board = boardMove(board,originI,originJ,i,j);
		AbandonSelect();
		moveImage(originI,originJ,i,j);
		
		int[] thisMove = {originI,originJ,i,j};
		move[moveNumber] = thisMove;
		moveNumber++;
		AllRecord(moveNumber);
	}
	public void AllRecord(int moveNumber){
		for (int i = 0 ;i < boardWidth ; i++)
			for (int j = 0 ;j < boardHeight; j++){
				labelRecord[moveNumber][i][j] = label[i][j];
				buffImageRecord[moveNumber][i][j] = buffImage[i][j];
				boardRecord[moveNumber][i][j] = board[i][j];
			}		
	}
	public void getRecord(int moveNumber){
		for (int i = 0; i < boardWidth; i++){
			for (int j = 0;j < boardHeight; j++){
				board[i][j] = boardRecord[moveNumber][i][j];
				AddImage(buffImageRecord[moveNumber][i][j], i, j);
				
				//System.out.print(board[i][j]+ " ");
			}		
			//System.out.println();
		}
	}
	public void AbandonSelect(){
		for (int i= 0; i < boardWidth; i++)
			for (int j= 0;j < boardHeight ;j++)
				AddImage(buffImage[i][j],i,j);
				//AddImage(AbandonSelect(buffImage[i][j]),i,j);
		
	}
	/*public BufferedImage AbandonSelect(BufferedImage image){
		if (image == myImg.bas) return myImg.ba;
		if (image == myImg.bbs) return myImg.bb;
		if (image == myImg.bcs) return myImg.bc;
		if (image == myImg.bks) return myImg.bk;
		if (image == myImg.bns) return myImg.bn;
		if (image == myImg.bps) return myImg.bp;
		if (image == myImg.brs) return myImg.br;
		if (image == myImg.ras) return myImg.ra;
		if (image == myImg.rbs) return myImg.rb;
		if (image == myImg.rcs) return myImg.rc;
		if (image == myImg.rks) return myImg.rk;
		if (image == myImg.rns) return myImg.rn;
		if (image == myImg.rps) return myImg.rp;
		if (image == myImg.rrs) return myImg.rr;
		if (image == myImg.oos) return myImg.oo;
		return image;
	}*/

	public int[][] boardMove(int[][] board, int originI, int originJ, int i, int j) {
		// TODO Auto-generated method stub
		int[][] newBoard = new int[boardWidth][boardHeight];
		newBoard = board;
		newBoard[i][j] = newBoard[originI][originJ];
		newBoard[originI][originJ] = 0;
		return newBoard;
	}
	public void SelectImage(int i,int j){
		BufferedImage image = buffImage[i][j];
		label[i][j].setIcon(null);
		ImageIcon imageIcon = new ImageIcon(ChangeImage(image));
		label[i][j].setIcon(imageIcon);
	}
	public void moveImage(int i,int j,int x,int y){
		BufferedImage image = getImage(i,j);
		AddImage(Image.oo,i,j);SelectImage(i,j);
		AddImage(image,x,y);SelectImage(x,y);
	}
	public BufferedImage getImage(int i,int j){
		return buffImage[i][j];
	}
	private BufferedImage ChangeImage(BufferedImage image){
		if (image == Image.ba) return Image.bas;
		if (image == Image.bb) return Image.bbs; 
		if (image == Image.bc) return Image.bcs;
		if (image == Image.bk) return Image.bks;
		if (image == Image.bn) return Image.bns;
		if (image == Image.bp) return Image.bps;
		if (image == Image.br) return Image.brs;
		if (image == Image.ra) return Image.ras;
		if (image == Image.rb) return Image.rbs;
		if (image == Image.rc) return Image.rcs;
		if (image == Image.rk) return Image.rks;
		if (image == Image.rn) return Image.rns;
		if (image == Image.rp) return Image.rps;
		if (image == Image.rr) return Image.rrs;
		if (image == Image.oo) return Image.oos;
		assert false:"false";
		return null;//false
	}
	private void AddImage(BufferedImage image,int i,int j){
		buffImage[i][j] = image;
		
		label[i][j].setIcon(null);
		
		ImageIcon imageIcon = new ImageIcon(image);
		label[i][j].setIcon(imageIcon);
		label[i][j].setBounds(baseWidth+i*imageWidth, baseHeight+j*imageHeight, imageWidth, imageHeight);
		add(label[i][j]);
	}
	//setBackground
	protected void paintComponent(Graphics g){
		super.paintComponent(g);	
		g.drawImage(Image.wood, 0, 0, 380, 417, this);
	}//380,417
	public void AllFlip(){
		currentPlayer = 3 - currentPlayer;
		for (int i = 0 ; i < boardWidth ;i ++)
			for (int j = 0; j < boardHeight/2 ;j++){
				for (int moveStep = -1 ;moveStep < moveNumber ;){
					if (moveStep>=0){
						move[moveStep][0] = 8 - move[moveStep][1];move[moveStep][2] = 8 - move[moveStep][3];
						move[moveStep][1] = 9 - move[moveStep][1];move[moveStep][3] = 9 - move[moveStep][3];}
					moveStep++;
					JLabel labelTmp = labelRecord[moveStep][i][j]; labelRecord[moveStep][i][j]=labelRecord[moveStep][8-i][9-j];labelRecord[moveStep][8-i][9-j]=labelTmp;
					BufferedImage buffImageTmp = buffImageRecord[moveStep][i][j]; buffImageRecord[moveStep][i][j]=buffImageRecord[moveStep][8-i][9-j];buffImageRecord[moveStep][8-i][9-j] = buffImageTmp;
					int boardTmp = boardRecord[moveStep][i][j]; boardRecord[moveStep][i][j]=boardRecord[moveStep][8-i][9-j]; boardRecord[moveStep][8-i][9-j]=boardTmp;					
				}
			}
		getRecord(moveNumber);
	}
	public boolean LastBoard(){//悔一次棋
		if(moveNumber == 0) return false;
		
		moveNumber--;
		getRecord(moveNumber);
		return true;
	}
	public boolean LastBoardSingle(){//和电脑对战悔棋
		//由于自己下完后电脑迅速下了一步，所以要退两步（退电脑的一步和退自己的一步）
		if (!LastBoard()) return false;
		if (!LastBoard()) return false;
		return true;
	}
	public void ReStarting() throws IOException{//重赛
		getRecord(0);
		moveNumber = 0;
	}
	public void Pause(){//暂停
		isPause = true;
	}
	public void GoingOn(){//继续
		isPause = false;
	}
	public int[] getThisMove() {
		// TODO Auto-generated method stub
		if (haveMove){
			haveMove = false;
			return move[moveNumber-1];
		}
		return new int[]{-1,-1,-1,-1};
	}
}