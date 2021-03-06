package xyz.narcissu5.music.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import xyz.narcissu5.music.Main;
import xyz.narcissu5.music.model.MainModel;
import xyz.narcissu5.music.model.Song;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainController {

    ExecutorService executorService = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r, "back-service");
        thread.setDaemon(true);
        return thread;
    });

    MainModel mainModel = new MainModel();

    EditorController editorCtl = new EditorController();

    BatchEditorController batchEditorCtl = new BatchEditorController();

    @FXML
    GridPane mainPane;

    @FXML
    TextField path;

    @FXML
    TableView<Song> songTbl;

    Stage editorWindow;

    Stage batchEditorWindow;

    @FXML
    public void initialize() {
        songTbl.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        songTbl.setRowFactory(tv -> {
            TableRow<Song> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Song song = row.getItem();
                    openEditor(song);
                }
            });
            return row;
        });
    }

    @FXML
    public void selectDirectory(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("????????????????????????????????????");
        File file = chooser.showDialog(mainPane.getScene().getWindow());
        path.setText(file.getAbsolutePath());
    }

    @FXML
    public void onEditClick(ActionEvent event) {
        Song song = songTbl.getSelectionModel().getSelectedItem();
        openEditor(song);
    }

    private void openEditor(Song song) {
        Stage stage = getEditorWindow(song);
        stage.show();
    }

    private Stage getEditorWindow(Song song) {
        if(editorWindow == null) {
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("Editor.fxml"));
                loader.setController(editorCtl);
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            editorWindow = new Stage();
            editorWindow.setTitle("???????????????");
            editorWindow.setScene(new Scene(root));
            editorWindow.initModality(Modality.WINDOW_MODAL);
        }
        editorCtl.setSong(song);
        return editorWindow;
    }

    @FXML
    public void openDirectory(ActionEvent event) {
        DirectoryChooser dc = new DirectoryChooser();
        File directory = dc.showDialog(((Node) event.getTarget()).getScene().getWindow());
        executorService.submit(() -> {
            try {
                mainModel.openDirectory(directory, s -> Platform.runLater(() -> songTbl.getItems().add(s)));
            } catch (CannotReadException | TagException | InvalidAudioFrameException | ReadOnlyFileException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void openBatchEditor(ActionEvent actionEvent) {
        if(!songTbl.getSelectionModel().isEmpty()) {
            getBatchEditorWindow(songTbl.getSelectionModel().getSelectedItems()).show();
        }
    }

    private Stage getBatchEditorWindow(Collection<Song> songs) {
        if(batchEditorWindow == null) {
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("BatchEditor.fxml"));
                loader.setController(batchEditorCtl);
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            batchEditorWindow = new Stage();
            batchEditorWindow.setTitle("????????????");
            batchEditorWindow.setScene(new Scene(root));
            batchEditorWindow.initModality(Modality.WINDOW_MODAL);
        }
        batchEditorCtl.setSongs(songs);
        return batchEditorWindow;
    }
}
