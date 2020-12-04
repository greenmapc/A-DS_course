/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package iteration2.min_cut;


import java.util.LinkedList;
import java.util.Queue;

// минимальный разрез в графе
public class MinGraphCut {

    // используется для определения маршрута из вершины s в вершину t
    private static boolean bfs(int[][] rGraph, int s, int t, int[] parent) {

        boolean[] visited = new boolean[rGraph.length];

        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (!q.isEmpty()) {
            int v = q.poll();
            for (int i = 0; i < rGraph.length; i++) {
                if (rGraph[v][i] > 0 && !visited[i]) {
                    q.offer(i);
                    visited[i] = true;
                    parent[i] = v;
                }
            }
        }

        return visited[t];
    }

    // с помощью dfs как бы разбиваем граф (с объединенными вершинами) на два не пересекающихся множества
    private static void dfs(int[][] rGraph, int s, boolean[] visited) {
        visited[s] = true;
        for (int i = 0; i < rGraph.length; i++) {
            if (rGraph[s][i] > 0 && !visited[i]) {
                dfs(rGraph, i, visited);
            }
        }
    }

    private static void minCut(int[][] graph, int begin, int end) {
        int u, v;

        // копируем в другой граф, так как основной граф будет портится во время работы алгоритма
        int[][] rGraph = new int[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                rGraph[i][j] = graph[i][j];
            }
        }

        int[] parent = new int[graph.length];

        while (bfs(rGraph, begin, end, parent)) {

            //находим минимальный разрез для двух вершин
            int pathFlow = Integer.MAX_VALUE;
            for (v = end; v != begin; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }

            // объединяем эти две вершины в одну
            for (v = end; v != begin; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] = rGraph[u][v] - pathFlow;
                rGraph[v][u] = rGraph[v][u] + pathFlow;
            }
        }

        boolean[] isVisited = new boolean[graph.length];
        dfs(rGraph, begin, isVisited);

        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) {
                    System.out.println(i + " - " + j);
                }
            }
        }
    }

    public static void main(String args[]) {
        int graph[][] = {
                {0, 16, 13, 0, 0},
                {0, 0, 10, 12, 0},
                {0, 4, 0, 0, 14},
                {0, 0, 9, 0, 0},
                {0, 0, 0, 7, 0},
        };
//        int graph[][] = {
//                { 0, 1, 1, 0, 0 },
//                { 2, 0, 0, 1, 0 },
//                { 1, 0, 0, 2, 0 },
//                { 0, 1, 2, 0, 3 },
//                { 0, 0, 0, 0, 3 },
//        };
        minCut(graph, 0, graph.length - 1);
    }
}
