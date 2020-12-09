package tasks.city_game;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EulerChecker {

    public static boolean checkForEulerPath(Map<Character, List<CityGame.Edge>> graph) {
        List<Deg> vertexDeg = new ArrayList<>();
        graph.keySet().forEach(vertex -> vertexDeg.add(deg(vertex, graph)));
        long oddVertex = vertexDeg.stream()
                .filter(deg -> deg.getDegSum() % 2 != 0)
                .count();

        return oddVertex <= 2 && (vertexDeg.stream().anyMatch(Deg::isStartVertex) && vertexDeg.stream().anyMatch(Deg::isEndVertex));
    }

    public static Deg deg(Character vertex, Map<Character, List<CityGame.Edge>> graph) {
        int outCount = graph.get(vertex).size();
        int inCount = (int) graph.values().stream()
                .flatMap(List::stream)
                .filter(word -> word.getTo().equals(vertex))
                .count();
        return Deg.builder()
                .outCount(outCount)
                .inCount(inCount)
                .build();
    }

    @Data
    @Builder
    public static class Deg {
        private int inCount;
        private int outCount;

        public int getDegSum() {
            return inCount + outCount;
        }

        public boolean isStartVertex() {
            return outCount - inCount == 1;
        }

        public boolean isEndVertex() {
            return inCount - outCount == 1;
        }
    }

}
