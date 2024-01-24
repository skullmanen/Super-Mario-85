
package gameEngine;


import gameEngine.gfx.Font;
import gameEngine.gfx.Image;
import gameEngine.gfx.ImageTile;
import java.awt.image.DataBufferInt;


public class Renderer
{
    private final Font font = Font.STANDARD;
    
    private final int pW, pH; // pixelWidth, pixelHeight
    private int[] p; // pixels

    private int camX, camY;

    public Renderer (GameContainer gc)
    {
	pW = gc.getWidth();
	pH = gc.getHeight();
	p = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear ()
    {
	for ( int i = 0; i < p.length; i++ )
	{
	    p[i] = 0;
	}
    }

    public void setPixel (int x, int y, int value)
    {
	int alpha = ( (value >> 24) & 0xff );
	
	if ( (x < 0 || x >= pW || y < 0 || y >= pH) || alpha == 0 )
	{
	    return;
	}

	p[x + y * pW] = value;
    }

    public void drawText (String text, int offX, int offY, int color)
    {
	offX -= camX;
	offY -= camY;
	
	text = text.toUpperCase();
	int offset = 0;

	for ( int i = 0; i < text.length(); i++ )
	{
	    int unicode = text.codePointAt(i) - 32;

	    for ( int y = 0; y < font.getFontImage().getH(); y++ )
	    {
		for ( int x = 0; x < font.getWidths()[unicode]; x++ )
		{
		    if ( font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getW()] == 0xffffffff )
		    {
			setPixel(x + offX + offset, y + offY, color);
		    }
		}
	    }

	    offset += font.getWidths()[unicode];
	}
    }

    public void drawImage (Image image, int offX, int offY)
    {
	offX -= camX;
	offY -= camY;
	
	//Don't render image
	if ( offX < -image.getW() )
	    return;
	if ( offY < -image.getH() )
	    return;
	if ( offX >= pW )
	    return;
	if ( offY >= pH )
	    return;

	int newX = 0;
	int newY = 0;
	int newWidth = image.getW();
	int newHeight = image.getH();

	//Clipp image
	if ( offY < 0 )
	    newY -= offY;
	if ( offX < 0 )
	    newX -= offX;
	
	if ( newWidth + offX > pW )
	    newWidth -= (newWidth + offX - pW);
	if ( newHeight + offY > pW )
	    newHeight -= (newHeight + offY  - pW);

	//Render image
	for ( int y = newY; y < newHeight; y++ )
	{
	    for ( int x = newX; x < newWidth; x++ )
	    {
		setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
	    }
	}
    }

    public void drawImageTile (ImageTile image, int offX, int offY, int tileX, int tileY)
    {
	offX -= camX;
	offY -= camY;
	
	//Don't render image
	if ( offX < -image.getTileW() )
	    return;
	if ( offY < -image.getTileH() )
	    return;
	if ( offX >= pW )
	    return;
	if ( offY >= pH )
	    return;

	int newX = 0;
	int newY = 0;
	int newWidth = image.getTileW();
	int newHeight = image.getTileH();

	//Clipp image
	if ( offY < 0 )
	    newY -= offY;
	if ( offX < 0 )
	    newX -= offX;
	
	
	if ( newWidth + offX > pW )
	    newWidth -= (newWidth + offX - pW);
	if ( newHeight + offY > pW )
	    newHeight -= (newHeight + offY - pW);

	//Render image
	for ( int y = newY; y < newHeight; y++ )
	{
	    for ( int x = newX; x < newWidth; x++ )
	    {
		setPixel(x + offX, y + offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
	    }
	}
    }

    public void drawRect (int offX, int offY, int width, int height, int color)
    {
	
	offX -= camX;
	offY -= camY;
	
	for ( int y = 0; y < height; y++ )
	{
	    setPixel(offX, y + offY, color);
	    setPixel(offX + width, y + offY, color);
	}

	for ( int x = 0; x < width; x++ )
	{
	    setPixel(x + offX, offY, color);
	    setPixel(x + offX, offY + height, color);
	}
    }

    public void drawFillRect (int offX, int offY, int width, int height, int color)
    {
	offX -= camX;
	offY -= camY;
	
	for ( int y = 0; y < height; y++ )
	{
	    for ( int x = 0; x < width; x++ )
	    {
		setPixel(x + offX, y + offY, color);
	    }
	}
    }

    public String getPixelAt (int x, int y)
    {
	return Integer.toHexString(p[x + y * pW]);
    }

    public int getCamX ()
    {
	return camX;
    }

    public void setCamX (int camX)
    {
	this.camX = camX;
    }

    public int getCamY ()
    {
	return camY;
    }

    public void setCamY (int camY)
    {
	this.camY = camY;
    }
    
    
}
