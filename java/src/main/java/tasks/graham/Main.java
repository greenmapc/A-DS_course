package tasks.graham;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        int n = 5; //число вершин
        Point[] points = new Point[n];
        System.out.println("points: ");
        for (int i = 0; i < n; i++) {
            //координаты вершин
            int x = rand.nextInt(20);
            int y = rand.nextInt(20);
            System.out.println("(" + x + ";" + y + ")");
            points[i] = new Point(x, y);
        }
        GrahamScan graham = new GrahamScan(points);
        for (Point p : graham.hull()) {
            System.out.println("(" + p.x() + ";" + p.y() + ")");
        }
    }
}
