package druyaned.yandexalgorithms.train3.l5depthfirstsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HW1DepthFirstSearch {
    
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
        int[] stack = new int[n * n];
        int size = 0;
        boolean[] passed = new boolean[n + 1];
        int[] ind = new int[n + 1];
        List<Integer> connectivityComponent = new ArrayList(n);
        stack[size++] = 1;
        while (size > 0) {
            int v = stack[size - 1];
            if (!passed[v]) {
                passed[v] = true;
                connectivityComponent.add(v);
            }
            if (ind[v] == graph.get(v).size()) {
                --size;
            } else {
                int subV = graph.get(v).get(ind[v]++);
                if (!passed[subV]) {
                    stack[size++] = subV;
                }
            }
        }
        connectivityComponent.sort((v1, v2) -> v1 - v2);
        writer.write(connectivityComponent.size() + "\n");
        if (!connectivityComponent.isEmpty()) {
            writer.write(connectivityComponent.get(0).toString());
        }
        for (int i = 1; i < connectivityComponent.size(); ++i) {
            writer.write(" " + connectivityComponent.get(i));
        }
        writer.write('\n');
    }
    
}
/*
input:
16 18
1 2
1 4
1 10
2 3
4 1
4 5
4 6
6 7
6 8
6 10
9 10
9 11
9 13
10 10
11 11
12 14
12 16
15 16
output:
12
1 2 3 4 5 6 7 8 9 10 11 13
*/