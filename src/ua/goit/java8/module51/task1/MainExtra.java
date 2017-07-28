package ua.goit.java8.module51.task1;

/**
 * Created by t.oleksiv on 26/07/2017.
 */
import javafx.application.Application;
import javafx.stage.Stage;

public class MainExtra extends Application {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 825;
    public static final int MARGIN = 100;   //нижнє та верхнє поле
    public static final int EYES_NOSE = 3;  //кількість кіл для очей та носа


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