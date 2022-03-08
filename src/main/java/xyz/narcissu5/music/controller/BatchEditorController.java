package xyz.narcissu5.music.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import xyz.narcissu5.music.model.Song;

import java.util.Collection;

public class BatchEditorController extends PopupBaseController {

    private Collection<Song> songs;

    @FXML
    TextField artists;

    @FXML
    TextField album;

    public void setSongs(Collection<Song> songs) {
        this.songs = songs;
    }

    public void close(ActionEvent event) {
        closePop(event);
    }

    public void save(ActionEvent event) {
        String artists = this.artists.getText();
        String album = this.album.getText();
        if ((artists == null || "".equals(artists)) && (album == null || "".equals(album))) {
            return;
        }
        for (Song song : songs) {
            if (artists != null && !"".equals(artists)) {
                song.artists.set(artists);
            }
            if(album!= null && !"".equals(album)) {
                song.album.set(album);
            }
            song.commit();
        }
        closePop(event);
    }
}
