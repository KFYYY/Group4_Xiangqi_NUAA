package Engine;

import rule.rule;
public class AI {
	//b : black ; r : red;
	//a : shi ; b : xiang ; c : pao ; k : jiang ; n : ma ; p : zu ; r : che ;
	//oo : blank ;
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
	private final int red = 1;
	private final int black = 2;
	
	//board 9*10，左上为(0,0),右下为(8,9)
	//color表示电脑是红方，或者黑方
	//用随机的算法，调用rule判断是否合法
	//newBoard传回电脑移动后的board
	//函数返回 电脑移动的起始位置和终点位置
	public static int[] Move(final int[][] board,int[][] newBoard,final int Color){ 
		int[] position = new int[4];
		// (position[0],postion[1])代表起始位置，(position[2],postion[3])代表终止位置
		
		//write
		return position;
	}
}
