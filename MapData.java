import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData {

  public static final int TYPE_SPACE = 0;
  public static final int TYPE_WALL = 1;
  public static final int TYPE_ITEM = 2;
  public static final int TYPE_GOAL = 3;
  public static final int TYPE_SPECIALWALL = 4;
  public static final int TYPE_BONUSWALL = 5;

  private static final String mapImageFiles[] = {
    "GameImage/Space.png",
    "GameImage/BrickWall.png",
    "GameImage/Heart.png",
    "png/goal.png",
    "GameImage/BrickWallForSpecial.png",
    "GameImage/MossyStoneWall.png"
  };

  private Image[] mapImages;
  private ImageView[][] mapImageViews;
  private int[][] maps;
  private int width; // width of the map
  private int height; // height of the map

  MapData(int x, int y) {
    mapImages = new Image[mapImageFiles.length];
    mapImageViews = new ImageView[y][x];
    for (int i = 0; i < mapImages.length; i++) {
      mapImages[i] = new Image(mapImageFiles[i]);
    }

    width = x;
    height = y;
    maps = new int[y][x];

    recreateNewMap();
  }

  // 通常ステージの作成
  public void recreateNewMap() {
    fillMap(MapData.TYPE_WALL);
    digMap(1, 3, new Random());

    // アイテムを2，3個おく
    Random rand = new Random();
    int itemNum = rand.nextInt(2) + 2;
    placeItem(itemNum);
    int WallNum = 1;
    placeWall(WallNum);

    setImageViews();
  }

  // ボーナスステージの作成
  public void createBonusMap() {
    fillMap(TYPE_BONUSWALL);
    digMap(1, 3, new Random());

    // アイテムを5,6個おく
    Random rand = new Random();
    int itemNum = rand.nextInt(2) + 5;
    placeItem(itemNum);

    setGoal(19, 13);
    setImageViews();
  }

  private static MapData mapData;
  private static MoveChara chara;
  private static MapGameController MapGameController;

  // fill two-dimentional arrays with a given number (maps[y][x])
  private void fillMap(int type) {
    for (int y = 0; y < height; y++) {
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

    for (int i = 0; i < dl.length; i++) {
      int r = random.nextInt(dl.length);
      tmp = dl[i];
      dl[i] = dl[r];
      dl[r] = tmp;
    }

    for (int i = 0; i < dl.length; i++) {
      int dx = dl[i][0];
      int dy = dl[i][1];
      int dm = getMap(x + dx * 2, y + dy * 2);
      if (dm == MapData.TYPE_WALL || dm == MapData.TYPE_BONUSWALL) {
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
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (maps[y][x] == TYPE_GOAL) continue; // ゴールの描画処理は他と少し違う処理をしているため、飛ばす
        mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
      }
    }
  }

  // Add a method to set the goal position
  public void setGoal(int x, int y) {
    setMap(x, y, MapData.TYPE_GOAL);
  }

  public void placeItem(int itemNum) {
    Random rand = new Random();
    int counter = 0;
    while (counter < itemNum) {
      int rx = rand.nextInt(width - 2) + 1;
      int ry = rand.nextInt(height - 2) + 1;
      // マップ内のいずれかのSPACEをITEMに置き換える
      if (maps[ry][rx] != TYPE_SPACE || (rx == 1 && ry == 1)) continue;
      setMap(rx, ry, TYPE_ITEM);
      counter++;
    }
  }

  public void placeWall(int WallNum) {
    Random rand2 = new Random();
    int counter2 = 0;
    while (counter2 < WallNum) {
      int rx2 = rand2.nextInt(width - 2) + 1;
      int ry2 = rand2.nextInt(height - 2) + 1;

      if (maps[ry2][rx2] != TYPE_WALL || (rx2 == 1 && ry2 == 1)) continue;
      setMap(rx2, ry2, TYPE_SPECIALWALL);
      counter2++;
    }
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }
}
