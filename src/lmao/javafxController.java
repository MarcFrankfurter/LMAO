package lmao;

import com.leapmotion.leap.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.stage.Screen;

import javax.print.DocFlavor;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class javafxController {

    //fxml
   // public Label label;

    double x;
    double y;
    int appWidth;
    int appHeight;

    Robot robot = new Robot();

    AudioClip plonkSound = new AudioClip(Paths.get("src/lmao/bomb.mp3").toUri().toString());

    //setting up controller
    Controller controller = new Controller();
    Listener listener = new Listener();
    AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
//            label.setText(controller.frame().hands().get(0).fingers().get(0).stabilizedTipPosition().toString());
            x = controller.frame().hands().get(0).fingers().get(0).stabilizedTipPosition().getX();
            y = controller.frame().hands().get(0).fingers().get(0).stabilizedTipPosition().getY();


            InteractionBox iBox = controller.frame().interactionBox();
            Pointable pointable = controller.frame().pointables().frontmost();

            Vector leapPoint = pointable.stabilizedTipPosition();
            Vector normalizedPoint = iBox.normalizePoint(leapPoint, true);

            float appX = normalizedPoint.getX() * appWidth;
            float appY = (1 - normalizedPoint.getY()) * appHeight;

            robot.mouseMove((int) appX, (int) appY);

            for(Gesture g:controller.frame().gestures()){
                if(g.type() == Gesture.Type.TYPE_SCREEN_TAP){
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }
            }

            if(appHeight - appY < 100){
                plonkSound.play();
            }
        }
    };

    public javafxController() throws AWTException {
    }


    public void initialize(){

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        appWidth = (int) bounds.getWidth();
        appHeight = (int) bounds.getHeight();
        //init controller
        controller.addListener(listener);
        animationTimer.start();

        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);

    }


    public void start(ActionEvent actionEvent) {
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
