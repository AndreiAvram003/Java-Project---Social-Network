package com.example.socialnetwork_gui.utils;

import java.util.*;

public class GraphUtils {
    public static <T> List<List<T>> getConnectedComponents(Map<T, List<T>> graph) {
        Set<T> visited = new HashSet<>();
        List<List<T>> components = new ArrayList<>();
        for (T node : graph.keySet()) {
            if (!visited.contains(node)) {
                List<T> component = new ArrayList<>();
                dfs(graph, visited, node, component);
                components.add(component);
            }
        }
        return components;
    }

    public static <T> List<List<T>> getLongestConnectedComponents(Map<T, List<T>> graph) {
        Set<T> visited = new HashSet<>();
        List<List<T>> components = new ArrayList<>();
        for (T node : graph.keySet()) {
            if (!visited.contains(node)) {
                List<T> component = new ArrayList<>();
                dfs2(graph, visited, node, component);
                components.add(component);
            }
        }
        return components;
    }

    private static <T> void dfs(Map<T, List<T>> graph, Set<T> visited, T current, List<T> component) {
        visited.add(current);
        component.add(current);
        for (T neighbor : graph.get(current)) {
            if (!visited.contains(neighbor)) {
                dfs(graph, visited, neighbor, component);
            }
        }
    }

    private static <T> void dfs2(Map<T, List<T>> graph, Set<T> visited, T current, List<T> component) {
        visited.add(current);
        component.add(current);
        for (T neighbor : graph.get(current)) {
            if (!visited.contains(neighbor)) {
                dfs2(graph, visited, neighbor, component);
            }
            break;
        }
    }
}
