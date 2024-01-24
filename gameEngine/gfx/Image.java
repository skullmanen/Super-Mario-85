package gameEngine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Image
{

    private int w, h; // width height
    private int[] p; // pixels

    public Image(String path)
    {
        BufferedImage image = null;

        try
        {
            image = ImageIO.read(Image.class.getResourceAsStream(path));
        } catch (IOException ex)
        {
            //Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
        }

        try{
	    w = image.getWidth();
	    h = image.getHeight();
	    p = image.getRGB(0, 0, w, h, null, 0, w);
	    image.flush();
	} catch(Exception e){
	    
	}
    }

    public int getW()
    {
        return w;
    }

    public void setW(int w)
    {
        this.w = w;
    }

    public int getH()
    {
        return h;
    }

    public void setH(int h)
    {
        this.h = h;
    }

    public int[] getP()
    {
        return p;
    }

    public void setP(int[] p)
    {
        this.p = p;
    }
    
    public int getRGB(int x, int y) {
	return p[x + y * w];
    }

}
