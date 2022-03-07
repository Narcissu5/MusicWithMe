package xyz.narcissu5.music.model;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.SupportedFileFormat;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Consumer;

public class MainModel {
    public void openDirectory(File path, Consumer<Song> callback)
            throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        String[] files = path.list((dir, name) -> {
            int index = name.lastIndexOf('.');
            if (index > 0) {
                return Arrays.stream(SupportedFileFormat.values())
                        .anyMatch(r -> r.getFilesuffix().equalsIgnoreCase(name.substring(index + 1)));
            } else {
                return false;
            }
        });

        if (files == null) {
            return;
        }

        for (String file : files) {
            Path filePath = Paths.get(path.getAbsolutePath(), file);
            AudioFile audioFile = AudioFileIO.read(filePath.toFile());
            Tag tag = audioFile.getTag();
            Song song = new Song(tag.getFirst(FieldKey.TITLE),
                    tag.getFirst(FieldKey.ALBUM), tag.getFirst(FieldKey.ARTIST), audioFile);
            System.out.printf("%s %s %s %s\n", filePath, song.getTitle(), song.getAlbum(), song.getArtists());
            callback.accept(song);
        }
    }
}
