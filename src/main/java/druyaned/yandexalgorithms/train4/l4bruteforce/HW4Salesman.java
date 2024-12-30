package druyaned.yandexalgorithms.train4.l4bruteforce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4Salesman {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        if (n == 1) {
            writer.write("0\n");
            return;
        }
        Vertex[] g = readGraph(reader, n);
        int[] v = new int[(int)10e5]; // stack of vertices
        int[] b = new int[(int)10e5]; // bitmasks of visited vertices
        int[] w = new int[(int)10e5]; // sums of weights
        final int ALL_SET = (1 << n) - 1; // full bitmask is set
        int minSum = Integer.MAX_VALUE;
        int previousVertex = 0;
        v[0] = 1;
        for (int size = 1; size > 0; ) {
            Vertex vertex = g[v[--size]];
            int vertexBit = 1 << (vertex.id - 1);
            int bitmask = b[size] | vertexBit;
            int weight = w[size];
            for (int j = 0; j < vertex.l; j++) {
                int descendant = vertex.d[j];
                int descendantBit = 1 << (descendant - 1);
                int sum = weight + vertex.w[j];
                if (bitmask == ALL_SET && descendant == 1) {
                    if (minSum > sum) {
                        minSum = sum;
                        previousVertex = vertex.id;
                    }
                } else if ((bitmask & descendantBit) == 0 && minSum > sum) {
                    v[size] = descendant;
                    b[size] = bitmask;
                    w[size++] = sum;
                }
            }
        }
        if (previousVertex == 0) {
            writer.write("-1\n");
        } else {
            writer.write(Integer.toString(minSum) + "\n");
        }
    }
    
    private static class Vertex {
        public final int id, l;
        public final int[] d; // descendants
        public final int[] w; // weights
        public Vertex(int id, int l) {
            this.id = id;
            this.l = l;
            d = new int[l];
            w = new int[l];
        }
    }
    
    private static Vertex[] readGraph(BufferedReader reader, int n) throws IOException {
        Vertex[] g = new Vertex[n + 1];
        int[] inputWeights = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int l = 0;
            for (int j = 1; j <= n; j++) {
                inputWeights[j] = readInt(reader);
                if (inputWeights[j] != 0) {
                    l++;
                }
            }
            g[i] = new Vertex(i, l);
            for (int j = 1, ind = 0; j <= n; j++) {
                if (inputWeights[j] != 0) {
                    g[i].d[ind] = j;
                    g[i].w[ind++] = inputWeights[j];
                }
            }
        }
        return g;
    }
    
    private static final char[] buf = new char[16];
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
    
}
/*
Найти кратчайший путь через все вершины графа.
n in [1, 10].
В худшем случае сложность O(n!).
Кроме использования цикла вместо рекурсии, использовал
битовые маски для хранения пройденных вершин.
*/