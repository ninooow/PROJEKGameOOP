package tile;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager extends Tile{
    private GamePanel gp;
    private Tile[] tile;
    private int[][][] mapTileNum;
    private int nLayer = 12;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[256];
        mapTileNum = new int[nLayer][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            InputStream is = getClass().getResourceAsStream("/res/tiles/Frame 3.png");
            if (is == null) {
                throw new IOException("Resource not found: /res/tiles/Frame 3.png");
            }
            BufferedImage image = ImageIO.read(is);

            int width = image.getWidth();
            int height = image.getHeight();
            int tileSize = width / 20;
            int tileX = width / tileSize;
            int tileY = height / tileSize;
            tile = new Tile[tileX * tileY];

            int n = 0;
            for (int h = 0; h < height; h += tileSize) {
                for (int w = 0; w < width; w += tileSize) {
                    BufferedImage subimg = image.getSubimage(w, h, tileSize, tileSize);
                    tile[n] = new Tile();
                    tile[n].image = subimg;
                    if (n == 124 || n == 63 || n == 64 || n==65 || n==66) {
                        tile[n].collision = true;
                    }
                    n++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            for (int index = 0; index < nLayer; index++) {
                String filePath = String.format("/res/maps/_Tile Layer %d.csv", index + 1);
                BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filePath)));
                int col = 0;
                int row = 0;
                while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                    String line = br.readLine();
                    String[] numbers = line.split(",");
                    while (col < gp.maxWorldCol) {
                        int num = Integer.parseInt(numbers[col]);
                        if (num == -1) {
                            num = 19;
                        }
                        mapTileNum[index][col][row] = num;
                        col++;
                    }
                    if (col == gp.maxWorldCol) {
                        col = 0;
                        row++;
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        int topLeftX = (int) (gp.player.worldX - gp.player.screenX);
        int topLeftY = (int) (gp.player.worldY - gp.player.screenY);

        topLeftX = Math.max(0, Math.min(topLeftX, gp.maxWorldCol * gp.tileSize - gp.screenWidth));
        topLeftY = Math.max(0, Math.min(topLeftY, gp.maxWorldRow * gp.tileSize - gp.screenHeight));

        for (int layer = 0; layer < nLayer-1; layer++) {
            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {
                for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
                    int tileNum = mapTileNum[layer][worldCol][worldRow];

                    int screenX = worldCol * gp.tileSize - topLeftX;
                    int screenY = worldRow * gp.tileSize - topLeftY;

                    if (screenX + gp.tileSize > 0 && screenX < gp.screenWidth &&
                            screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {
                        g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                    }
                }
            }
        }
    }

    public boolean isCollisionTile(int col, int row) {
//        for (int layer = 0; layer < nLayer; layer++) {//nino ngubah
            int tileNum = mapTileNum[nLayer-1][col][row];
            if (tile[tileNum].collision) {
                return true;
            }
//        }
        return false;
    }
//    public boolean isNPC(int col, int row){//nino nambah buat ngecek npc apa ga
//        int tileNum = mapTileNum[nLayer-1][col][row];
//        if (tileNum == 63 || tileNum == 64 || tileNum == 65 || tileNum == 66) {
//            return true;
//        }return false;
//    }
    public int numNPC(int col, int row){
        int tileNum = mapTileNum[nLayer-1][col][row];
        if (tileNum == 63) {
            return 63;
        }if (tileNum == 64) {
            return 64;
        }if (tileNum == 65) {
            return 65;
        }if (tileNum == 66) {
            return 66;
        }return 0;
    }
}