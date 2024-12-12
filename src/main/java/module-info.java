module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.Levels to javafx.fxml;
    opens com.example.demo.Actor to javafx.fxml;
    opens com.example.demo.Plane to javafx.fxml;
    opens com.example.demo.Projectiles to javafx.fxml;
    opens com.example.demo.Boss to javafx.fxml;
    opens com.example.demo.UI to javafx.fxml;
    opens com.example.demo.LevelController to javafx.fxml;
    opens com.example.demo.MusicController to javafx.fxml;
}