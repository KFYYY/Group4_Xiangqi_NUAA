package rule;

public class rule 
{
	    //b : black ; r : red;
		//a : shi ; b : xiang ; c : pao ; k : jiang ; n : ma ; p : zu ; r : che ;
		//oo : blank ;
		private final static int oo = 0;//空
		private final static int ba = 1;//黑 士
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
		//board 中 各棋子的数值如上，判断(originX,originY)->(toX,toY)是否合法，保证起点有棋子
		//board 9*10，左上为(0,0),右下为(8,9)
		
		private final static int redWin = 1;
		private final static int blackWin = 2;
		private static final Object[][] boardmodified = null;
		
		public static boolean isChess(int[][] board,int x,int y)
		{
			if(board[x][y]!=0)
				return true;
			else
				return false;
		}//判断指定位置是否有旗子
		
		public static int checkcolour(int[][] board)
		{
			for(int i=3;i<=5;i++)
			{	for(int j=0;j<=2;j++)
				{
					if(board[i][j]==4)
						return 1;
					if(board[i][j]==11)
						return 8;
				}
			}
			return 0;
	 	}
		
		public static void swapboard(int[][] board,int[][] boardmodified)
		{
			for(int i=0;i<9;i++)
			{	for(int j=0;j<10;j++)
		    	   {
				    boardmodified[i][j] = board[i][9-j];
			    }
			}	
		}
		
		public static void copyboard(int[][] board,int[][] boardmodified)
		{
			for(int i=0;i<9;i++)
			{
				for(int j=0;j<10;j++)
				{
					boardmodified[i][j] = board[i][j];
				}
			}
		}
		
