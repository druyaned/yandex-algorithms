package druyaned.yandexalgorithms.train3.l6breadthfirstsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HW5Subway {
    
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
    
    private static List<Set<Integer>> newList(int n) {
        List<Set<Integer>> list = new ArrayList(n + 1);
        list.add(null);
        for (int i = 1; i <= n; ++i) {
            list.add(new HashSet());
        }
        return list;
    }
    
    private static void show(int n, List<Set<Integer>> list) {
        for (int v = 1; v <= n; ++v) {
            System.out.printf("%2d:", v);
            list.get(v).forEach(l -> System.out.printf(" %d", l));
            System.out.println();
        }
    }
    
    private static void show(int m, int[] counts) {
        for (int l = 1; l <= m; ++l) {
            System.out.printf("%2d: %d\n", l, counts[l]);
        }
    }
    
    private static void show(Deque<Integer> lineDeque) {
        System.out.print("lineDeque:");
        lineDeque.forEach(x -> System.out.printf(" %s", x));
        System.out.println();
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int m = readInt(reader);
        List<Set<Integer>> linesOfVertex = newList(n);
        for (int i = 1; i <= m; ++i) {
            int len = readInt(reader);
            for (int j = 0; j < len; ++j) {
                int v = readInt(reader);
                linesOfVertex.get(v).add(i);
            }
        }
        int start = readInt(reader);
        int finish = readInt(reader);
        List<Set<Integer>> lines = newList(m);
        for (int v = 1; v <= n; ++v) {
            int size = linesOfVertex.get(v).size();
            Integer[] l = new Integer[size];
            linesOfVertex.get(v).toArray(l);
            for (int i = 0; i < size; ++i) {
                for (int j = i + 1; j < size; ++j) {
                    lines.get(l[i]).add(l[j]);
                    lines.get(l[j]).add(l[i]);
                }
            }
        }
        Deque<Integer> lineDeque = new ArrayDeque(m * m);
        int[] counts = new int[m + 1];
        for (int l = 1; l <= m; ++l) {
            counts[l] = -1;
        }
        boolean[] passed = new boolean[m + 1];
        for (int line : linesOfVertex.get(start)) {
            lineDeque.add(line);
            counts[line] = 0;
            passed[line] = true;
        }
        show(lineDeque); // TODO: debug
        while (!lineDeque.isEmpty()) {
            int line = lineDeque.pollFirst();
            for (int subLine : lines.get(line)) {
                if (!passed[subLine]) {
                    lineDeque.add(subLine);
                    passed[subLine] = true;
                    counts[subLine] = counts[line] + 1;
                }
            }
            show(lineDeque); // TODO: debug
        }
        int cnt = -1;
        for (int line : linesOfVertex.get(finish)) {
            if (counts[line] != -1 && (cnt == -1 || cnt > counts[line])) {
                cnt = counts[line];
            }
        }
        System.out.printf("\nstart=%d finish=%d\n", start, finish); // TODO: debug
        System.out.print("\nvertexes:\n"); // TODO: debug
        show(n, linesOfVertex); // TODO: debug
        System.out.print("\nlines:\n"); // TODO: debug
        show(m, lines); // TODO: debug
        System.out.print("\ncounts:\n"); // TODO: debug
        show(m, counts); // TODO: debug
        writer.write(cnt + "\n");
        System.out.print(cnt + "\n"); // TODO: debug
    }
    
}
/*
n=19 m=5
l1: 1 2 4 5 6       | l3 l4
l2: 7 8 9 10 11 19  | l3 l4 l5
l3: 3 2 9 14        | l1 l2
l4: 4 11 17 18      | l1 l2 l5
l5: 15 16 11 12 13  | l2 l4
l6: 20 21           |

input:
21 6
5 1 2 4 5 6
6 7 8 9 10 11 19
4 3 2 9 14
4 4 11 17 18
5 15 16 11 12 13
2 20 21
15 1
output:
2
*/