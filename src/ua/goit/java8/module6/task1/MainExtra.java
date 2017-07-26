package ua.goit.java8.module6.task1;

/**
 * Created by t.oleksiv on 26/07/2017.
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Scanner;

public class MainExtra extends Application {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 825;
    public static final int MARGIN = 100;
    public static final int EYES_NOSE = 3;


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setTitle("Сніговик");

        CircleScene circleScene = new CircleScene(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}