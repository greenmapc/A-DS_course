/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

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
        Arrays.sort(points, 1, n, points[0].polarOrder());

        stack.push(points[0]);       // p[0] стартовая точка

        // найти индекс k1 первой точки, не равной points[0]
        int k1;
        for (k1 = 1; k1 < n; k1++)
            if (!points[0].equals(points[k1])) break;
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

            //проверка на поворот, пока он неправильный
            while (Point.ccw(stack.peek(), top, points[i]) <= 0) {
                top = stack.pop();
            }
            stack.push(top);
            stack.push(points[i]);
        }

        assert isConvex();
    }

    Iterable<Point> hull() {
        Stack<Point> s = new Stack<>();
        for (Point p : stack) s.push(p);
        return s;
    }

    int number(){ //количество точек в готовом многоугольнике
        int  n2 = 0;
        for (Point p : stack) ++n2;
        return n2;
    }

    //проверить, является ли граница многоугольника строго выпуклой
    private boolean isConvex() {
        int n = stack.size();
        if (n <= 2) return true;

        Point[] points = new Point[n];
        int k = 0;
        for (Point p : hull()) {
            points[k++] = p;
        }

        for (int i = 0; i < n; i++) {
            if (Point.ccw(points[i], points[(i + 1) % n], points[(i + 2) % n]) <= 0) {
                return false;
            }
        }
        return true;
    }
}