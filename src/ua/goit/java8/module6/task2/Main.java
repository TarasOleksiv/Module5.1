package ua.goit.java8.module6.task2;

/**
 * Created by t.oleksiv on 26/07/2017.
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

    private static double x;
    private static double y;
    private static double radius;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);

        primaryStage.setTitle("Зірочка");
        Pane root = new Pane();
        getInput();

        // Перевіряєм чи зірка поміститься на сцені.
        // Якщо так - генеруєм зірку, якщо ні - генеруєм попередження

        if (checkSize(x,y,radius)) {
            root.getChildren().addAll(generateLines(x,y-radius,radius));
            double radius_pentagon = radius * Math.tan(Math.toRadians(SQUARE - CIRCLE/POLYGON));
            //System.out.println(radius_pentagon);
            //System.out.println(y+radius_pentagon);
            root.getChildren().addAll(generatePentagon(x,y,radius_pentagon));
        } else {
            root.getChildren().addAll(generateWarning());
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Генерація великої зірки через масив ліній
    private Line[] generateLines(double x, double y, double radius){
        Line[] lines = new Line[POLYGON];
        double deltaX;      // зміщення по горизонталі до наступної вершини (через одну по колу, тому множник 2):  R * cos(90° + 2 * 72°); 72° = 360° / 5
        double deltaY;      // зміщення по вертикалі до наступної вершини (через одну, тому множник 2):  R * (1 - sin(90° + 2 * 72°)); 72° = 360° / 5
        double x_old = x;   // стартова позиція по горизонталі над центром зірки
        double y_old = y;   // стартова позиція по вертикалі над центром зірки
        for (int i = 0; i < lines.length; i++){
            deltaX = radius * Math.cos(Math.toRadians(SQUARE + (i+1)*2*CIRCLE/POLYGON));
            deltaY = radius * (1 - Math.sin(Math.toRadians(SQUARE + (i+1)*2*CIRCLE/POLYGON)));
            // сполучаєм дві послідовні по циклу вершини (через одну по колу)
            lines[i] = new Line(x_old,
                    y_old,
                    x + deltaX,
                    y + deltaY);
            x_old = x + deltaX;
            y_old = y + deltaY;
        }
        return lines;
    }

    // Генерація малого п'ятикутника через масив ліній
    private Line[] generatePentagonTest(double x, double y, double radius){
        Color color = Color.color(1,
                0,
                0);
        Line[] lines = new Line[POLYGON];
        double deltaX;      // зміщення по горизонталі до наступної вершини :  R * cos(90° + 72°); 72° = 360° / 5
        double deltaY;      // зміщення по вертикалі до наступної вершини :  R * (1 - sin(90° + 72°)); 72° = 360° / 5
        double x_old = x;   // стартова позиція по горизонталі під центром зірки
        double y_old = y;   // стартова позиція по вертикалі під центром зірки
        //System.out.println("i = -1 ; x: " + x_old + " ; y: " + y_old);
        for (int i = 0; i < lines.length; i++){
            deltaX = radius * Math.cos(Math.toRadians(3*SQUARE + (i+1)*CIRCLE/POLYGON));
            deltaY = radius * (1 - Math.sin(Math.toRadians(3*SQUARE + (i+1)*CIRCLE/POLYGON)));
            // сполучаєм дві послідовні по циклу вершини
            lines[i] = new Line(x_old,
                    y_old,
                    x + deltaX,
                    y + deltaY);
            lines[i].setFill(Paint.valueOf(color.toString()));
            x_old = x + deltaX;
            y_old = y + deltaY;
            //System.out.println("i: " + i + " ; x: " + x_old + " ; y: " + y_old);
        }
        return lines;
    }

    private Line[] generatePentagon(double x, double y, double radius){
        Color color = Color.color(1,
                0,
                0);
        Line[] lines = new Line[POLYGON];
        double deltaX;      // зміщення по горизонталі до наступної вершини :  R * cos(90° + 72°); 72° = 360° / 5
        double deltaY;      // зміщення по вертикалі до наступної вершини :  R * (1 - sin(90° + 72°)); 72° = 360° / 5
        double x_old = x + radius * Math.cos(Math.toRadians(SQUARE + 1/2 * (CIRCLE/POLYGON)));   // стартова позиція по горизонталі під центром зірки
        double y_old = y + radius * (1 - Math.sin(Math.toRadians(SQUARE + 1/2 * (CIRCLE/POLYGON))));   // стартова позиція по вертикалі під центром зірки
        //System.out.println("i = -1 ; x: " + x_old + " ; y: " + y_old);
        for (int i = 0; i < lines.length; i++){
            deltaX = radius * Math.cos(Math.toRadians(SQUARE + (2*(i+1)+1)/2 * (CIRCLE/POLYGON)));
            deltaY = radius * (1 - Math.sin(Math.toRadians(SQUARE + (2*(i+1)+1)/2 * (CIRCLE/POLYGON))));
            // сполучаєм дві послідовні по циклу вершини
            lines[i] = new Line(x_old,
                    y_old,
                    x + deltaX,
                    y + deltaY);
            lines[i].setFill(Paint.valueOf(color.toString()));
            x_old = x + deltaX;
            y_old = y + deltaY;
            //System.out.println("i: " + i + " ; x: " + x_old + " ; y: " + y_old);
        }
        return lines;
    }

    // Перевірка чи зірка поміститься на сцені
    private boolean checkSize(double x, double y, double radius){
        return ((x - radius > MARGIN) && (x + radius < WIDTH - MARGIN) && (y - radius > MARGIN) && (y + radius < HEIGHT - MARGIN));
    }

    // Генерація попередження
    private Label generateWarning(){
        Label warningLabel = new Label("Зірка не вміщається на картинці!");
        warningLabel.setLayoutX(WIDTH/6);
        warningLabel.setLayoutY(HEIGHT/3);
        warningLabel.setFont(new Font("Arial", 30));
        return warningLabel;
    }

    // Отримуєм дані з консолі
    private void getInput(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Введіть координату Х центру зірки: ");
        x = sc.nextInt();
        sc.nextLine();
        System.out.print("Введіть координату Y центру зірки: ");
        y = sc.nextInt();
        sc.nextLine();
        System.out.print("Введіть радіус зірки: ");
        radius = sc.nextInt();
        sc.nextLine();
    }
}