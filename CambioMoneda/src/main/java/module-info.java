module com.example.cambiomoneda {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires AccesoBBDDMoneda;
    requires java.sql;

    opens conversorPropio to javafx.fxml;
    exports conversorPropio.controller;
    exports conversorPropio.modelo;
    opens conversorPropio.controller to javafx.fxml;
    exports conversorPropio;
}