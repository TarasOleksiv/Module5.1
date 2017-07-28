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

    private static double x;        // координата Х центру зірки
    private static double y;        // координата Y центру зірки
    private static double radius;   // радіус зірки
    private static String method;   // метод виведення зображення
                                    // 1 - простіший метод: 5 ліній із самоперетинами
                                    // 2 - складніший метод: 10 ліній без самоперетинів

    private static double[] vertexesX = new double[2*POLYGON+1];        // масив координат Х зовнішніх вершин зірки
    private static double[] vertexesY = new double[2*POLYGON+1];        // масив координат Y зовнішніх вершин зірки
    private static double[] vertexes_outX = new double[POLYGON+1];      // масив координат Х внутрішніх вершин зірки
    private static double[] vertexes_outY = new double[POLYGON+1];      // масив координат Y внутрішніх вершин зірки
    private static double[] vertexes_inX = new double[POLYGON];         // масив координат Х зовнішніх + внутрішніх вершин зірки
    private static double[] vertexes_inY = new double[POLYGON];         // масив координат Y зовнішніх + внутрішніх вершин зірки

    private Pane root = new Pane();

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setTitle("Зірочка");

        // отримуєм вхідні дані
        getInput();

        // обираємо метод виводу зображення зірки
        switch (method){
            case "1":
                // 5 ліній із самоперетинами
                method1();
                break;
            default:
                // 10 ліній без самоперетинів
                method2();
                break;
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // 5 ліній з самоперетинами, простіший метод
    private void method1(){
        // Перевіряєм чи зірка поміститься на сцені.
        // Якщо так - генеруєм зірку, якщо ні - генеруєм попередження
        if (checkSize(x,y,radius)) {
            root.getChildren().addAll(generateLines(x,y-radius,radius));
        } else {
            root.getChildren().addAll(generateWarning());
        }
    }

    // 10 ліній без самоперетинів, складніший метод
    private void method2(){
        // Перевіряєм чи зірка поміститься на сцені.
        // Якщо так - генеруєм зірку, якщо ні - генеруєм попередження
        if (checkSize(x,y,radius)) {

            // генеруєм масив зовнішніх вершин (6)[6-а співпадає з 1-ю]
            getVertexesOut(x,y-radius,radius);

            // менший радіус внутрішнього п'ятикутника (тоерема синусів):
            // radius_pentagon = radius * sin(18°))/sin(126°)
            double radius_pentagon = radius * Math.sin(Math.toRadians(SQUARE - CIRCLE/POLYGON))/Math.sin(Math.toRadians(2*SQUARE - CIRCLE/(2*POLYGON) - CIRCLE/(4*POLYGON)));

            // генеруєм масив внутрішніх вершин (5)
            getVertexesIn(x,y,radius_pentagon);

            // об'єднуєм зовнішні + внутрішні вершини через одну в один масив
            getVertexes();

            // сполучаєм всі вершини між собою послідовно і виводим масив ліній
            root.getChildren().addAll(getLines(vertexesX,vertexesY));
        } else {
            root.getChildren().addAll(generateWarning());
        }
    }

    // Генерація великої зірки через масив ліній (всього 5 ліній) - перший метод
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

    // визначаєм координати зовнішніх вершин зірки
    private void getVertexesOut(double x, double y, double radius){
        double deltaX;      // зміщення по горизонталі до наступної вершини :  R * cos(90° + 72°); 72° = 360° / 5
        double deltaY;      // зміщення по вертикалі до наступної вершини :  R * (1 - sin(90° + 72°)); 72° = 360° / 5
        vertexes_outX[0] = x;   // стартова позиція по горизонталі над центром зірки
        vertexes_outY[0] = y;   // стартова позиція по вертикалі над центром зірки

        for (int i = 1; i < vertexes_outX.length; i++){
            deltaX = radius * Math.cos(Math.toRadians(SQUARE + i*CIRCLE/POLYGON));
            deltaY = radius * (1 - Math.sin(Math.toRadians(SQUARE + i*CIRCLE/POLYGON)));
            // координати наступної вершини, остання 6-та має співпадати з 1-ю
            vertexes_outX[i] = x + deltaX;
            vertexes_outY[i] = y + deltaY;
        }
    }

    // визначаєм координати внутрішніх вершин зірки
    private void getVertexesIn(double x, double y, double radius){
        double deltaX;      // зміщення по горизонталі від х до наступної вершини :  R * cos(90° + 36° + i*72°); 72° = 360°/5
        double deltaY;      // зміщення по вертикалі від y до наступної вершини :  R * sin(90° + 36° + i*72°); 72° = 360°/5
        vertexes_inX[0] = x + radius * Math.cos(Math.toRadians(SQUARE + CIRCLE/(2*POLYGON)));   // стартова позиція по горизонталі від центру зірки (зміщення: R * cos(90° + 36°))
        vertexes_inY[0] = y - radius * Math.sin(Math.toRadians(SQUARE + CIRCLE/(2*POLYGON)));   // стартова позиція по вертикалі від центру зірки (зміщення: R * sin(90° + 36°))

        for (int i = 1; i < vertexes_inX.length; i++){
            deltaX = radius * Math.cos(Math.toRadians(SQUARE + CIRCLE/(2*POLYGON) + i*CIRCLE/POLYGON));
            deltaY = radius * Math.sin(Math.toRadians(SQUARE + CIRCLE/(2*POLYGON) + i*CIRCLE/POLYGON));
            // координати наступної вершини
            vertexes_inX[i] = x + deltaX;
            vertexes_inY[i] = y - deltaY;
        }
    }

    // заповнюєм кінцевий масив вершин об'єднуючи зовнішні вершини з внутрішніми
    // зовнішні та внутрішні вершини додаємо по-черзі: раз - зовнішню, раз - внутрішню
    private void getVertexes(){
        int i_out = 0;
        int i_in = 0;
        for (int i = 0; i < vertexesX.length; i++){
            if (i%2 == 0){      // для парних номерів записуєм зовнішні вершини
                vertexesX[i] = vertexes_outX[i/2];
                vertexesY[i] = vertexes_outY[i/2];
            } else {        // для непарних номерів записуєм внутрішні вершини
                vertexesX[i] = vertexes_inX[(i-1)/2];
                vertexesY[i] = vertexes_inY[(i-1)/2];
            }
        }
    }

    // сполучаєм між собою вершини масиву vertexes
    private Line[] getLines(double[] vX, double[] vY){
        Line[] lines = new Line[vX.length];
        for (int i = 0; i < lines.length-1; i++){
            lines[i] = new Line(vX[i], vY[i], vX[i+1], vY[i+1]);
        }
        // останню вершину сполучаємо з першою
        lines[lines.length-1] = new Line(vX[vX.length-1], vY[vX.length-1], vX[0], vY[0]);
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
        System.out.println("Оберіть метод виведення зображення зірки.");
        System.out.println("1 - Метод1: 5 ліній із самоперетинами;     Будь-який інший символ - Метод2: 10 ліній без самоперетинів.");
        System.out.print("Введіть символ для вибору методу: ");;
        method = sc.nextLine();
    }

    // метод для малювання внутрішнього п'ятикутника
    // у фінальній версії не використовується
    // служить лише для тестування
    private Line[] generatePentagon(double x, double y, double radius){
        Line[] lines = new Line[POLYGON];
        double deltaX;      // зміщення по горизонталі від х до наступної вершини :  R * cos(90° + 36° + i*72°); 72° = 360°/5
        double deltaY;      // зміщення по вертикалі від y до наступної вершини :  R * sin(90° + 36° + i*72°); 72° = 360°/5
        double x_old = x + radius * Math.cos(Math.toRadians(SQUARE + CIRCLE/(2*POLYGON)));   // стартова позиція по горизонталі від центру зірки (зміщення: R * cos(90° + 36°))
        double y_old = y - radius * Math.sin(Math.toRadians(SQUARE + CIRCLE/(2*POLYGON)));   // стартова позиція по вертикалі від центру зірки (зміщення: R * sin(90° + 36°))

        System.out.println(x_old + "     " + y_old);

        for (int i = 0; i < lines.length; i++){
            deltaX = radius * Math.cos(Math.toRadians(SQUARE + CIRCLE/(2*POLYGON) + (i+1)*CIRCLE/POLYGON));
            deltaY = radius * Math.sin(Math.toRadians(SQUARE + CIRCLE/(2*POLYGON) + (i+1)*CIRCLE/POLYGON));
            // сполучаєм дві послідовні по циклу вершини
            lines[i] = new Line(x_old,
                    y_old,
                    x + deltaX,
                    y - deltaY);
            x_old = x + deltaX;
            y_old = y - deltaY;
            System.out.println(x_old + "     " + y_old);
        }
        return lines;
    }

}