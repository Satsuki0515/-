import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class MoveChara {

  public static final int TYPE_DOWN = 0;
  public static final int TYPE_LEFT = 1;
  public static final int TYPE_RIGHT = 2;
  public static final int TYPE_UP = 3;

  private final String[] directions = { "Down", "Left", "Right", "Up" };
  private final String[] animationNumbers = { "1", "2", "3" };
  private final String pngPathPre = "png/otaku";
  private final String pngPathSuf = ".png";

  private int posX;
  private int posY;

  private int health;

  private MapData mapData;

  private Image[][] charaImages;
  private ImageView[] charaImageViews;
  private ImageAnimation[] charaImageAnimations;

  private int charaDirection;

  MoveChara(int startX, int startY, MapData mapData) {
    this.mapData = mapData;

    charaImages = new Image[4][3];
    charaImageViews = new ImageView[4];
    charaImageAnimations = new ImageAnimation[4];

    for (int i = 0; i < 4; i++) {
      charaImages[i] = new Image[3];
      for (int j = 0; j < 3; j++) {
        charaImages[i][j] =
          new Image(
            pngPathPre + directions[i] + animationNumbers[j] + pngPathSuf
          );
      }
      charaImageViews[i] = new ImageView(charaImages[i][0]);
      charaImageAnimations[i] =
        new ImageAnimation(charaImageViews[i], charaImages[i]);
    }

    posX = startX;
    posY = startY;

    health = 1;

    setCharaDirection(TYPE_RIGHT); // start with right-direction
  }

  // set the cat's direction
  public void setCharaDirection(int cd) {
    charaDirection = cd;
    for (int i = 0; i < 4; i++) {
      if (i == charaDirection) {
        charaImageAnimations[i].start();
      } else {
        charaImageAnimations[i].stop();
      }
    }
  }

  // check whether the cat can move on
  private boolean isMovable(int dx, int dy) {
    int nextX = posX + dx;
    int nextY = posY + dy;

    if (mapData.getMap(nextX, nextY) == MapData.TYPE_WALL) {
      return false;
    } else if (
      mapData.getMap(nextX, nextY) == MapData.TYPE_SPACE ||
      mapData.getMap(nextX, nextY) == MapData.TYPE_GOAL ||
      mapData.getMap(nextX, nextY) == MapData.TYPE_ITEM ||
      mapData.getMap(nextX, nextY) == MapData.TYPE_SPECIALWALL
    ) {
      return true;
    }
    return false;
  }

  // move the cat
  //changed code
  // move the cat
  public boolean move(int dx, int dy) {
    if (isMovable(dx, dy)) {
      posX += dx;
      posY += dy;
      System.out.println("chara[X,Y]:" + posX + "," + posY);

      // キャラクターとアイテムの接触を判定する
      if (mapData.getMap(posX, posY) == MapData.TYPE_ITEM) {
        System.out.println("Got an item!");
        health++; // 残機を追加
        StageDB.getItemGetSound().play();
        // アイテムをマップ上から削除
        mapData.setMap(posX, posY, MapData.TYPE_SPACE);
        mapData.setImageViews();
      }

      //ボーナスステージに入る
      if (mapData.getMap(posX, posY) == MapData.TYPE_SPECIALWALL) {
        System.out.println("Bonus Stage!!!");
        mapData.createBonusMap();
        // 座標を初期位置に戻す
        posX = 1;
        posY = 1;
        setCharaDirection(TYPE_RIGHT);
      }

      // Check if the character reached the goal
      if (mapData.getMap(posX, posY) == MapData.TYPE_GOAL) {
        System.out.println("Game Clear!!");
        StageDB.getMainStage().hide();
        StageDB.getMainSound().stop();
      }

      return true;
    } else {
      return false;
    }
  }

  // getter: direction of the cat
  public ImageView getCharaImageView() {
    return charaImageViews[charaDirection];
  }

  // getter: x-positon of the cat
  public int getPosX() {
    return posX;
  }

  // getter: y-positon of the cat
  public int getPosY() {
    return posY;
  }

  public int getHealth() {
    return health;
  }

  public void decreaseHealth() {
    health--;
  }

  // Show the cat animation
  private class ImageAnimation extends AnimationTimer {

    private ImageView charaView = null;
    private Image[] charaImages = null;
    private int index = 0;

    private long duration = 500 * 1000000L; // 500[ms]
    private long startTime = 0;

    private long count = 0L;
    private long preCount;
    private boolean isPlus = true;

    public ImageAnimation(ImageView charaView, Image[] images) {
      this.charaView = charaView;
      this.charaImages = images;
      this.index = 0;
    }

    @Override
    public void handle(long now) {
      if (startTime == 0) {
        startTime = now;
      }

      preCount = count;
      count = (now - startTime) / duration;
      if (preCount != count) {
        if (isPlus) {
          index++;
        } else {
          index--;
        }
        if (index < 0 || 2 < index) {
          index = 1;
          isPlus = !isPlus; // true == !false, false == !true
        }
        charaView.setImage(charaImages[index]);
      }
    }
  }
}