		public static boolean isCorrect(int[][] board,int originX,int originY,int toX,int toY)
		{
			int boardmodified[][] = new int[9][10];
			int colour = checkcolour(board);
			if(colour==0)
			{
				return false;
			}
			if(colour==8)
			{
				swapboard(board,boardmodified);
				originY = 9 - originY;
				toY = 9 - toY;
			}
			if(colour==1)
			{
				copyboard(board,boardmodified);
			}
			
			if(!isChess(boardmodified,originX,originY))//初始位置没有旗子，则返回false
				return false;
			if(originX==toX&&originY==toY)
				return false;                   //没有移动，则返回false
			int chess = boardmodified[originX][originY];
			int RelativeMoveX = Math.abs(toX-originX);
	    	int RelativeMoveY = Math.abs(toY-originY);
	    	int MaxX = Math.max(originX, toX);
	    	int MaxY = Math.max(originY, toY);
	    	int MinX = Math.min(originX, toX);
	    	int MinY = Math.min(originY, toY);
			switch(chess)
			{
			    case 1:
			    	if(toY<=2&&toY>=0&&toX<=5&&toX>=3&&RelativeMoveX==1&&RelativeMoveY==1)//判断是否在指定田字格内，并且是否斜线 走的
			    	{
			    		if(isChess(boardmodified,toX,toY))
			    		{
			    			if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)//如果目标位置有旗子且为红子，则可以移动
			    				return true;
			    			else
			    				return false;
			    		}
			    		return true;
			    	}
			    	else
			    		return false;
			    case 8:
			    	if(toY<=9&toY>=7&&toX<=5&&toX>=3&&RelativeMoveX==1&&RelativeMoveY==1)
			    	{
			    		if(isChess(boardmodified,toX,toY))
			    		{
			    			if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)//如果目标位置有旗子且为黑子，则可以移动
			    				return true;
			    			else
			    				return false;
			    		}
			    		return true;
			    	}
			    	else
			    		return false;
			    case 2:
			    	int centerX = (toX+originX)/2;
			    	int centerY = (toY+originY)/2;//象飞田，中心位置不能有旗子
			    	if(isChess(boardmodified,centerX,centerY)||toY>4||RelativeMoveX!=2||RelativeMoveY!=2)//象不能过河
			    		return false;
			    	else if(isChess(boardmodified,toX,toY))
			    	{
			    		if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)//目标位置是红子，则可以移动
			    			return true;
			    		else
			    			return false;
			    	}
			    	else
			    		return true;
			    case 9:
			    	int centerX1 = (toX+originX)/2;
			    	int centerY1 = (toY+originY)/2;
			    	if(isChess(boardmodified,centerX1,centerY1)||toY<5||RelativeMoveX!=2||RelativeMoveY!=2)
			    		return false;
			    	else if(isChess(boardmodified,toX,toY))
			    	{
			    		if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    			return true;
			    		else
			    			return false;
			    	}
			    	else
			    		return true;
			    case 3:
			    	int chessnum=0;//用于记录炮要走的路径上的旗子个数
			    	if(RelativeMoveX!=0&&RelativeMoveY!=0)//不是直走的话返回false
			    		return false;
			    	else if(RelativeMoveX==0)//竖走
			    	{
			    		for(int j=MinY+1;j<MaxY;j++)
			    		{
			    			if(isChess(boardmodified,originX,j))
			    				chessnum++;
			    		}
			    		if(chessnum>1)//路径上旗子数大于1，不能移动
			    			return false;
			    		else if(chessnum==1)
			    		{
			    			if(isChess(boardmodified,toX,toY))//棋子数为1，目标位置是红子，则可移动
			    			{
			    				if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    				return false;
			    		}
			    		else
			    		{
			    			if(!isChess(boardmodified,toX,toY))
			    				return true;
			    			else
			    				return false;
			    		}
			    	}
			    	else if(RelativeMoveY==0)//横走
			    	{
			    		for(int i=MinX+1;i<MaxX;i++)
			    		{
			    			if(isChess(boardmodified,i,toY))
			    				chessnum++;
			    		}
			    		if(chessnum>1)
			    			return false;
			    		else if(chessnum==1)
			    		{
			    			if(isChess(boardmodified,toX,toY))
			    			{
			    				if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    				return false;
			    		}
			    		else
			    		{
			    			if(!isChess(boardmodified,toX,toY))
			    				return true;
			    			else
			    				return false;
			    		}
			    	}
			    case 10:
			    	int chessnum1=0;
			    	if(RelativeMoveX!=0&&RelativeMoveY!=0)
			    		return false;
			    	else if(RelativeMoveX==0)
			    	{
			    		for(int j=MinY+1;j<MaxY;j++)
			    		{
			    			if(isChess(boardmodified,originX,j))
			    				chessnum1++;
			    		}
			    		if(chessnum1>1)
			    			return false;
			    		else if(chessnum1==1)
			    		{
			    			if(isChess(boardmodified,toX,toY))
			    			{
			    				if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    				return false;
			    		}
			    		else
			    		{
			    			if(!isChess(boardmodified,toX,toY))
			    				return true;
			    			else
			    				return false;
			    		}
			    	}
			    	else if(RelativeMoveY==0)
			    	{
			    		for(int i=MinX+1;i<MaxX;i++)
			    		{
			    			if(isChess(boardmodified,i,toY))
			    				chessnum1++;
			    		}
			    		if(chessnum1>1)
			    			return false;
			    		else if(chessnum1==1)
			    		{
			    			if(isChess(boardmodified,toX,toY))
			    			{
			    				if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    				return false;
			    		}
			    		else
			    		{
			    			if(!isChess(boardmodified,toX,toY))
			    				return true;
			    			else
			    				return false;
			    		}
			    	}
			    case 4:
			    	if (RelativeMoveX == 0 && boardmodified[toX][toY] == 11){//将和帅，面对面，可以吃
			    		int Chessnum = 0;
			    		for (int j = MinY + 1; j < MaxY ;j++)
			    			if (isChess(boardmodified,toX,j))
			    				Chessnum ++;
			    		if (Chessnum == 0) return true;
			    	}
			    	if(toX<=5&&toX>=3&&toY<=2&&toY>=0&&((RelativeMoveX==1&&RelativeMoveY==0)||
			    			(RelativeMoveX==0&&RelativeMoveY==1)))//在指定田字内,横走或者竖走
			    	{
			    		if(isChess(boardmodified,toX,toY))
			    		{
			    			if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)
			    				return true;
			    			else
			    				return false;
			    		}
			    		else
			    			return true;
			    	}
			    	else
			    		return false;
			    case 11:
			    	if (RelativeMoveX == 0 && boardmodified[toX][toY] == 4){//将和帅，面对面，可以吃
			    		int Chessnum = 0;
			    		for (int j = MinY + 1; j < MaxY ;j++)
			    			if (isChess(boardmodified,toX,j))
			    				Chessnum ++;
			    		if (Chessnum == 0) return true;
			    	}
			    	if(toX<=5&&toX>=3&&toY<=9&&toY>=7&&((RelativeMoveX==1&&RelativeMoveY==0)||
			    			(RelativeMoveX==0&&RelativeMoveY==1)))
			    	{
			    		if(isChess(boardmodified,toX,toY))
			    		{
			    			if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    				return true;
			    			else
			    				return false;
			    		}
			    		else
			    			return true;
			    	}
			    	else
			    		return false;
			    case 5:
			    	if(RelativeMoveX==1&&RelativeMoveY==2)//马走日
			    	{
			    		if(toY>originY)
			    		{
			    			if(isChess(boardmodified,originX,originY+1))//判断马是否别脚
			    				return false;
			    			else
			    			{
			    				if(isChess(boardmodified,toX,toY))
			    				{
			    					if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)
			    						return true;
			    					else
			    						return false;
			    				}
			    				else
			    					return true;
			    			}
			    		}
			    		else
			    		{
			    			if(isChess(boardmodified,originX,originY-1))
			    				return false;
			    			else
			    			{
			    				if(isChess(boardmodified,toX,toY))
			    				{
			    					if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)
			    						return true;
			    					else
			    						return false;
			    				}
			    				else
			    					return true;
			    			}
			    		}
			    	}
			    	else if(RelativeMoveX==2&&RelativeMoveY==1)
			    	{
			    		if(toX>originX)
			    		{
			    			if(isChess(boardmodified,originX+1,originY))
			    			    return false;
			    			else
			    			{
			    				if(isChess(boardmodified,toX,toY))
			    				{
			    					if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)
			    						return true;
			    					else
			    						return false;
			    				}
			    				else
			    					return true;
			    			}
			    		}
			    		else
			    		{
			    			if(isChess(boardmodified,originX-1,originY))
			    			    return false;
			    			else
			    			{
			    				if(isChess(boardmodified,toX,toY))
			    				{
			    					if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)
			    						return true;
			    					else
			    						return false;
			    				}
			    				else
			    					return true;
			    		    }
			    		}
			    	}
			    	else
			    		return false;	
			    case 12:
			    	if(RelativeMoveX==1&&RelativeMoveY==2)
			    	{
			    		if(toY>originY)
			    		{
			    			if(isChess(boardmodified,originX,originY+1))
			    				return false;
			    			else
			    			{
			    				if(isChess(boardmodified,toX,toY))
			    				{
			    					if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    						return true;
			    					else
			    						return false;
			    				}
			    				else
			    					return true;
			    			}
			    		}
			    		else
			    		{
			    			if(isChess(boardmodified,originX,originY-1))
			    				return false;
			    			else
			    			{
			    				if(isChess(boardmodified,toX,toY))
			    				{
			    					if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    						return true;
			    					else
			    						return false;
			    				}
			    				else
			    					return true;
			    			}
			    		}
			    	}
			    	else if(RelativeMoveX==2&&RelativeMoveY==1)
			    	{
			    		if(toX>originX)
			    		{
			    			if(isChess(boardmodified,originX+1,originY))
			    			    return false;
			    			else
			    			{
			    				if(isChess(boardmodified,toX,toY))
			    				{
			    					if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    						return true;
			    					else
			    						return false;
			    				}
			    				else
			    					return true;
			    			}
			    		}
			    		else
			    		{
			    			if(isChess(boardmodified,originX-1,originY))
			    			    return false;
			    			else
			    			{
			    				if(isChess(boardmodified,toX,toY))
			    				{
			    					if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    						return true;
			    					else
			    						return false;
			    				}
			    				else
			    					return true;
			    		    }
			    		}
			    	}
			    	else
			    		return false;
			    case 6:
			    	if(originY<5)
			    	{
			    		if((toY-originY==1)&&toX==originX)//兵不过河的话只能向前走
			    		{
			    			if(isChess(boardmodified,toX,toY))
			    			{
			    				if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    				return true;
			    		}
			    		else
			    			return false;
			    	}
			    	else
			    	{
			    		if((toY-originY==1&&RelativeMoveX==0)||(RelativeMoveY==0&&RelativeMoveX==1))//过河了可以向前或左右走
			    		{
			    			if(isChess(boardmodified,toX,toY))
			    			{
			    				if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    			    return true;
			    		}
			    		else
			    			return false;
			    	}
			    case 13:
			    	if(originY>4)
			    	{
			    		if((originY-toY==1)&&toX==originX)
			    		{
			    			if(isChess(boardmodified,toX,toY))
			    			{
			    				if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    				return true;
			    		}
			    		else
			    			return false;
			    	}
			    	else
			    	{
			    		if((originY-toY==1&&RelativeMoveX==0)||(RelativeMoveY==0&&RelativeMoveX==1))
			    		{
			    			if(isChess(boardmodified,toX,toY))
			    			{
			    				if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    				return true;
			    		}
			    		else
			    			return false;
			    	}
			    case 7:
			    	int Chessnum = 0;
			    	if(RelativeMoveX!=0&&RelativeMoveY!=0)//车只能横走或竖走
			    		return false;
			    	else if(RelativeMoveX==0)
			    	{
			    		for(int j=MinY+1;j<MaxY;j++)
			    		{
			    			if(isChess(boardmodified,toX,j))
			    				Chessnum++;
			    		}
			    		if(Chessnum<1)//路线上没有旗子时才能移动
			    		{
			    			if(isChess(boardmodified,toX,toY))
			    			{
			    				if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    				return true;
			    		}
			    		else
			    			return false;
			    	}
			    	else if(RelativeMoveY==0)
			    	{
			    		for(int i=MinX+1;i<MaxX;i++)
			    		{
			    			if(isChess(boardmodified,i,toY))
			    				Chessnum++;
			    		}
			    		if(Chessnum<1)
			    		{
			    			if(isChess(boardmodified,toX,toY))
			    			{
			    				if(boardmodified[toX][toY]<=14&&boardmodified[toX][toY]>=8)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    				return true;
			    		}else return false;
			    	}
			    	else
			    		return false;
			    case 14:
			    	int Chessnum1 = 0;
			    	if(RelativeMoveX==0)
			    	{
			    		for(int j=MinY+1;j<MaxY;j++)
			    		{
			    			if(isChess(boardmodified,toX,j))
			    				Chessnum1++;
			    		}
			    		if(Chessnum1<1)
			    		{
			    			if(isChess(boardmodified,toX,toY))
			    			{
			    				if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    				return true;
			    		}
			    		else
			    			return false;
			    	}
			    	else if(RelativeMoveY==0)
			    	{
			    		for(int i=MinX+1;i<MaxX;i++)
			    		{
			    			if(isChess(boardmodified,i,toY))
			    				Chessnum1++;
			    		}
			    		if(Chessnum1<1)
			    		{
			    			if(isChess(boardmodified,toX,toY))
			    			{
			    				if(boardmodified[toX][toY]<=7&&boardmodified[toX][toY]>=1)
			    					return true;
			    				else
			    					return false;
			    			}
			    			else
			    				return true;
			    		}
			    		else return false;
			    	}
			    	else
			    		return false;

				default :{
					assert false : "Wrong data!";
				}
			}
			return false;
		}
		
		public static int GameOver(int[][] board){
			boolean HaveShuai = false;
			boolean HaveJiang = false;
			for (int i = 0 ;i < 9; i++)
				for (int j = 0 ;j < 10;j++)
					if (board[i][j] == bk){ 
						HaveJiang = true;
					}else
					if (board[i][j] == rk){
						HaveShuai = true ;
					}
			if (!HaveJiang) return redWin;else
			if (!HaveShuai) return blackWin;else
			return 0;
			
		}
}