package xyz.narcissu5.music.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import xyz.narcissu5.music.model.Song;

import java.util.Collection;

public class BatchEditorController extends PopupBaseController {

    private Collection<Song> songs;

    @FXML
    TextField artists;

    @FXML
    TextField album;

    @FXML
    CheckBox fromFileName;

    @FXML
    ComboBox<String> inferWay;

    public void setSongs(Collection<Song> songs) {
        this.songs = songs;
    }

    public void close(ActionEvent event) {
        closePop(event);
    }

    public void save(ActionEvent event) {
        String artists = this.artists.getText();
        String album = this.album.getText();
        if ((artists == null || "".equals(artists)) &&
                (album == null || "".equals(album)) &&
                !fromFileName.isSelected()) {
            return;
        }
        for (Song song : songs) {
            if (artists != null && !"".equals(artists)) {
                song.artists.set(artists);
            }
            if (album != null && !"".equals(album)) {
                song.album.set(album);
            }
            if(fromFileName.isSelected()) {
                String filename = song.getFile().getFile().getName();
                filename = filename.substring(0, filename.lastIndexOf('.'));
                String[] tokens = filename.split("-");
                String inferWayStr = inferWay.getSelectionModel().getSelectedItem();
                if("歌手-歌名".equals(inferWayStr)) {
                    song.artists.set(tokens[0]);
                    song.title.set(tokens[1]);
                } else if ("歌名-歌手".equals(inferWayStr)) {
                    song.title.set(tokens[0]);
                    song.artists.set(tokens[1]);
                }
            }
            song.commit();
        }
        closePop(event);
    }
}
