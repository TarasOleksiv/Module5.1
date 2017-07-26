package ua.goit.java8.module6.task1;

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

    private void generateCircles(int count, int min_radius, int max_radius) {

        if (checkCount(count,max_radius)) {
            //    root.getChildren().addAll(generateCircles(count, min_radius,max_radius));
            //} else {
            //    root.getChildren().addAll(generateWarning());
            //}

            Random random = new Random();
            circles = new Circle[count + Main.EYES_NOSE];
            int radius;
            int radius_old = Main.MARGIN;
            int x;
            int y = Main.HEIGHT;
            int count_i = 0;
            for (int i = 0; i < circles.length - Main.EYES_NOSE; i++) {
                radius = random.nextInt(max_radius - min_radius) + min_radius;
                y -= radius + radius_old;
                radius_old = radius;

                circles[i] = new Circle(
                        (int) Main.WIDTH / 2,
                        y,
                        radius,
                        Paint.valueOf(getColor().toString()));
                count_i = i;
            }
            for (int i = 0; i < Main.EYES_NOSE; i++) {
                circles[i + circles.length - Main.EYES_NOSE] = generateEyesNose(circles[count_i])[i];
            }
            root.getChildren().addAll(circles);
        } else {
            generateWarning();
        }

    }

    private boolean checkCount(int count, int max_radius){
        return 2 * max_radius * count < (Main.HEIGHT - Main.MARGIN);
    }

    private Circle[] generateEyesNose(Circle circle){
        Random random = new Random();
        final int DIM = 3;
        int x_head = (int)circle.getCenterX();
        int y_head = (int)circle.getCenterY();
        int radius_head = (int)circle.getRadius();

        int radius = (int)radius_head/4;

        Circle[] circles_head = new Circle[DIM];

        // ліве око
        circles_head[0] = new Circle(
                x_head - 2*radius,
                y_head - 2*radius,
                radius,
                Paint.valueOf(getColor().toString()));

        // праве око
        circles_head[1] = new Circle(
                x_head + 2*radius,
                y_head - 2*radius,
                radius,
                Paint.valueOf(getColor().toString()));

        // ніс
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

    private void generateWarning(){
        warningLabel = new Label("Надто велика кількість кругів. Не помістяться!");
        warningLabel.setLayoutX(Main.WIDTH/6);
        warningLabel.setLayoutY(Main.HEIGHT/3);
        warningLabel.setFont(new Font("Arial", 30));
        root.getChildren().addAll(warningLabel);
    }

    private void graphicInterface() {
        TextField countField = new TextField();
        countField.setTranslateX(10);
        countField.setTranslateY(10);
        countField.setText("50");

        TextField minRadius = new TextField();
        minRadius.setTranslateX(10);
        minRadius.setTranslateY(40);
        minRadius.setText("20");

        TextField maxRadius = new TextField();
        maxRadius.setTranslateX(10);
        maxRadius.setTranslateY(70);
        maxRadius.setText("30");

        Button generateButton = new Button();
        generateButton.setTranslateX(10);
        generateButton.setTranslateY(100);
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
        redPaintAll.setTranslateY(130);
        redPaintAll.setText("Замалювати червоним");
        redPaintAll.setOnMouseClicked(event -> {
            paintAll(Color.color(1, 0, 0));
        });

        root.getChildren().addAll(countField, minRadius, maxRadius, generateButton, redPaintAll);
    }

    private void clearCircles() {
        if (circles != null && circles.length > 0) {
            root.getChildren().removeAll(circles);
        }
    }

    private void clearWarnings(){
        if (warningLabel != null){
            root.getChildren().removeAll(warningLabel);
        }
    }

    private void paintAll(Color color) {
        if (circles == null) return;

        for (int i = 0; i < circles.length; i++)
            circles[i].setFill(Paint.valueOf(color.toString()));
    }
}
