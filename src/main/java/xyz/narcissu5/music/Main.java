package xyz.narcissu5.music;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
        primaryStage.setTitle("音乐管理器");
        primaryStage.setScene(new Scene(root, 400.0, 300.0));
        primaryStage.show();
    }

    public static void main(String... args) {
        Main.launch(args);
    }

}