import controller.GameWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sprites.Player;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("SpaceGame");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("GameWindow.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        GameWindow gameWindow = fxmlLoader.getController();
        gameWindow.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
