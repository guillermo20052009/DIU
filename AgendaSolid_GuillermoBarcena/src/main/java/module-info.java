module com.example.agendasolid_guillermobarcena {
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
    requires java.sql;
    requires AccesoBBDDMoneda;
    exports Agenda;
    exports Agenda.modelo.repository.impl;
    exports Agenda.controller;
    exports Agenda.modelo;

    opens Agenda to javafx.fxml;
    opens Agenda.modelo to javafx.fxml;
    opens Agenda.controller to javafx.fxml;
}