package com.miskevich.controller;

import com.miskevich.io.ILogAnalyzer;
import com.miskevich.io.LogToken;
import com.miskevich.locator.ServiceLocator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private TextField hhFrom;
    @FXML
    private TextField mmFrom;
    @FXML
    private TextField ssFrom;
    @FXML
    private DatePicker dateFrom;
    @FXML
    private TextField hhTo;
    @FXML
    private TextField mmTo;
    @FXML
    private TextField ssTo;
    @FXML
    private DatePicker dateTo;
    @FXML
    private TableView<LogToken> tokenTableView;
    @FXML
    private TableColumn<LogToken, LocalDateTime> time;
    @FXML
    private TableColumn<LogToken, LogToken.HttpMethod> method;
    @FXML
    private TableColumn<LogToken, String> message;

    private String pathToLogFileFromUser;

    private ILogAnalyzer logAnalyzer;

    public Controller() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        logAnalyzer = serviceLocator.get(ILogAnalyzer.class);
    }

    public void showLogTokens() {
        LocalDateTime timeFrom = getLocalDateTimeFrom();
        LocalDateTime timeTo = getLocalDateTimeTo();

        Collection<LogToken> tokenCollection = logAnalyzer.scanLog(pathToLogFileFromUser, timeFrom, timeTo);
        ObservableList<LogToken> obs = FXCollections.observableArrayList();
        obs.addAll(tokenCollection);
        tokenTableView.setItems(obs);
    }

    private LocalDateTime getLocalDateTimeTo() {
        int yearTo = dateTo.getValue().getYear();
        Month monthTo = dateTo.getValue().getMonth();
        int dayOfMonthTo = dateTo.getValue().getDayOfMonth();
        int hhValueTo = Integer.parseInt(hhTo.getText());
        int mmValueTo = Integer.parseInt(mmTo.getText());
        int ssValueTo = Integer.parseInt(ssTo.getText());
        return LocalDateTime.of(yearTo, monthTo, dayOfMonthTo, hhValueTo, mmValueTo, ssValueTo);
    }

    private LocalDateTime getLocalDateTimeFrom() {
        int hhValueFrom = Integer.parseInt(hhFrom.getText());
        int mmValueFrom = Integer.parseInt(mmFrom.getText());
        int ssValueFrom = Integer.parseInt(ssFrom.getText());

        int yearFrom = dateFrom.getValue().getYear();
        Month monthFrom = dateFrom.getValue().getMonth();
        int dayOfMonthFrom = dateFrom.getValue().getDayOfMonth();
        return LocalDateTime.of(yearFrom, monthFrom, dayOfMonthFrom, hhValueFrom, mmValueFrom, ssValueFrom);
    }

    public void selectLogFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files", "*.txt")
        );
        Node node = (Node) event.getSource();
        File file = fileChooser.showOpenDialog(node.getScene().getWindow());
        if(file != null){
            pathToLogFileFromUser = String.valueOf(file);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        method.setCellValueFactory(new PropertyValueFactory<>("method"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
    }
}
