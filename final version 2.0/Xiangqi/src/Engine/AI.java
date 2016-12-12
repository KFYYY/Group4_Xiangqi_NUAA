package Engine;

import java.util.Random;

import rule.rule;
public class AI {
	//b : black ; r : red;
	//a : shi ; b : xiang ; c : pao ; k : jiang ; n : ma ; p : zu ; r : che ;
	//oo : blank ;
	private final static int oo = 0;//��
	private final static int ba = 1;//�� ʿ
	private final static int bb = 2;
	private final static int bc = 3;
	private final static int bk = 4;
	private final static int bn = 5;
	private final static int bp = 6;
	private final static int br = 7;
	
	private final static int ra = 8;
	private final static int rb = 9;
	private final static int rc = 10;
	private final static int rk = 11;
	private final static int rn = 12;
	private final static int rp = 13;
	private final static int rr = 14;
	private final static int red = 1;
	private final static int black = 2;
	
	private static int currentPlayer = black;
	//board 9*10������Ϊ(0,0),����Ϊ(8,9)
	//color��ʾ�����Ǻ췽�����ߺڷ�
	//��������㷨������rule�ж��Ƿ�Ϸ�
	//newBoard���ص����ƶ����board
	//�������� �����ƶ�����ʼλ�ú��յ�λ��
	public static int[] Move(final int[][] board,final int player){
		currentPlayer = player;
		int[] position = new int[4];
		// (position[0],postion[1])������ʼλ�ã�(position[2],postion[3])������ֹλ��
		
		Random r = new Random();
		while (true){
			int x1 = r.nextInt(9) , x2 = r.nextInt(9);
			int y1 = r.nextInt(10), y2 = r.nextInt(10);
			
			if (rule.isCorrect(board, x1, y1, x2, y2) && board[x1][y1]!=0 && (board[x1][y1]-1)/7+currentPlayer  == 2){
				position[0] = x1;
				position[1] = y1;
				position[2] = x2;
				position[3] = y2;
				
				return position;
			}
		}
	}
}
