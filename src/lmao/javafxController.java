package lmao;

import com.leapmotion.leap.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.stage.Screen;
import jm.music.data.Note;
import jm.util.Play;

import javax.print.DocFlavor;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class javafxController {

    //fxml
    @FXML
    Button line1;

    @FXML
    Button line2;

    @FXML
    Button line3;

    @FXML
    Button line4;

    @FXML
    Button line5;

    @FXML
    Button line6;

    @FXML
    Button line7;
    // public Label label;

    double x;
    double y;
    int appWidth;
    int appHeight;
    boolean B = false;

    InteractionBox iBox;
    Pointable pointable;
    Vector leapPoint;
    Vector normalizedPoint;

    float appX;
    float appY;

    int eighth;

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


            iBox = controller.frame().interactionBox();
            pointable = controller.frame().pointables().frontmost();

            leapPoint = pointable.stabilizedTipPosition();
            normalizedPoint = iBox.normalizePoint(leapPoint, true);

            appX = normalizedPoint.getX() * appWidth;
            appY = (1 - normalizedPoint.getY()) * appHeight;

            robot.mouseMove((int) appX, (int) appY);

            for (Gesture g : controller.frame().gestures()) {
                if (g.type() == Gesture.Type.TYPE_SCREEN_TAP) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }
            }

            if (appHeight - appY < 100) {

                //plonkSound.play();
                if (!B) {
                    if (appX < eighth) {
                        Play.midi(new Note(60, 1));
                    } else if (appX >= eighth && appX < 2 * eighth) {
                        Play.midi(new Note(62, 1));
                    } else if (appX >= 2 * eighth && appX < 3 * eighth) {
                        Play.midi(new Note(64, 1));
                    } else if (appX >= 3 * eighth && appX < 4 * eighth) {
                        Play.midi(new Note(65, 1));
                    } else if (appX >= 4 * eighth && appX < 5 * eighth) {
                        Play.midi(new Note(67, 1));
                    } else if (appX >= 5 * eighth && appX < 6 * eighth) {
                        Play.midi(new Note(69, 1));
                    } else if (appX >= 6 * eighth && appX < 7 * eighth) {
                        Play.midi(new Note(71, 1));
                    } else if (appX >= 7 * eighth && appX < appWidth) {
                        Play.midi(new Note(72, 1));
                    }
                    B = true;
                }
            } else {
                B = false;
            }
        }
    };


    public javafxController() throws AWTException {
    }


    public void initialize() {

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        appWidth = (int) bounds.getWidth();
        appHeight = (int) bounds.getHeight();
        //init controller
        controller.addListener(listener);
        animationTimer.start();

        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);

        eighth = appWidth/8;
        line1.setLayoutX(eighth);
        line2.setLayoutX(2 * eighth);
        line3.setLayoutX(3 * eighth);
        line4.setLayoutX(4 * eighth);
        line5.setLayoutX(5 * eighth);
        line6.setLayoutX(6 * eighth);
        line7.setLayoutX(7 * eighth);

    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
