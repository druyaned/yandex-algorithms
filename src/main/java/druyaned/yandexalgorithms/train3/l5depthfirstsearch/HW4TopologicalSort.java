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

public class HW4TopologicalSort {
    
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
    
    private static void show(int n, List<List<Integer>> graph, int[] incomingCounts) {
        System.out.print("\ngraph:\n");
        for (int i = 1; i <= n; ++i) {
            System.out.printf("%2d:", i);
            for (Integer v : graph.get(i)) {
                System.out.printf(" %d", v);
            }
            System.out.printf(" | incoming=%d\n", incomingCounts[i]);
        }
    }
    
    private static void show(int ancestorCount, int[] ancestors) {
        System.out.print("\nancestors:");
        for (int i = 0; i < ancestorCount; ++i) {
            System.out.printf(" %d", ancestors[i]);
        }
        System.out.println();
    }
    
    private static void show(int[] sequence, int sequenceCount) {
        System.out.print("\nsequence:");
        for (int i = sequenceCount; i > 0; --i) {
            System.out.printf(" %d", sequence[i - 1]);
        }
        System.out.println();
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int m = readInt(reader);
        List<List<Integer>> graph = new ArrayList(n + 1);
        int[] incomingCounts = new int[n + 1];
        for (int i = 0; i <= n; ++i) {
            graph.add(new ArrayList());
        }
        for (int i = 0; i < m; ++i) {
            int v1 = readInt(reader);
            int v2 = readInt(reader);
            graph.get(v1).add(v2);
            incomingCounts[v2]++;
        }
        int[] ancestors = new int[n + 1];
        int ancestorCount = 0;
        for (int i = 1; i <= n; ++i) {
            if (incomingCounts[i] == 0) {
                ancestors[ancestorCount++] = i;
            }
        }
        show(n, graph, incomingCounts); // TODO: debug
        show(ancestorCount, ancestors); // TODO: debug
        int[] vertexStack = new int[n + m + 1];
        int size = 0;
        int[] ind = new int[n + 1];
        int[] color = new int[n + 1]; // 0 == free, 1 == visited, 2 == no descendants left
        int[] sequence = new int[n + 1];
        int sequenceCount = 0;
        for (int i = 1; i <= n; ++i) {
            vertexStack[size++] = i;
            while (size > 0) {
                int v = vertexStack[size - 1];
                if (color[v] == 0) {
                    color[v] = 1;
                }
                if (ind[v] == graph.get(v).size()) {
                    size--;
                    if (color[v] != 2) {
                        sequence[sequenceCount++] = v;
                        color[v] = 2;
                    }
                } else if (color[v] != 2) {
                    int subV = graph.get(v).get(ind[v]++);
                    if (color[subV] == 0) {
                        vertexStack[size++] = subV;
                    } else if (color[subV] == 1) {
                        System.out.println("CYCLE IS FOUND"); // TODO: debug
                        writer.write("-1\n");
                        return;
                    }
                }
            }
        }
        if (sequenceCount > 0) {
            writer.write(Integer.toString(sequence[sequenceCount - 1]));
        }
        for (int i = sequenceCount - 1; i > 0; --i) {
            writer.write(" " + sequence[i - 1]);
        }
        writer.write('\n');
        show(sequence, sequenceCount); // TODO: debug
    }
    
}
/*
input:
6 6
1 3
1 5
3 2
3 4
4 2
5 3
output:
6 1 5 3 4 2

input:
6 6
1 5
3 1
3 2
3 4
4 2
5 3
output:
-1
*/