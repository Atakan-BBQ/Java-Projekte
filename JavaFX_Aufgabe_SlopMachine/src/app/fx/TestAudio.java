package app.fx;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class TestAudio extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
        File mediaFile = new File("src/IconMatch.wav");
        Media media = new Media(mediaFile.toURI().toURL().toString());

        MediaPlayer mediaPlayer = new MediaPlayer(media);


        Scene scene = new Scene(new Pane(), 1024, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        mediaPlayer.play();

    }

}
