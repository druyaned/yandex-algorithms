package com.github.druyaned.yandexalgorithms.train3.l5depthfirstsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HW3Cheating {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[15];
    
    private static int readInt(Reader reader) throws IOException {
        int c;
        while ((c = reader.read()) != -1 && c != '+' && c != '-' && !('0' <= c && c <= '9')) {}
        if (c == -1) {
            throw new NumberFormatException("empty input");
        }
        buf[0] = (char)c;
        int l = 1;
        while ((c = reader.read()) != -1 && '0' <= c && c <= '9') {
            buf[l++] = (char)c;
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
    private static void show(int n, List<List<Integer>> graph) {
        for (int i = 1; i <= n; ++i) {
            System.out.printf("%2d:", i);
            for (Integer v : graph.get(i)) {
                System.out.printf(" %s", v.toString());
            }
            System.out.println();
        }
    }
    
    private static void show(int n, int[] groups) {
        System.out.print("groups:");
        for (int i = 1; i <= n; ++i) {
            System.out.printf(" %d{%d}", i, groups[i]);
        }
        System.out.println();
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int m = readInt(reader);
        List<List<Integer>> graph = new ArrayList(n + 1);
        graph.add(null);
        for (int i = 1; i <= n; ++i) {
            graph.add(new ArrayList());
        }
        for (int i = 0; i < m; ++i) {
            int vertex1 = readInt(reader);
            int vertex2 = readInt(reader);
            graph.get(vertex1).add(vertex2);
            if (vertex1 != vertex2) {
                graph.get(vertex2).add(vertex1);
            }
        }
        show(n, graph); // TODO: debug
        int[] groupStack = new int[n + m + 1];
        int[] vertexStack = new int[n + m + 1];
        int size = 0;
        boolean[] passed = new boolean[n + 1];
        int[] ind = new int[n + 1];
        int[] groups = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            if (groups[i] == 0) {
                groupStack[size] = 1;
            }
            vertexStack[size++] = i;
            while (size > 0) {
                int vertex = vertexStack[size - 1];
                int group = groupStack[size - 1];
                if (!passed[vertex]) {
                    passed[vertex] = true;
                    groups[vertex] = group;
                }
                if (ind[vertex] == graph.get(vertex).size()) {
                    size--;
                } else {
                    int subV = graph.get(vertex).get(ind[vertex]++);
                    if (!passed[subV]) {
                        groupStack[size] = 3 - group;
                        vertexStack[size++] = subV;
                    } else if (groups[subV] == groups[vertex]) {
                        System.out.printf("MISTAKE: %d %d\n", vertex, subV); // TODO: debug
                        show(n, groups);
                        System.out.print("NO\n");
                        writer.write("NO\n");
                        return;
                    }
                }
            }
        }
        System.out.print("groups:");
        for (int i = 1; i <= n; ++i) {
            System.out.printf(" %d{%d}", i, groups[i]);
        }
        System.out.print("\nYES\n");
        writer.write("YES\n");
    }
    
}
/*
6 4
1 4
1 5
2 4
3 5
*/
