package gestionhotel.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebViewController {
    @FXML
    private WebView webView;

    @FXML
    public void initialize() {
        WebEngine webEngine = webView.getEngine();
        webView.getEngine().load("https://www.riu.com/es/hotel/espana/cadiz-chiclana/clubhotel-riu-chiclana/"); // Cambia la URL según necesites

    }
}
