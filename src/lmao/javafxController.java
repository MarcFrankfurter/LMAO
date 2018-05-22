package lmao;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

public class javafxController {

    //fxml
    public Label label;

    //setting up controller
    Controller controller = new Controller();
    Listener listener = new Listener();
    AnimationTimer animationTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            label.setText(controller.frame().hands().get(0).palmPosition().toString());
        }
    };


    public void initialize(){

        //init controller
        controller.addListener(listener);
        animationTimer.start();

    }





}
