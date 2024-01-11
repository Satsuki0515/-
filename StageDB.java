import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.Scene;  // 追加
import java.io.File;  // 追加
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.IOException;

public class StageDB {
    static private Stage mainStage = null;
    static private Stage gameOverStage = null;
    static private MediaPlayer mainSound = null;
    static private MediaPlayer gameOverSound = null;
    static private Class mainClass;
    static private final String mainSoundFileName = "sound/JoyToTheWorld.mp3";

    public static void setMainClass(Class mainClass) {
        StageDB.mainClass = mainClass;
    }

    public static MediaPlayer getMainSound() {
        if (mainSound == null) {
            try {
                Media m = new Media(new File(mainSoundFileName).toURI().toString());
                MediaPlayer mp = new MediaPlayer(m);
                mp.setCycleCount(MediaPlayer.INDEFINITE);
                mp.setRate(1.0);
                mp.setVolume(0.5);
                mainSound = mp;
            } catch (Exception io) {
                System.err.print(io.getMessage());
                mainSound = new MediaPlayer(new Media(""));
            }
        }
        return mainSound;
    }

    public static void setMainSound(MediaPlayer mediaPlayer) {
        mainSound = mediaPlayer;
    }

    public static MediaPlayer getGameOverSound() {
        if (gameOverSound == null) {
            try {
                // 例外が発生した場合もgameOverSoundを初期化
                gameOverSound = new MediaPlayer(new Media(""));
            } catch (Exception io) {
                System.err.print(io.getMessage());
                gameOverSound = new MediaPlayer(new Media(""));
            }
        }
        return gameOverSound;
    }

    public static Stage getMainStage() {
        if (mainStage == null) {
            try {
                FXMLLoader loader = new FXMLLoader(mainClass.getResource("MapGame.fxml"));
                VBox root = loader.load();
                Scene scene = new Scene(root);
                mainStage = new Stage();
                mainStage.setScene(scene);
            } catch (IOException ioe) {
                System.err.println(ioe);
            }
        }
        return mainStage;
    }

    public static Stage getGameOverStage() {
        if (gameOverStage == null) {
            try {
                System.out.println("StageDB:getGameOverStage()");
                FXMLLoader loader = new FXMLLoader(mainClass.getResource("MapGameOver.fxml"));
                VBox root = loader.load();
                Scene scene = new Scene(root);
                gameOverStage = new Stage();
                gameOverStage.setScene(scene);
            } catch (IOException ioe) {
                System.err.println(ioe);
            }
        }
        return gameOverStage;
    }
}
