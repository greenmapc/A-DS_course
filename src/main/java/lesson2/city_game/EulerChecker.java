package lesson2.city_game;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

public class EulerChecker {

    public static boolean checkForEulerPath(Map<Character, List<CityGame.Edge>> graph) {
        Counter oddVertex = new Counter(0);
        graph.keySet().forEach(vertex -> {
            if (deg(vertex, graph).getDegSum() % 2 != 0) {
                oddVertex.increment();
            }
        });

        if (oddVertex.getCount() > 2) {
            return false;
        }

//        Map<Character, Boolean> visited = new HashMap<>();
//        graph.keySet().stream()
//                .filter(vertex -> deg(vertex, graph) > 0)
//                .findAny(vertex -> dfs(vertex, visited, graph));
        return true;
    }


    public static int dfs(Character vertex, Map<Character, Boolean> visited, Map<Character, List<CityGame.Edge>> graph) {
        visited.put(vertex, true);
        Counter visitedVertexes = new Counter(1);
        graph.get(vertex).forEach(edge -> {
            char v = edge.getTo();
            if (visited.get(v) == null) {
                visitedVertexes.incrementBy(dfs(v, visited, graph));
            }
        });
        return visitedVertexes.count;
    }

    public static Deg deg(Character vertex, Map<Character, List<CityGame.Edge>> graph) {
        int outCount = graph.get(vertex).size();
        int inCount = (int) graph.values().stream()
                .flatMap(List::stream)
                .filter(word -> word.getTo() == vertex)
                .count();
        return Deg.builder()
                .outCount(outCount)
                .inCount(inCount)
                .build();
    }

    @Data
    @AllArgsConstructor
    private static class Counter {
        private int count;

        public void increment() {
            count ++;
        }

        public void incrementBy(int by) {
            count += by;
        }
    }

    @Data
    @Builder
    public static class Deg {
        private int inCount;
        private int outCount;

        public int getDegSum() {
            return inCount + outCount;
        }
    }

}
