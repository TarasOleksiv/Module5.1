package ua.goit.java8.module51.task2;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * Created by t.oleksiv on 01/08/2017.
 */
public class DrawMethod10Lines {
    private double[] vertexesX = new double[2*Main.POLYGON+1];        // масив координат Х зовнішніх вершин зірки
    private double[] vertexesY = new double[2*Main.POLYGON+1];        // масив координат Y зовнішніх вершин зірки
    private double[] vertexes_outX = new double[Main.POLYGON+1];      // масив координат Х внутрішніх вершин зірки
    private double[] vertexes_outY = new double[Main.POLYGON+1];      // масив координат Y внутрішніх вершин зірки
    private double[] vertexes_inX = new double[Main.POLYGON];         // масив координат Х зовнішніх + внутрішніх вершин зірки
    private double[] vertexes_inY = new double[Main.POLYGON];         // масив координат Y зовнішніх + внутрішніх вершин зірки
    private Pane root;
    double x;
    double y;
    double radius;

    public DrawMethod10Lines(double x, double y, double radius, Pane root){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.root = root;
        method2();
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
            double radius_pentagon = radius * Math.sin(Math.toRadians(Main.SQUARE - Main.CIRCLE/Main.POLYGON))/
                    Math.sin(Math.toRadians(2*Main.SQUARE - Main.CIRCLE/(2*Main.POLYGON) - Main.CIRCLE/(4*Main.POLYGON)));

            // генеруєм масив внутрішніх вершин (5)
            getVertexesIn(x,y,radius_pentagon);

            // об'єднуєм зовнішні + внутрішні вершини через одну в один масив
            getVertexes();

            // сполучаєм всі вершини між собою послідовно і виводим масив ліній
            root.getChildren().addAll(getLines(vertexesX,vertexesY));
        } else {
            Warning warning = new Warning(root);
            root.getChildren().addAll(warning.generateWarning());
        }
    }

    private boolean checkSize(double x, double y, double radius){
        return ((x - radius > Main.MARGIN) && (x + radius < Main.WIDTH - Main.MARGIN) && (y - radius > Main.MARGIN) && (y + radius < Main.HEIGHT - Main.MARGIN));
    }

    // визначаєм координати зовнішніх вершин зірки
    private void getVertexesOut(double x, double y, double radius){
        double deltaX;      // зміщення по горизонталі до наступної вершини :  R * cos(90° + 72°); 72° = 360° / 5
        double deltaY;      // зміщення по вертикалі до наступної вершини :  R * (1 - sin(90° + 72°)); 72° = 360° / 5
        vertexes_outX[0] = x;   // стартова позиція по горизонталі над центром зірки
        vertexes_outY[0] = y;   // стартова позиція по вертикалі над центром зірки

        for (int i = 1; i < vertexes_outX.length; i++){
            deltaX = radius * Math.cos(Math.toRadians(Main.SQUARE + i*Main.CIRCLE/Main.POLYGON));
            deltaY = radius * (1 - Math.sin(Math.toRadians(Main.SQUARE + i*Main.CIRCLE/Main.POLYGON)));
            // координати наступної вершини, остання 6-та має співпадати з 1-ю
            vertexes_outX[i] = x + deltaX;
            vertexes_outY[i] = y + deltaY;
        }
    }

    // визначаєм координати внутрішніх вершин зірки
    private void getVertexesIn(double x, double y, double radius){
        double deltaX;      // зміщення по горизонталі від х до наступної вершини :  R * cos(90° + 36° + i*72°); 72° = 360°/5
        double deltaY;      // зміщення по вертикалі від y до наступної вершини :  R * sin(90° + 36° + i*72°); 72° = 360°/5
        vertexes_inX[0] = x + radius * Math.cos(Math.toRadians(Main.SQUARE + Main.CIRCLE/(2*Main.POLYGON)));   // стартова позиція по горизонталі від центру зірки (зміщення: R * cos(90° + 36°))
        vertexes_inY[0] = y - radius * Math.sin(Math.toRadians(Main.SQUARE + Main.CIRCLE/(2*Main.POLYGON)));   // стартова позиція по вертикалі від центру зірки (зміщення: R * sin(90° + 36°))

        for (int i = 1; i < vertexes_inX.length; i++){
            deltaX = radius * Math.cos(Math.toRadians(Main.SQUARE + Main.CIRCLE/(2*Main.POLYGON) + i*Main.CIRCLE/Main.POLYGON));
            deltaY = radius * Math.sin(Math.toRadians(Main.SQUARE + Main.CIRCLE/(2*Main.POLYGON) + i*Main.CIRCLE/Main.POLYGON));
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

    // метод для малювання внутрішнього п'ятикутника
    // у фінальній версії не використовується
    // служить лише для тестування
    private Line[] generatePentagon(double x, double y, double radius){
        Line[] lines = new Line[Main.POLYGON];
        double deltaX;      // зміщення по горизонталі від х до наступної вершини :  R * cos(90° + 36° + i*72°); 72° = 360°/5
        double deltaY;      // зміщення по вертикалі від y до наступної вершини :  R * sin(90° + 36° + i*72°); 72° = 360°/5
        double x_old = x + radius * Math.cos(Math.toRadians(Main.SQUARE + Main.CIRCLE/(2*Main.POLYGON)));   // стартова позиція по горизонталі від центру зірки (зміщення: R * cos(90° + 36°))
        double y_old = y - radius * Math.sin(Math.toRadians(Main.SQUARE + Main.CIRCLE/(2*Main.POLYGON)));   // стартова позиція по вертикалі від центру зірки (зміщення: R * sin(90° + 36°))

        System.out.println(x_old + "     " + y_old);

        for (int i = 0; i < lines.length; i++){
            deltaX = radius * Math.cos(Math.toRadians(Main.SQUARE + Main.CIRCLE/(2*Main.POLYGON) + (i+1)*Main.CIRCLE/Main.POLYGON));
            deltaY = radius * Math.sin(Math.toRadians(Main.SQUARE + Main.CIRCLE/(2*Main.POLYGON) + (i+1)*Main.CIRCLE/Main.POLYGON));
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
