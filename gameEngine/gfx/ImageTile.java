
package gameEngine.gfx;

public class ImageTile extends Image {
    private int tileW, tileH;
    private int tileAmountX, tileAmountY;

    public ImageTile(String path, int tileW, int tileH) {
        super(path);
        this.tileW = tileW;
        this.tileH = tileH;

        tileAmountX = super.getW() / tileW;
        tileAmountY = super.getH() / tileH;
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
