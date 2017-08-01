package ua.goit.java8.module51.task2;

import java.util.Scanner;

/**
 * Created by t.oleksiv on 01/08/2017.
 */
public class Input {
    // Отримуєм дані з консолі
    private double x;
    private double y;
    private double radius;
    private String method;

    public Input(){
        getInput();
    }

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

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getRadius(){
        return radius;
    }

    public String getMethod(){
        return method;
    }
}
