package xyz.narcissu5.music.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import xyz.narcissu5.music.model.Song;

public class EditorController {

    Stage stage;

    public final SimpleStringProperty file = new SimpleStringProperty();

    @FXML
    TextField title;

    @FXML
    TextField artists;

    @FXML
    TextField album;

    @FXML
    TextField filename;

    @FXML
    public void init() {
        filename.textProperty().bindBidirectional(this.file);
    }

    private Song song;

    public Song getSong() {
        return song;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSong(Song song) {
        this.song = song;
        title.textProperty().bindBidirectional(song.title);
        artists.textProperty().bindBidirectional(song.artists);
        album.textProperty().bindBidirectional(song.album);
        file.set(song.getFile().getFile().toString());
        filename.textProperty().bindBidirectional(file);
    }

    public void save(ActionEvent event) {
        song.commit();
        stage.close();
    }

    public void close(ActionEvent event) {
        stage.close();
    }
}
