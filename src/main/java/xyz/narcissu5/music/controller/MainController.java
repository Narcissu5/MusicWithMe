package xyz.narcissu5.music.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import xyz.narcissu5.music.model.MainModel;
import xyz.narcissu5.music.model.Song;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController {

    ObservableList<Song> songs = FXCollections.observableArrayList();

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    MainModel mainModel = new MainModel();

    @FXML
    GridPane mainPane;

    @FXML
    TextField path;

    @FXML
    TableView<Song> songTbl;

    @FXML
    public void initialize() {
        TableColumn<Song, String> column = new TableColumn<>("歌名");
        column.setCellValueFactory(new PropertyValueFactory<>("title"));
        column.setCellFactory(TextFieldTableCell.<Song>forTableColumn());
        songTbl.getColumns().add(column);
        column = new TableColumn<>("专辑");
        column.setCellValueFactory(new PropertyValueFactory<>("album"));
        column.setCellFactory(TextFieldTableCell.<Song>forTableColumn());
        songTbl.getColumns().add(column);
        column = new TableColumn<>("歌手");
        column.setCellValueFactory(new PropertyValueFactory<>("artists"));
        column.setCellFactory(TextFieldTableCell.<Song>forTableColumn());
        songTbl.getColumns().add(column);

//        songTbl.setItems(songs);
    }

    @FXML
    public void start(ActionEvent actionEvent) {

    }

    @FXML
    public void selectDirectory(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择下载文件存放的文件夹");
        File file = chooser.showDialog(mainPane.getScene().getWindow());
        path.setText(file.getAbsolutePath());
    }

    @FXML
    public void openDirectory(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        File directory = dc.showDialog(((Node) event.getTarget()).getScene().getWindow());
        executorService.submit(() -> {
            try {
                mainModel.openDirectory(directory, s -> Platform.runLater(() -> songTbl.getItems().add(s)));
            } catch (CannotReadException|TagException|InvalidAudioFrameException|ReadOnlyFileException|IOException e) {
                e.printStackTrace();
            }
        });
    }
}
