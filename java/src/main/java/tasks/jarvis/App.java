package tasks.jarvis;

import java.awt.*;
import java.util.Random;

public class App {
    public static void main(String[] args) {
//      количество точек на плоскости
        int n = 8;
        Point[] data = generateData(n);
        Point[] res = JarvisAlgorithm.convexHull(data);
        for (Point r : res) {
            System.out.printf("{%d:%d};", r.x, r.y);
        }
    }

    private static Point[] generateData(int n) {
        Point[] data = new Point[n];
        for (int i = 0; i < n; i++) {
            data[i] = new Point(new Random().nextInt(100), new Random().nextInt(100));
        }
        return data;
    }
}
