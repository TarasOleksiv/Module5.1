package ua.goit.java8.module51.task2;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * Created by t.oleksiv on 01/08/2017.
 */
public class DrawMethod5Lines {
    private Pane root;
    double x;
    double y;
    double radius;

    public DrawMethod5Lines(double x, double y, double radius, Pane root){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.root = root;
        method1();
    }

    // 5 ліній з самоперетинами, простіший метод
    private void method1(){
        // Перевіряєм чи зірка поміститься на сцені.
        // Якщо так - генеруєм зірку, якщо ні - генеруєм попередження
        if (checkSize(x,y,radius)) {
            root.getChildren().addAll(generateLines(x,y-radius,radius));
        } else {
            Warning warning = new Warning(root);
            root.getChildren().addAll(warning.generateWarning());
        }
    }

    private boolean checkSize(double x, double y, double radius){
        return ((x - radius > Main.MARGIN) && (x + radius < Main.WIDTH - Main.MARGIN) && (y - radius > Main.MARGIN) && (y + radius < Main.HEIGHT - Main.MARGIN));
    }

    // Генерація великої зірки через масив ліній (всього 5 ліній) - перший метод
    private Line[] generateLines(double x, double y, double radius){
        Line[] lines = new Line[Main.POLYGON];
        double deltaX;      // зміщення по горизонталі до наступної вершини (через одну по колу, тому множник 2):  R * cos(90° + 2 * 72°); 72° = 360° / 5
        double deltaY;      // зміщення по вертикалі до наступної вершини (через одну, тому множник 2):  R * (1 - sin(90° + 2 * 72°)); 72° = 360° / 5
        double x_old = x;   // стартова позиція по горизонталі над центром зірки
        double y_old = y;   // стартова позиція по вертикалі над центром зірки
        for (int i = 0; i < lines.length; i++){
            deltaX = radius * Math.cos(Math.toRadians(Main.SQUARE + (i+1)*2*Main.CIRCLE/Main.POLYGON));
            deltaY = radius * (1 - Math.sin(Math.toRadians(Main.SQUARE + (i+1)*2*Main.CIRCLE/Main.POLYGON)));
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
}
