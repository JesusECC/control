import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GridMap extends JFrame {

	BufferedImage theMap;
	int imwidth,imheight;
	double scale;
	
	
	public GridMap(int width, int height, double mpp){
		//this.imwidth = imwidth;
		//this.imheight = imheight;
		//this.scale = scale;
		imwidth=(int)(width/mpp);
		imheight=(int)(height/mpp);
		scale=mpp;
		theMap=new BufferedImage(imwidth, imheight, BufferedImage.TYPE_INT_ARGB);
		//scalas grices
		int midgray=(0xff<<24)|(180<<16)|(180<<8)|(180);
		
		for (int x = 0; x < imwidth; x++) {
			for (int y = 0; y < imheight; y++) {
				theMap.setRGB(x, y, midgray);				
			}
		}
		
		MapPanel mp=new MapPanel();
		add(mp);
	}

	void setVal(double x,double y,int value) {
		if (value<0 || value>255) {
			return;
		}
		int imx=(int)(x/scale+imwidth/2);
		int imy=(int)(imheight/2-y/scale);
		int rgbval=(0xff<<24)|(value<<16)|(value<<8)|(value);
		
		if (imx>=0 && imx<imwidth && imy>=0 && imy<imheight) {
			theMap.setRGB(imx, imy, rgbval);
			
		}
	}
	int getVal(double x,double y) {
		int value=180;
		
		int imx=(int)(x/scale+imwidth/2);
		int imy=(int)(imheight/2-y/scale);
		int rgbval=0;
		
		if (imx>=0 && imx<imwidth && imy>=0 && imy<imheight) {
			rgbval=theMap.getRGB(imx, imy);
		}
		//			
		value=(((rgbval>>16)& 0x00ff)+((rgbval>>8)& 0x0000ff)+(rgbval&0x000000ff))/3;
		return value;
	}
	class MapPanel extends JPanel{
		
		protected void paintComponent(Graphics g) {
			g.drawImage(theMap, 0,0,null);
			
		}
		
		public Dimension getPreferredSize() {
			return new Dimension(imwidth,imheight);
		}
		
	}
}
