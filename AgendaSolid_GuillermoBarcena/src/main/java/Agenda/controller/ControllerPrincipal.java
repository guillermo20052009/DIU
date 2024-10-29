package Agenda.controller;

import Agenda.Main;
import javafx.fxml.FXML;

public class ControllerPrincipal {
    Main main;
    @FXML
    private void handleShowBirthdayStatistics() {
        main.showBirthdayStatistics();
    }
    public void setMainApp(Main main){
        this.main = main;
    }
}
