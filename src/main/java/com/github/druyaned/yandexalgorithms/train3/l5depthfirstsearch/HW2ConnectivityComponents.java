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

public class HW2ConnectivityComponents {
    
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
        int[] stack = new int[n + m + 1];
        int size = 0;
        boolean[] passed = new boolean[n + 1];
        int[] ind = new int[n + 1];
        List<List<Integer>> components = new ArrayList(n + 1);
        for (int i = 0; i <= n; ++i) {
            components.add(null);
        }
        int componentCount = 0;
        int[] starts = new int[n];
        boolean[] startsWith = new boolean[n + 1];
        stack[size++] = 1;
        for (int i = 1; i <= n; ++i) {
            stack[size++] = i;
            while (size > 0) {
                int vertex = stack[size - 1];
                if (!passed[vertex]) {
                    passed[vertex] = true;
                    if (!startsWith[i]) {
                        startsWith[i] = true;
                        starts[componentCount++] = i;
                        components.set(i, new ArrayList());
                    }
                    components.get(i).add(vertex);
                }
                if (ind[vertex] == graph.get(vertex).size()) {
                    --size;
                } else {
                    int subV = graph.get(vertex).get(ind[vertex]++);
                    if (!passed[subV]) {
                        stack[size++] = subV;
                    }
                }
            }
        }
        writer.write(componentCount + "\n");
        for (int i = 0; i < componentCount; ++i) {
            List<Integer> c = components.get(starts[i]);
            writer.write(c.size() + "\n");
            if (!c.isEmpty()) {
                writer.write(c.get(0).toString());
            }
            for (int j = 1; j < c.size(); ++j) {
                writer.write(" " + c.get(j).toString());
            }
            writer.write('\n');
        }
    }
    
}
