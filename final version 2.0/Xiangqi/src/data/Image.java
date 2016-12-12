package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	//b : black ; r : red;
	//a : shi ; b : xiang ; c : pao ; k : jiang ; n : ma ; p : zu ; r : che ;
	//s : select
	//oo : blank ;
	//oos : blank_select
	public static BufferedImage ba,bb,bc,bk,bn,bp,br;
	public static BufferedImage bas,bbs,bcs,bks,bns,bps,brs;
	public static BufferedImage ra,rb,rc,rk,rn,rp,rr;
	public static BufferedImage ras,rbs,rcs,rks,rns,rps,rrs;
	public static BufferedImage oo,oos,wood;
	public Image() throws IOException{
		ba = ImageIO.read(new File("image/wood/ba.gif"));
		bb = ImageIO.read(new File("image/wood/bb.gif"));
		bc = ImageIO.read(new File("image/wood/bc.gif"));
		bk = ImageIO.read(new File("image/wood/bk.gif"));
		bn = ImageIO.read(new File("image/wood/bn.gif"));
		bp = ImageIO.read(new File("image/wood/bp.gif"));
		br = ImageIO.read(new File("image/wood/br.gif"));
		bas = ImageIO.read(new File("image/wood/bas.gif"));
		bbs = ImageIO.read(new File("image/wood/bbs.gif"));
		bcs = ImageIO.read(new File("image/wood/bcs.gif"));
		bks = ImageIO.read(new File("image/wood/bks.gif"));
		bns = ImageIO.read(new File("image/wood/bns.gif"));
		bps = ImageIO.read(new File("image/wood/bps.gif"));
		brs = ImageIO.read(new File("image/wood/brs.gif"));

		ra = ImageIO.read(new File("image/wood/ra.gif"));
		rb = ImageIO.read(new File("image/wood/rb.gif"));
		rc = ImageIO.read(new File("image/wood/rc.gif"));
		rk = ImageIO.read(new File("image/wood/rk.gif"));
		rn = ImageIO.read(new File("image/wood/rn.gif"));
		rp = ImageIO.read(new File("image/wood/rp.gif"));
		rr = ImageIO.read(new File("image/wood/rr.gif"));
		ras = ImageIO.read(new File("image/wood/ras.gif"));
		rbs = ImageIO.read(new File("image/wood/rbs.gif"));
		rcs = ImageIO.read(new File("image/wood/rcs.gif"));
		rks = ImageIO.read(new File("image/wood/rks.gif"));
		rns = ImageIO.read(new File("image/wood/rns.gif"));
		rps = ImageIO.read(new File("image/wood/rps.gif"));
		rrs = ImageIO.read(new File("image/wood/rrs.gif"));
		
		oo = ImageIO.read(new File("image/wood/oo.gif"));
		oos = ImageIO.read(new File("image/wood/oos.gif"));
		wood = ImageIO.read(new File("image/wood.gif"));
	}
}
