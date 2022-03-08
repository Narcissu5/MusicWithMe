package xyz.narcissu5.music.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.FieldKey;

public class Song {

    public SimpleStringProperty title = new SimpleStringProperty();

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty album = new SimpleStringProperty();

    public SimpleStringProperty albumProperty() {
        return album;
    }

    public SimpleStringProperty artists = new SimpleStringProperty();

    public SimpleStringProperty artistsProperty() {
        return artists;
    }

    private AudioFile file;

    public Song() {
        this.title.addListener(new Listener(FieldKey.TITLE));
        this.album.addListener(new Listener(FieldKey.ALBUM));
        this.artists.addListener(new Listener(FieldKey.ARTIST));
    }

    public Song(String title, String album, String artists, AudioFile file) {
        this();
        this.title.set(title);
        this.album.set(album);
        this.artists.set(artists);
        this.file = file;
    }

    private class Listener implements ChangeListener<String> {

        private final FieldKey fieldKey;

        public Listener(FieldKey fieldKey) {
            this.fieldKey = fieldKey;
        }

        @Override
        public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
            try {
                if (file != null) {
                    file.getTag().setField(fieldKey, newValue);
                }
            } catch (FieldDataInvalidException e) {
                e.printStackTrace();
            }
        }
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
