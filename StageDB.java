import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

class StageDB {

    static private Timeline timeline = null;
    static private Stage mainStage = null;
    static private Stage gameOverStage = null;
    static private MediaPlayer mainSound = null;
    static private MediaPlayer gameOverSound = null;
    static private ArrayList<MediaPlayer> itemGetSound = new ArrayList<>();
    static private Class mainClass;
    static private final String mainSoundFileName = "sound/JoyToTheWorld.mp3"; // BGM by OtoLogic
    static private final String gameOverSoundFileName = "sound/DropsByWindow.mp3"; // please set proper music for gameover
    static private final String itemGetSoundFileName = "sound/magical.mp3";

    public static void setMainClass(Class mainClass) {
        StageDB.mainClass = mainClass;
    }

    public static Timeline getTimeline() {
        return timeline;
    }

    public static Timeline setTimeline(KeyFrame key) {
        timeline = new Timeline(key);
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    public static MediaPlayer getItemGetSound() {
        try {
            Media m = new Media(new File(itemGetSoundFileName).toURI().toString());
            MediaPlayer mp = new MediaPlayer(m);
            // mp.setCycleCount(MediaPlayer.INDEFINITE);
            mp.setRate(1.0);
            mp.setVolume(0.5);
            itemGetSound.add(mp);
            return mp;
        } catch (Exception io) {
            System.err.println(io.getMessage());
            return null;
        }
    }

    public static MediaPlayer getMainSound() {
        if (mainSound == null) {
            try {
                Media m = new Media(new File(mainSoundFileName).toURI().toString());
                MediaPlayer mp = new MediaPlayer(m);
                mp.setCycleCount(MediaPlayer.INDEFINITE); // loop play
                mp.setRate(1.0); // 1.0 = normal speed
                mp.setVolume(0.5); // volume from 0.0 to 1.0
                mainSound = mp;
            } catch (Exception io) {
                System.err.print(io.getMessage());
            }
        }
        return mainSound;
    }

    public static MediaPlayer getGameOverSound() {
        if (gameOverSound == null) {
            try {
                // set gameover sound
                Media m = new Media(new File(gameOverSoundFileName).toURI().toString());
                MediaPlayer mp = new MediaPlayer(m);
                mp.setCycleCount(MediaPlayer.INDEFINITE);
                mp.setRate(1.0);
                mp.setVolume(1.0);
                gameOverSound = mp;
            } catch (Exception io) {
                System.err.print(io.getMessage());
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