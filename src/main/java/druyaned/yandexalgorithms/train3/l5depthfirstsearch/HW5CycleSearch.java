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

public class HW5CycleSearch {
    
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
        System.out.print("\ngraph:\n");
        for (int i = 1; i <= n; ++i) {
            System.out.printf("%2d:", i);
            for (Integer v : graph.get(i)) {
                System.out.printf(" %d", v);
            }
            System.out.println();
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        List<List<Integer>> graph = new ArrayList(n + 1);
        for (int i = 0; i <= n; ++i) {
            graph.add(new ArrayList());
        }
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (readInt(reader) == 1) {
                    graph.get(i).add(j);
                }
            }
        }
        show(n, graph); // TODO: debug
        int[] vertexStack = new int[n + 1];
        int size = 0;
        int[] ind = new int[n + 1];
        int[] color = new int[n + 1]; // 0 == free, 1 == visited, 2 == no descendants left
        int[] cycle = new int[n];
        int cycleSize = 0;
        for (int i = 1; i <= n && cycleSize == 0; ++i) {
            vertexStack[size++] = i;
            while (size > 0 && cycleSize == 0) {
                int v = vertexStack[size - 1];
                if (color[v] == 0) {
                    color[v] = 1;
                }
                if (ind[v] == graph.get(v).size()) {
                    size--;
                    color[v] = 2;
                } else {
                    int subV = graph.get(v).get(ind[v]++);
                    if (color[subV] == 0) {
                        vertexStack[size++] = subV;
                    } else if (color[subV] == 1 && vertexStack[size - 2] != subV) {
                        while (size > 0 && vertexStack[size - 1] != subV) {
                            cycle[cycleSize++] = vertexStack[--size];
                        }
                        cycle[cycleSize++] = subV;
                    }
                }
            }
        }
        if (cycleSize == 0) {
            writer.write("NO\n");
        } else {
            writer.write("YES\n" + cycleSize + "\n");
            writer.write(Integer.toString(cycle[0]));
            for (int i = 1; i < cycleSize; ++i) {
                writer.write(" " + cycle[i]);
            }
            writer.write('\n');
        }
    }
    
}
/*
    1 2 3 4 5 6 7 8 9
  +------------------
1 | 0 0 0 0 0 1 0 0 0
2 | 0 0 1 0 0 0 1 1 0
3 | 0 1 0 0 0 0 0 0 0
4 | 0 0 0 0 0 1 0 1 0
5 | 0 0 0 0 0 0 1 0 0
6 | 1 0 0 1 0 0 1 0 0
7 | 0 1 0 0 1 1 0 0 0
8 | 0 1 0 1 0 0 0 0 0
9 | 0 0 0 0 0 0 0 0 0

input:
9
0 0 0 0 0 1 0 0 0
0 0 1 0 0 0 1 1 0
0 1 0 0 0 0 0 0 0
0 0 0 0 0 1 0 1 0
0 0 0 0 0 0 1 0 0
1 0 0 1 0 0 1 0 0
0 1 0 0 1 1 0 0 0
0 1 0 1 0 0 0 0 0
0 0 0 0 0 0 0 0 0
output:
YES
5
7 2 8 4 6

input:
9
0 0 0 0 0 1 0 0 0
0 0 1 0 0 0 1 1 0
0 1 0 0 0 0 0 0 0
0 0 0 0 0 1 0 0 0
0 0 0 0 0 0 1 0 0
1 0 0 1 0 0 1 0 0
0 1 0 0 1 1 0 0 0
0 1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0
output:
NO
*/