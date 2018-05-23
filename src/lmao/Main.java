package lmao;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Menue.fxml"));
        primaryStage.setTitle("LMAO");
        primaryStage.setScene(new Scene(root));

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        // fx shit
        primaryStage.setMaximized(true);

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
