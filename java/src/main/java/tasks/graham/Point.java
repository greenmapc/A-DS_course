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

import java.util.Comparator;

//описание точки
public class Point implements Comparable<Point>{

    private final int x;    // x координата
    private final int y;    // y координата

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    int x() {
        return x;
    }

    int y() {
        return y;
    }

    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        return Integer.compare(this.x, that.x);
    }

    //Возвращает 1, если a -> b -> c - поворот против часовой стрелки (т.е. угол возрастает)
    // с помощью векторного произведения
    static int ccw(Point a, Point b, Point c) {
        int area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        return Integer.compare(area2, 0);
    }

    Comparator<Point> polarOrder() {
        return new PolarOrder();
    }

    private class PolarOrder implements Comparator<Point> {
        public int compare(Point q1, Point q2) {
            int dx1 = q1.x - x;
            int dy1 = q1.y - y;
            int dx2 = q2.x - x;
            int dy2 = q2.y - y;
            if      (dy1 >= 0 && dy2 < 0) return -1;    // q1 сверху; q2 снизу
            else if (dy2 >= 0 && dy1 < 0) return +1;    // q1 снизу; q2 сверху
            else if (dy1 == 0 && dy2 == 0) {            // коллинеарные по y
                if      (dx1 >= 0 && dx2 < 0) return -1;
                else if (dx2 >= 0 && dx1 < 0) return +1;
                else                          return  0;
            }
            else return -ccw(Point.this, q1, q2);     // обе сверху или снизу
        }
    }
}