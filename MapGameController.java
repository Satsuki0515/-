import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MapGameController implements Initializable {

  public MapData mapData;
  public MoveChara chara;
  public GridPane mapGrid;
  public ImageView[] mapImageViews;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeMap();
    }

  // Draw the map
  //changed code
  public void drawMap(MoveChara c, MapData m) {
    int cx = c.getPosX();
    int cy = c.getPosY();
    mapGrid.getChildren().clear();
    loadImageView();

    // 描画処理
    for (int y = 0; y < mapData.getHeight(); y++) {
      for (int x = 0; x < mapData.getWidth(); x++) {
        int index = y * mapData.getWidth() + x;
        if (x == cx && y == cy) {
          mapGrid.add(c.getCharaImageView(), x, y);

        } else if (m.getMap(x, y) == MapData.TYPE_GOAL) {

          // ゴールの描画処理
          if (x == cx && y == cy) {
            Label gameClearLabel = new Label("Game Clear!");
            mapGrid.add(gameClearLabel, x, y);
          } else {
            Image goalImage = new Image("png/goal.png");
            ImageView goalImageView = new ImageView(goalImage);
            mapGrid.add(goalImageView, x, y);
          }
          
        } else {
          mapGrid.add(mapImageViews[index], x, y);
        }
      }
    }
  }

  // Get users' key actions
  public void keyAction(KeyEvent event) {
    KeyCode key = event.getCode();
    System.out.println("keycode:" + key);
    if (key == KeyCode.A) {
      leftButtonAction();
    } else if (key == KeyCode.S) {
      downButtonAction();
    } else if (key == KeyCode.W) {
      upButtonAction();
    } else if (key == KeyCode.D) {
      rightButtonAction();
    }
  }

  // Operations for going the cat up
  public void upButtonAction() {
    printAction("UP");
    chara.setCharaDirection(MoveChara.TYPE_UP);
    chara.move(0, -1);
    drawMap(chara, mapData);
  }

  // Operations for going the cat down
  public void downButtonAction() {
    printAction("DOWN");
    chara.setCharaDirection(MoveChara.TYPE_DOWN);
    chara.move(0, 1);
    drawMap(chara, mapData);
  }

  // Operations for going the cat right
  public void leftButtonAction() {
    printAction("LEFT");
    chara.setCharaDirection(MoveChara.TYPE_LEFT);
    chara.move(-1, 0);
    drawMap(chara, mapData);
  }

  // Operations for going the cat right
  public void rightButtonAction() {
    printAction("RIGHT");
    chara.setCharaDirection(MoveChara.TYPE_RIGHT);
    chara.move(1, 0);
    drawMap(chara, mapData);
  }

    @FXML
    public void func1ButtonAction(ActionEvent event) {
        try {
            System.out.println("func1");
            StageDB.getMainStage().hide();
            StageDB.getMainSound().stop();
            StageDB.getGameOverStage().show();
            StageDB.getGameOverSound().play();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    public void func2ButtonAction(ActionEvent event) {
        try {
            initializeMap();
            System.out.println("func2: Recreate new map");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        
    }
    private void initializeMap() {
        mapData = new MapData(21, 15);
        chara = new MoveChara(1, 1, mapData);
        // Set the goal position (adjust x and y values accordingly)
        mapData.setGoal(19, 13);
        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        drawMap(chara, mapData);
    }

    public void loadImageView() {
      for (int y = 0; y < mapData.getHeight(); y++) {
        for (int x = 0; x < mapData.getWidth(); x++) {
            int index = y * mapData.getWidth() + x;
            mapImageViews[index] = mapData.getImageView(x, y);
        }
      }
    }

    @FXML
    public void func3ButtonAction(ActionEvent event) {
        System.out.println("func3: Nothing to do");
    }

  @FXML
  public void func4ButtonAction(ActionEvent event) {
    System.out.println("func4: Nothing to do");
  }

  // Print actions of user inputs
  public void printAction(String actionString) {
    System.out.println("Action: " + actionString);
  }
}
