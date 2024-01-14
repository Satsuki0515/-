import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

public class MapData {
    public static final int TYPE_SPACE = 0;
    public static final int TYPE_WALL = 1;
    public static final int TYPE_ITEM = 2;
    public static final int TYPE_GOAL = 3; // Add a new constant for the goal

    private static final String mapImageFiles[] = {
            "GameImage/Space.png",
            "GameImage/BrickWall.png",
            "GameImage/Heart.png"
    };

    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private int[][] maps;
    private int width; // width of the map
    private int height; // height of the map

    MapData(int x, int y) {
        mapImages = new Image[mapImageFiles.length];
        mapImageViews = new ImageView[y][x];
        for (int i = 0; i < mapImages.length; i ++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }

        width = x;
        height = y;
        maps = new int[y][x];

        recreateNewMap();

        setImageViews();
    }

    public void recreateNewMap() {
         fillMap(MapData.TYPE_WALL);
        digMap(1, 3, new Random());
        placeItem(2, 1);
    }
    // fill two-dimentional arrays with a given number (maps[y][x])
    private void fillMap(int type) {
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                maps[y][x] = type;
            }
        }
    }

    // dig walls for making roads
    private void digMap(int x, int y, Random random) {
        setMap(x, y, MapData.TYPE_SPACE);
        int[][] dl = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
        int[] tmp;

        for (int i = 0; i < dl.length; i ++) {
            int r = random.nextInt(dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i = 0; i < dl.length; i ++) {
            int dx = dl[i][0];
            int dy = dl[i][1];
            if (getMap(x + dx * 2, y + dy * 2) == MapData.TYPE_WALL) {
                setMap(x + dx, y + dy, MapData.TYPE_SPACE);
                digMap(x + dx * 2, y + dy * 2, random);
            }
        }
    }

    
    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }

    public void setMap(int x, int y, int type) {
        if (x < 1 || width <= x - 1 || y < 1 || height <= y - 1) {
            return;
        }
        maps[y][x] = type;
    }

    public ImageView getImageView(int x, int y) {
        return mapImageViews[y][x];
    }

    public void setImageViews() {
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
            }
        }
    }

    // Add a method to set the goal position
    public void setGoal(int x, int y) {
        setMap(x, y, MapData.TYPE_GOAL);
    }

    public void placeItem(int x, int y) {
        setMap(x, y, TYPE_ITEM);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}