package tasks.graham;

import java.util.Arrays;
import java.util.Stack;

class GrahamScan {

    private final Stack<Point> stack = new Stack<>();

    GrahamScan(Point[] pts) {
        int n = pts.length; //копия числа вершин
        Point[] points = new Point[n];
        System.arraycopy(pts, 0, points, 0, n);
        Arrays.sort(points);
        // сортируем все точки по полярному углу, относительно стартовой точки
        Arrays.sort(points, 1, n, points[0].polarOrder());

        //кладем в стек первую вершину
        stack.push(points[0]);       // p[0] стартовая точка

        // найти индекс k1 первой точки, не равной points[0]
        int k1;
        for (k1 = 1; k1 < n; k1++)
            if (!points[0].equals(points[k1])) break;
            //если ничего не нашли - выходим
        if (k1 == n) return;

        // найти индекс k2 первой точки, не коллинеарной с points[0] и points[k1]
        int k2;
        for (k2 = k1 + 1; k2 < n; k2++)
            if (Point.ccw(points[0], points[k1], points[k2]) != 0) break;
        stack.push(points[k2 - 1]);    // points[k2-1] вторая точка

        // алгоритм Грэхема
        for (int i = k2; i < n; i++) {
            //pop - вытаскивает последний объект
            //peek - первый элемент
            Point top = stack.pop();

            //проверка на поворот, если он правый - удаляем из ответа
            while (Point.ccw(stack.peek(), top, points[i]) <= 0) {
                top = stack.pop();
            }

            //как только поворот левый - добавляем в ответ
            stack.push(top);
            stack.push(points[i]);
        }
    }

    Iterable<Point> hull() {
        Stack<Point> s = new Stack<>();
        for (Point p : stack) s.push(p);
        return s;
    }

}