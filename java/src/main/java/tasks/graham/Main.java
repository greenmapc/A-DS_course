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
