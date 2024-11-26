module com.example.conversorexamen {
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
    requires AccesoBBDDExamenV2;
    requires java.sql;

    opens com.example.conversorexamen to javafx.fxml;
    opens com.example.conversorexamen.controller;
    exports com.example.conversorexamen;
    exports com.example.conversorexamen.controller;
}