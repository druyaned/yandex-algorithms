package com.github.druyaned.yandexalgorithms.train3.l5depthfirstsearch;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch {
    
    public static void main(String[] args) {
        int n = 16;
        int m = 18;
        int[] edges1 = {1, 1, 1, 2, 4, 4, 4, 6, 6, 6, 9, 9, 9, 10, 11, 12, 12, 15};
        int[] edges2 = {2, 4, 10, 3, 1, 5, 6, 7, 8, 10, 10, 11, 13, 10, 11, 14, 16, 16};
        List<List<Integer>> graph = new ArrayList(n + 1);
        for (int i = 0; i <= n; ++i) {
            graph.add(new ArrayList());
        }
        for (int i = 0; i < m; ++i) {
            graph.get(edges1[i]).add(edges2[i]);
            if (edges1[i] != edges2[i]) {
                graph.get(edges2[i]).add(edges1[i]);
            }
        }
        show(n, graph);
        dfs(n, graph);
    }
    
    private static void show(int n, List<List<Integer>> graph) {
        for (int i = 1; i <= n; ++i) {
            System.out.printf("%2d:", i);
            for (int v : graph.get(i)) {
                System.out.printf(" %d", v);
            }
            System.out.println();
        }
    }
    
    private static void dfs(int n, List<List<Integer>> graph) {
        int[] vertexStack = new int[n * n];
        int size = 0;
        boolean[] passed = new boolean[n + 1];
        int[] ind = new int[n + 1];
        int[] connectivityComponent = new int[n + 1];
        System.out.print("\ndfs:\n");
        int maxSize = 0;
        for (int i = 1; i <= n; ++i) {
            vertexStack[size++] = i;
            int printCount = 0;
            while (size > 0) {
                int vertex = vertexStack[size - 1];
                if (!passed[vertex]) {
                    passed[vertex] = true;
                    System.out.printf(" %d", vertex);
                    connectivityComponent[vertex] = i;
                    printCount++;
                }
                if (ind[vertex] == graph.get(vertex).size()) {
                    size--;
                } else {
                    int subV = graph.get(vertex).get(ind[vertex]++);
                    if (!passed[subV]) {
                        vertexStack[size++] = subV;
                    }
                    if (maxSize < size) {
                        maxSize = size;
                    }
                }
            }
            if (printCount != 0) {
                System.out.println();
            }
        }
        System.out.printf("maxSize: %d\n", maxSize);
        System.out.print("connectivityComponent:");
        for (int i = 1; i <= n; ++i) {
            System.out.printf(" %d{%d}", i, connectivityComponent[i]);
        }
        System.out.println();
    }
    
}
/*
Как я понял как написать обход в глубину без рекурсии?
Взял листик и вручную пошагово расписал состояния стека,
что позволило проследить условия переходов и вспомогательные
параметры. Вкратце, удаляю, когда ind[v] == vertex.size(),
а добавляю, когда !passed[v].
*/