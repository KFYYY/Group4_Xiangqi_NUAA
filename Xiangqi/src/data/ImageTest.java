package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTest {
	public static void main(String[] args) throws IOException{
		Image myImg = new Image();
		ImageIO.write(myImg.ba, "gif", new File("d:\\x\\ba.gif"));
		ImageIO.write(myImg.bb, "gif", new File("d:\\x\\bb.gif"));
		ImageIO.write(myImg.bc, "gif", new File("d:\\x\\bc.gif"));
		ImageIO.write(myImg.bk, "gif", new File("d:\\x\\bk.gif"));
		ImageIO.write(myImg.bn, "gif", new File("d:\\x\\bn.gif"));
		ImageIO.write(myImg.bp, "gif", new File("d:\\x\\bp.gif"));	
		ImageIO.write(myImg.br, "gif", new File("d:\\x\\br.gif"));
		ImageIO.write(myImg.bas, "gif", new File("d:\\x\\bas.gif"));
		ImageIO.write(myImg.bbs, "gif", new File("d:\\x\\bbs.gif"));
		ImageIO.write(myImg.bcs, "gif", new File("d:\\x\\bcs.gif"));
		ImageIO.write(myImg.bks, "gif", new File("d:\\x\\bks.gif"));
		ImageIO.write(myImg.bns, "gif", new File("d:\\x\\bns.gif"));
		ImageIO.write(myImg.bps, "gif", new File("d:\\x\\bps.gif"));	
		ImageIO.write(myImg.brs, "gif", new File("d:\\x\\brs.gif"));
		
		ImageIO.write(myImg.ra, "gif", new File("d:\\x\\ra.gif"));
		ImageIO.write(myImg.rb, "gif", new File("d:\\x\\rb.gif"));
		ImageIO.write(myImg.rc, "gif", new File("d:\\x\\rc.gif"));
		ImageIO.write(myImg.rk, "gif", new File("d:\\x\\rk.gif"));
		ImageIO.write(myImg.rn, "gif", new File("d:\\x\\rn.gif"));
		ImageIO.write(myImg.rp, "gif", new File("d:\\x\\rp.gif"));	
		ImageIO.write(myImg.rr, "gif", new File("d:\\x\\rr.gif"));
		ImageIO.write(myImg.ras, "gif", new File("d:\\x\\ras.gif"));
		ImageIO.write(myImg.rbs, "gif", new File("d:\\x\\rbs.gif"));
		ImageIO.write(myImg.rcs, "gif", new File("d:\\x\\rcs.gif"));
		ImageIO.write(myImg.rks, "gif", new File("d:\\x\\rks.gif"));
		ImageIO.write(myImg.rns, "gif", new File("d:\\x\\rns.gif"));
		ImageIO.write(myImg.rps, "gif", new File("d:\\x\\rps.gif"));	
		ImageIO.write(myImg.rrs, "gif", new File("d:\\x\\rrs.gif"));
		
		ImageIO.write(myImg.oo, "gif", new File("d:\\x\\oo.gif"));
		ImageIO.write(myImg.oos, "gif", new File("d:\\x\\oos.gif"));	
		ImageIO.write(myImg.wood, "gif", new File("d:\\x\\wood.gif"));	
	}
}
