package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.val;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        val loader = new FXMLLoader(
                Main.class.getResource("/SearchController.fxml"));
        val page = loader.load();
        val scene = new Scene((AnchorPane) page);

        primaryStage.setTitle("Title goes here");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
