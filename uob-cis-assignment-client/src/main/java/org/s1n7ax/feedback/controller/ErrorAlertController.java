package org.s1n7ax.feedback.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.s1n7ax.feedback.ui.Common;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class ErrorAlertController {
	
	private final Logger logger = LogManager.getLogger(ErrorAlertController.class);
	private final String message;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblMessage;

    @FXML
    private Button btnOk;
    
    public ErrorAlertController(String message) {
    	this.message = message;
	}

    @FXML
    void btnOkClicked(MouseEvent event) {
    	Common.getStage(event).close();
    }

    @FXML
    void initialize() {
    	logger.info("initializing");
    	lblMessage.setText(message);
    }
}