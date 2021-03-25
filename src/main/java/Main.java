import controller.GameWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sprites.Player;

public class Main extends Application {

    public static int WIDTH = 600;
    public static int HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("SpaceGame");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("GameWindow.fxml"));
        Parent root = fxmlLoader.load();

        GameWindow gameWindow = fxmlLoader.getController();

        Scene scene = new Scene(root);


        gameWindow.setScene(scene);
        gameWindow.setStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
