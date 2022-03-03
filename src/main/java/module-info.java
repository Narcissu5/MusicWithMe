module musicwithme {
    requires javafx.fxml;
    requires javafx.controls;
    requires jaudiotagger;

    exports xyz.narcissu5.music;
    opens xyz.narcissu5.music to javafx.fxml;
    exports xyz.narcissu5.music.controller;
    opens xyz.narcissu5.music.controller to javafx.fxml;
    exports xyz.narcissu5.music.model;
    opens xyz.narcissu5.music.model to javafx.fxml;
}