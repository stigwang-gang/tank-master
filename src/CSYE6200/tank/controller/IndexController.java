package CSYE6200.tank.controller;

import CSYE6200.tank.Director;
import CSYE6200.tank.util.SoundEffect;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class IndexController {

    @FXML
    private ImageView startGame;
    @FXML
    private ImageView startGame1;
    @FXML
    private ImageView startGame2;

    @FXML
    void mouseClickedStartGame1(MouseEvent event) {
        SoundEffect.play("/sound/done.wav");
        Director.getInstance().gameStart(1);
    }

    @FXML
    void mouseEnteredStartGame(MouseEvent event) {
        startGame.setOpacity(0.8);
        SoundEffect.play("/sound/button.wav");
    }

    @FXML
    void mouseExitedStartGame(MouseEvent event) {
        startGame.setOpacity(1);
    }

    public void mouseClickedStartGame2(MouseEvent mouseEvent) {
        Director.getInstance().gameStart(2);
    }

    public void mouseClickedStartGame3(MouseEvent mouseEvent) {
        Director.getInstance().gameStart(3);
    }

    public void mouseEnteredStartGame2(MouseEvent mouseEvent) {
        startGame1.setOpacity(0.8);
        SoundEffect.play("/sound/button.wav");
    }

    public void mouseExitedStartGame2(MouseEvent mouseEvent) {
        startGame1.setOpacity(1);
    }

    public void mouseEnteredStartGame3(MouseEvent mouseEvent) {
        startGame2.setOpacity(0.8);
        SoundEffect.play("/sound/button.wav");
    }

    public void mouseExitedStartGame3(MouseEvent mouseEvent) {
        startGame2.setOpacity(1);
    }
}
