package lesson2.city_game;

import lombok.Builder;
import lombok.Data;

import java.util.*;

public class CityGame {

    public static void main(String[] args) throws IllegalAccessException {
        List<String> words = Arrays.asList("b", "ab", "bc", "bb");
        Map<Character, List<Edge>> graph = graphCreation(words);
        if (!EulerChecker.checkForEulerPath(graph)){
            throw new IllegalAccessException("Game can not be ended success");
        }

        Character startVertex = findStartVertex(graph);
        List<String> wordOrder = findEulerPath(graph, startVertex);
        System.out.println(wordOrder);

    }

    public static List<String> findEulerPath(Map<Character, List<Edge>> graph, Character startVertex) {
        List<String> wordOrder = new ArrayList<>();
        Deque<Character> stack = new LinkedList<>();
        stack.push(startVertex);
        while (!stack.isEmpty()) {
            Character currentVertex = stack.pop();
            boolean findIntermediateVertex = false;
            for (Edge edge : graph.get(currentVertex)) {
                if (!edge.isDeleted() && EulerChecker.deg(edge.getTo(), graph).getDegSum() % 2 == 0) {
                    wordOrder.add(edge.word);
                    edge.setDeleted();
                    stack.push(edge.getTo());
                    findIntermediateVertex = true;
                }
            }
            if (!findIntermediateVertex) {
                graph.get(currentVertex).forEach(edge -> {
                    if (!edge.isDeleted()) {
                        wordOrder.add(edge.word);
                        edge.setDeleted();
                        stack.push(edge.getTo());
                    }
                });
            }
        }

        return wordOrder;
    }

    public static Character findStartVertex(Map<Character, List<Edge>> graph) {
        for (Character vertex : graph.keySet()) {
            EulerChecker.Deg deg = EulerChecker.deg(vertex, graph);
            if (deg.getOutCount() - deg.getInCount() == 1) {
                return vertex;
            }
        }

        return graph.keySet().stream()
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No vertex in graph"));

    }

    public static Map<Character, List<Edge>> graphInit(List<String> words) {
        Map<Character, List<Edge>> graph = new HashMap<>();
        words.forEach(word -> {
            Character startVertex = word.charAt(0);
            Character endVertex = word.charAt(word.length() - 1);
            graph.computeIfAbsent(startVertex, k -> new ArrayList<>());
            graph.computeIfAbsent(endVertex, k -> new ArrayList<>());
        });
        return graph;
    }

    public static Map<Character, List<Edge>> graphCreation(List<String> words) {
        Map<Character, List<Edge>> graph = graphInit(words);
        words.forEach(word -> {
            char vertex = word.charAt(0);
            graph.computeIfAbsent(vertex, k -> new ArrayList<>());
            List<Edge> list = graph.get(vertex);
            list.add(Edge.builder()
                    .deleted(false)
                    .word(word)
                    .to(word.charAt(word.length() - 1))
                    .build());
        });
        return graph;
    }


    @Data
    @Builder
    public static class Edge {
        private Character to;
        private String word;
        private boolean deleted;

        public void setDeleted() {
            deleted = true;
        }
    }

}
