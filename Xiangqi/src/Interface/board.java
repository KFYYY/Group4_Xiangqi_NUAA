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
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Image;

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
	private JLabel[][] label = new JLabel[boardWidth][boardHeight];
	
	board() throws IOException{
		setSize(395,445);
		setLayout(null);
		
		ImageMouse imageMouse = new ImageMouse();
		addMouseListener(imageMouse);
		
		for (int i = 0 ; i < boardWidth ; i++){
			for (int j = 0; j < boardHeight ; j++){
				label[i][j] = new JLabel();
				AddImage(myImg.oo,i,j);
			}
		}
		AddImage(myImg.br,0,0);
		AddImage(myImg.bn,1,0);
		AddImage(myImg.bb,2,0);
		AddImage(myImg.ba,3,0);
		AddImage(myImg.bk,4,0);
		AddImage(myImg.ba,5,0);
		AddImage(myImg.bb,6,0);
		AddImage(myImg.bn,7,0);
		AddImage(myImg.br,8,0);
		AddImage(myImg.bc,1,2);
		AddImage(myImg.bc,7,2);
		AddImage(myImg.bp,0,3);
		AddImage(myImg.bp,2,3);
		AddImage(myImg.bp,4,3);
		AddImage(myImg.bp,6,3);
		AddImage(myImg.bp,8,3);
		
		AddImage(myImg.rp,0,6);
		AddImage(myImg.rp,2,6);
		AddImage(myImg.rp,4,6);
		AddImage(myImg.rp,6,6);
		AddImage(myImg.rp,8,6);
		AddImage(myImg.rr,0,9);
		AddImage(myImg.rn,1,9);
		AddImage(myImg.rb,2,9);
		AddImage(myImg.ra,3,9);
		AddImage(myImg.rk,4,9);
		AddImage(myImg.ra,5,9);
		AddImage(myImg.rb,6,9);
		AddImage(myImg.rn,7,9);
		AddImage(myImg.rr,8,9);
		AddImage(myImg.rc,1,7);
		AddImage(myImg.rc,7,7);
		
		moveImage(7,7,7,0,myImg.rc);
	}
	public class ImageMouse implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int i = e.getX();
			int j = e.getY();
			//System.out.println(i);
			//System.out.println(j);
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
	public void SelectImage(BufferedImage image,int i,int j){
		label[i][j].setIcon(null);
		ImageIcon imageIcon = new ImageIcon(ChangeImage(image));
		label[i][j].setIcon(imageIcon);
	}
	public void moveImage(int i,int j,int x,int y,BufferedImage image){
		AddImage(myImg.oos,i,j);
		AddImage(image,x,y);
		SelectImage(image,x,y);
	}
	private BufferedImage ChangeImage(BufferedImage image){
		if (image == myImg.ba){
			return myImg.bas;
		}else
		if (image == myImg.bb){
			return myImg.bbs;
		}else 
		if (image == myImg.bc){
			return myImg.bcs;
		}else
		if (image == myImg.bk){
			return myImg.bks;
		}else
		if (image == myImg.bn){
			return myImg.bns;
		}else
		if (image == myImg.bp){
			return myImg.bps;
		}else
		if (image == myImg.br){
			return myImg.brs;
		}else
		if (image == myImg.ra){
			return myImg.ras;
		}else
		if (image == myImg.rb){
			return myImg.rbs;
		}else 
		if (image == myImg.rc){
			return myImg.rcs;
		}else
		if (image == myImg.rk){
			return myImg.rks;
		}else
		if (image == myImg.rn){
			return myImg.rns;
		}else
		if (image == myImg.rp){
			return myImg.rps;
		}else
		if (image == myImg.rr){
			return myImg.rrs;
		}else
		if (image == myImg.oo){
			return myImg.oos;
		}
		assert false:"false";
		return null;//false
	}
	private void AddImage(BufferedImage image,int i,int j){
		label[i][j].setIcon(null);
		
		ImageIcon imageIcon = new ImageIcon(image);
		label[i][j].setIcon(imageIcon);
		label[i][j].setBounds(baseWidth+i*imageWidth, baseHeight+j*imageHeight, imageWidth, imageHeight);
		add(label[i][j]);
	}
	//setBackground
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(myImg.wood, 0, 0, this.getWidth(), this.getHeight(), this);	
	}
}


//b : black ; r : red;
//a : shi ; b : xiang ; c : pao ; k : jiang ; n : ma ; p : zu ; r : che ;
//s : select
//oo : blank ;
//oos : blank_select
/*private int HashImage(BufferedImage image){
	if (image == myImg.ba ){
		return 0;
	}else 
	if (image == myImg.bas){
		return 1;
	}else
	if (image == myImg.bb){
		return 2;
	}else
	if (image == myImg.bbs){
		return 3;
	}else 
	if (image == myImg.bc){
		return 4;
	}else
	if (image == myImg.bcs){
		return 5;
	}else
	if (image == myImg.bk){
		return 6;
	}else 
	if (image == myImg.bks){
		return 7;
	}else
	
	return -1;//fault
}*/