package ua.goit.java8.module51.task1;

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

/**
 * Created by Taras on 27.07.2017.
 */
public class CircleScene {
    private static int count;
    private static int min_radius;
    private static int max_radius;
    private Circle[] circles;
    private Label warningLabel;
    private Pane root = new Pane();

    public CircleScene(Stage primaryStage) {
        graphicInterface();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    // метод для генерації кіл
    private void generateCircles(int count, int min_radius, int max_radius) {
        // викликаємо метод для первірки чи помістяться кола на картинці
        if (checkCount(count,max_radius)) {
            Random random = new Random();
            circles = new Circle[count + Main.EYES_NOSE];
            int radius;
            int radius_old = Main.MARGIN;
            int x;
            int y = Main.HEIGHT;
            int count_i = 0;
            for (int i = 0; i < circles.length - Main.EYES_NOSE; i++) {
                radius = random.nextInt(max_radius - min_radius) + min_radius;      // радіус рендомний
                y -= radius + radius_old;       // нова координата y гарантує нам дотикання нового кола до старого
                radius_old = radius;

                circles[i] = new Circle(
                        (int) Main.WIDTH / 2,   // координата х - фіксована по центру
                        y,
                        radius,
                        Paint.valueOf(getColor().toString()));
                count_i = i;    // поточна кількість кіл (потрібна для того, щоб виловити останній згенерований в цьому,циклі який буде головою)
            }

            // генеруєм останні 3 кола (очі та ніс) всередині голови (останнього кола згенерованого в попередньому циклі)
            for (int i = 0; i < Main.EYES_NOSE; i++) {
                circles[i + circles.length - Main.EYES_NOSE] = generateEyesNose(circles[count_i])[i];
            }
            root.getChildren().addAll(circles);
        } else {
            // якщо не кола не вміщаються, малюємо попередження
            generateWarning();
        }

    }

    // метод для перевірки чи вказана кількість кіл з можливим максимальним радіусом поміститься на картинці
    private boolean checkCount(int count, int max_radius){
        return 2 * max_radius * count < (Main.HEIGHT - Main.MARGIN);
    }

    // генерація очей та носа всередині останнього кола
    // всі очі та ніс мають однаковий фіксований радіус, який обчислюється на основі радіусу голови
    // розташування центру очей та носа фіксоване по відношенню до центру голови
    private Circle[] generateEyesNose(Circle circle){
        final int DIM = 3;
        int x_head = (int)circle.getCenterX();
        int y_head = (int)circle.getCenterY();
        int radius_head = (int)circle.getRadius();

        // радіус очей та носа
        int radius = (int)radius_head/4;

        Circle[] circles_head = new Circle[DIM];

        // ліве око - зміщення вліво та вгору від центра голови на фіксовану відстань
        circles_head[0] = new Circle(
                x_head - 2*radius,
                y_head - 2*radius,
                radius,
                Paint.valueOf(getColor().toString()));

        // праве око - зміщення вправо та вгору від центра голови на фіксовану відстань
        circles_head[1] = new Circle(
                x_head + 2*radius,
                y_head - 2*radius,
                radius,
                Paint.valueOf(getColor().toString()));

        // ніс - розташування точно в центрі голови
        circles_head[2] = new Circle(
                x_head,
                y_head,
                radius,
                Paint.valueOf(getColor().toString()));
        return circles_head;
    }

    private Color getColor(){
        Random random = new Random();
        Color color = Color.color(random.nextDouble(),
                random.nextDouble(),
                random.nextDouble(),
                0.6f);
        return color;
    }

    // метод для генерації попередження
    private void generateWarning(){
        warningLabel = new Label("Надто велика кількість кругів. Не помістяться!");
        warningLabel.setLayoutX(Main.WIDTH/6);
        warningLabel.setLayoutY(Main.HEIGHT/3);
        warningLabel.setFont(new Font("Arial", 30));
        root.getChildren().addAll(warningLabel);
    }

    // основний метод виведення зображення на сцені
    private void graphicInterface() {

        Label countFieldLabel = new Label("Кількість кругів: ");
        countFieldLabel.setTranslateX(10);
        countFieldLabel.setTranslateY(10);

        TextField countField = new TextField();
        countField.setTranslateX(140);
        countField.setTranslateY(10);
        countField.setPrefWidth(50);
        countField.setText("10");

        Label minRadiusLabel = new Label("Мінімальний радіус: ");
        minRadiusLabel.setTranslateX(10);
        minRadiusLabel.setTranslateY(40);

        TextField minRadius = new TextField();
        minRadius.setTranslateX(140);
        minRadius.setTranslateY(40);
        minRadius.setPrefWidth(50);
        minRadius.setText("20");

        Label maxRadiusLabel = new Label("Максимальний радіус: ");
        maxRadiusLabel.setTranslateX(10);
        maxRadiusLabel.setTranslateY(70);

        TextField maxRadius = new TextField();
        maxRadius.setTranslateX(140);
        maxRadius.setTranslateY(70);
        maxRadius.setPrefWidth(50);
        maxRadius.setText("30");

        Button generateButton = new Button();
        generateButton.setTranslateX(10);
        generateButton.setTranslateY(110);
        generateButton.setText("Згенерувати");
        generateButton.setOnMouseClicked(event -> {
            int count = Integer.parseInt(countField.getText());
            int min_radius = Integer.parseInt(minRadius.getText());
            int max_radius = Integer.parseInt(maxRadius.getText());
            clearWarnings();
            clearCircles();
            generateCircles(count, min_radius, max_radius);
        });

        Button redPaintAll = new Button();
        redPaintAll.setTranslateX(10);
        redPaintAll.setTranslateY(140);
        redPaintAll.setText("Замалювати червоним");
        redPaintAll.setOnMouseClicked(event -> {
            paintAll(Color.color(1, 0, 0));
        });

        Button gradientPaintAll = new Button();
        gradientPaintAll.setTranslateX(10);
        gradientPaintAll.setTranslateY(170);
        gradientPaintAll.setText("Градієнт чорного");
        gradientPaintAll.setOnMouseClicked(event -> {
            paintAllGradient(Color.color(0, 0, 0));
        });

        root.getChildren().addAll(countField, minRadius, maxRadius, generateButton, redPaintAll, gradientPaintAll);
        root.getChildren().addAll(countFieldLabel, minRadiusLabel, maxRadiusLabel);
    }

    // метод для стирання всіх кіл
    private void clearCircles() {
        if (circles != null && circles.length > 0) {
            root.getChildren().removeAll(circles);
        }
    }

    // метод для стирання попередження
    private void clearWarnings(){
        if (warningLabel != null){
            root.getChildren().removeAll(warningLabel);
        }
    }

    // метод для замальовки всіх кіл вказаним кольором
    private void paintAll(Color color) {
        if (circles == null) return;

        for (int i = 0; i < circles.length; i++)
            circles[i].setFill(Paint.valueOf(color.toString()));
    }

    // метод для замальовки всіх кіл градієнтом заданого кольору
    private void paintAllGradient(Color color) {
        if (circles == null) return;
        double red = color.getRed();
        double green = color.getGreen();
        double blue = color.getBlue();
        double step;    // крок градієнта

        for (int i = 0; i < circles.length; i++) {
            step = (double)i / circles.length;     // крок градієнта обчислюєм пропорційно до кількості кіл
            circles[i].setFill(Paint.valueOf(Color.color(red + step, green + step, blue + step).toString()));
        }
    }
}
