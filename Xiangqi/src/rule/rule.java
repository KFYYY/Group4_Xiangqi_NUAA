package rule;

public class rule {
	//b : black ; r : red;
	//a : shi ; b : xiang ; c : pao ; k : jiang ; n : ma ; p : zu ; r : che ;
	//oo : blank ;
	private final int oo = 0;//��
	private final int ba = 1;//�� ʿ
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
	//board �� �����ӵ���ֵ���ϣ��ж�(originX,originY)->(toX,toY)�Ƿ�Ϸ�����֤���������
	//board 9*10������Ϊ(0,0),����Ϊ(8,9)
	public static boolean isCorrect(int[][] board,int originX,int originY,int toX,int toY){
		//��д
		return false;
	}
}
