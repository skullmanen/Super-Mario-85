
package gameEngine.gfx;

public class ImageTile extends Image {
    private int tileW, tileH;
    private int tileAmountX, tileAmountY;

    public ImageTile(String path, int width, int height) {
        super(path);
        this.tileW = width;
        this.tileH = height;

        tileAmountX = super.getW() / width;
        tileAmountY = super.getH() / height;
    }

    public int getTileW() {
        return tileW;
    }

    public void setTileW(int tileW) {
        this.tileW = tileW;
    }

    public int getTileH() {
        return tileH;
    }

    public void setTileH(int tileH) {
        this.tileH = tileH;
    }

    public int getTileAmountX() {
        return tileAmountX;
    }

    public int getTileAmountY() {
        return tileAmountY;
    }

}
