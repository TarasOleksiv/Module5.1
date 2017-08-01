package ua.goit.java8.module51.task2;

/**
 * Created by t.oleksiv on 26/07/2017.
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Scanner;

public class Main extends Application {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 825;
    public static final int MARGIN = 100;   // розмір поля

    public static final double CIRCLE = 360;    // повний кут в градусах
    public static final double SQUARE = 90;     // прямий кут в градусах
    public static final int POLYGON = 5;        // кількість сторін многокутника

    private double x;        // координата Х центру зірки
    private double y;        // координата Y центру зірки
    private double radius;   // радіус зірки
    private String method;   // метод виведення зображення
                                    // 1 - простіший метод: 5 ліній із самоперетинами
                                    // 2 - складніший метод: 10 ліній без самоперетинів

    private Pane root = new Pane();

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setTitle("Зірочка");

        // отримуєм вхідні дані
        Input input = new Input();
        x = input.getX();
        y = input.getY();
        radius = input.getRadius();
        method = input.getMethod();

        // обираємо метод виводу зображення зірки
        switch (method){
            case "1":
                // 5 ліній із самоперетинами
                DrawMethod5Lines drawMethod5Lines = new DrawMethod5Lines(x,y,radius,root);
                break;
            default:
                // 10 ліній без самоперетинів
                DrawMethod10Lines drawMethod10Lines = new DrawMethod10Lines(x,y,radius,root);
                break;
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}