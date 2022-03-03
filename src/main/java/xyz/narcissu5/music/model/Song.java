package xyz.narcissu5.music.model;

public class Song {

    private String title;

    private String album;

    private String artists;

    public Song() {
    }

    public Song(String title, String album, String artists) {
        this.title = title;
        this.album = album;
        this.artists = artists;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }
}
