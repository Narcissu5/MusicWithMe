package xyz.narcissu5.music.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;

import java.util.function.Consumer;

public class Song {

    public final SimpleStringProperty title = new SimpleStringProperty();

    public final SimpleStringProperty album = new SimpleStringProperty();

    public final SimpleStringProperty artists = new SimpleStringProperty();

    private AudioFile file;

    private Consumer<Song> writer;

    public Song() {
    }

    public Song(String title, String album, String artists, AudioFile file) {
        this.title.set(title);
        this.album.set(album);
        this.artists.set(artists);
        this.file = file;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        try {
            file.getTag().setField(FieldKey.TITLE, title);
        } catch (FieldDataInvalidException e) {
            e.printStackTrace();
        }
        this.title.set(title);
    }

    public String getAlbum() {
        return album.get();
    }

    public void setAlbum(String album) {
        try {
            file.getTag().setField(FieldKey.ALBUM, album);
        } catch (FieldDataInvalidException e) {
            e.printStackTrace();
        }
        this.album.set(album);
    }

    public String getArtists() {
        return artists.get();
    }

    public void setArtists(String artists) {
        try {
            file.getTag().setField(FieldKey.ARTIST, artists);
        } catch (FieldDataInvalidException e) {
            e.printStackTrace();
        }
        this.artists.set(artists);
    }

    public AudioFile getFile() {
        return file;
    }

    public void commit() {
        try {
            file.getTag().setField(FieldKey.TITLE, title.get());
            file.getTag().setField(FieldKey.ALBUM, album.get());
            file.getTag().setField(FieldKey.ARTIST, artists.get());
            file.commit();
        } catch (FieldDataInvalidException e) {
            e.printStackTrace();
        } catch (CannotWriteException e) {
            e.printStackTrace();
        }
    }
}
