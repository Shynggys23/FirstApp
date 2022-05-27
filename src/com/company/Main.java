package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        System.out.println("Launching Application");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        System.out.println("Application starts");
        Parent rootPane = FXMLLoader.load(getClass().getResource("viewCal.fxml"));
        rootPane.getStylesheets().add(getClass().getResource("calculate.css").toExternalForm());
        Scene scene = new Scene(rootPane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("CalculatorWiWa");
        stage.getIcons().add(new Image("com/company/icon.png"));
        stage.centerOnScreen();
        stage.show();

    }

}

