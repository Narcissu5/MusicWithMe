package xyz.narcissu5.music.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public abstract class PopupBaseController {
    protected void closePop(ActionEvent event) {
        if (event.getSource() instanceof Node node && node.getScene().getWindow() instanceof Stage stage) {
            stage.close();
        }
    }
}
