package com.miskevich;

import com.miskevich.io.ILogAnalyzer;
import com.miskevich.io.LogAnalyzer;
import com.miskevich.locator.ServiceLocator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application{

    public static void main(String[] args) throws Exception {
        ILogAnalyzer logAnalyzer = new LogAnalyzer();
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        serviceLocator.register(ILogAnalyzer.class, logAnalyzer);
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/index.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("LogAnalyzer shows LogTokens for you");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
