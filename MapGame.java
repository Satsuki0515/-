import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MapGame extends Application {
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.hide();
        StageDB.setMainClass(getClass());
        StageDB.getMainStage().show();

        MediaPlayer mainSound = StageDB.getMainSound();
        if (mainSound == null) {
            // デフォルトのMediaPlayerを作成して設定
            mainSound = new MediaPlayer(new Media(""));
            StageDB.setMainSound(mainSound);
        }

        mainSound.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

