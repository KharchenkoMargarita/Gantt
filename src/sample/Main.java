package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        primaryStage.setTitle("Gantt Chart");
        primaryStage.setScene(new Scene(root, 293, 400));
        primaryStage.show();

        stage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
