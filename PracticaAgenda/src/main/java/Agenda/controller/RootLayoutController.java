package Agenda.controller;

import javafx.fxml.FXML;

public class RootLayoutController {
    Agenda agenda=new Agenda();
    @FXML
    private void handleShowBirthdayStatistics() {
        agenda.showBirthdayStatistics();
    }
}
