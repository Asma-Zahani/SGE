package com.sge;

import com.sge.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SGEApplication extends Application {
    @Override
    public void start(Stage stage) {
        MainController mainController = new MainController();
        Scene scene = new Scene(mainController.getView(), 900, 700);

        stage.setScene(scene);
        stage.setTitle("Système de Gestion Université");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}