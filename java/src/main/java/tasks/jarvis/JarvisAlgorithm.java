package tasks.jarvis;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JarvisAlgorithm {

    public static Point[] convexHull(Point[] a) {
        int m = 0;

        //поиск стартовой точки
        for (int i = 1; i < a.length; i++) {
            if (a[i].x > a[m].x) m = i;
            else if (a[i].x == a[m].x && a[i].y < a[m].y) m = i;
        }

        Point[] p = new Point[a.length + 1];
        List<Integer> visited = new ArrayList<>();
        visited.add(m);
        p[0] = a[m];
        a[m] = a[0];
        a[0] = p[0];

        int k = 0;
        int min = 0;

        do {
            for (int j = 1; j < a.length; j++) {
                // ищем две точки с наибольшим полярным углом,
                // и если поворот совпадает, проверяем по удаленности
                if ((cross(p[k], a[min], a[j]) < 0 ||
                        cross(p[k], a[min], a[j]) == 0 &&
                                dist2(p[k], a[min]) < dist2(p[k], a[j])) && !visited.contains(j)) {
                    min = j;
                    visited.add(min);
                }

            }
            k++;
            p[k] = a[min];
            min = 0;
        }
        while (!Objects.equals(p[k], p[0]));
        return Arrays.copyOf(p, k);
    }

    //перекрестное умножение векторов
    private static long cross(Point a1, Point a2, Point b2) {
        return (long) (a2.x - a1.x) * (b2.y - a1.y) - (long) (b2.x -a1.x) * (a2.y - a1.y);
    }

//    расстояние в квадрате
    private static long dist2(Point a1, Point a2) {
        return (long) (a2.x - a1.x) * (a2.x - a1.x) +
                (long) (a2.y - a1.y) * (a2.y - a1.y);
    }

}
