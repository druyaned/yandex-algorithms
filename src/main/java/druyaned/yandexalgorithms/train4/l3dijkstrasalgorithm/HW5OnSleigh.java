package druyaned.yandexalgorithms.train4.l3dijkstrasalgorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class HW5OnSleigh {
    
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
        Vertex[] v = inputVertices(reader, n);
        int[][] m = makeRangeMatrix(n, v);
        Target[] targets = makeTargets(n, v, m);
        writeAnswer(writer, n, targets);
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
    
    private static class Vertex {
        public final int t, u;
        public int l; // descendants and ranges length
        public int[] d; // descendants
        public int[] r; // ranges
        public Vertex(int t, int u) {
            this.t = t;
            this.u = u;
            l = 0;
        }
    }
    
    private static class Edge {
        public final int a, b, r;
        public Edge(int a, int b, int r) {
            this.a = a;
            this.b = b;
            this.r = r;
        }
    }
    
    private static class Target {
        public double t; // time
        public int p; // prev
        public Target(double t, int p) {
            this.t = Double.MAX_VALUE;
            this.p = 0;
        }
    }
    
    private static Vertex[] inputVertices(BufferedReader reader, int n) throws IOException {
        Vertex[] v = new Vertex[n + 1];
        Edge[] e = new Edge[n];
        for (int i = 1; i <= n; i++) {
            int t = readInt(reader);
            int u = readInt(reader);
            v[i] = new Vertex(t, u);
        }
        for (int i = 1; i < n; i++) {
            int a = readInt(reader);
            int b = readInt(reader);
            int r = readInt(reader);
            e[i] = new Edge(a, b, r);
            v[a].l++;
            if (a != b) {
                v[b].l++;
            }
        }
        for (int i = 1; i <= n; i++) {
            v[i].d = new int[v[i].l];
            v[i].r = new int[v[i].l];
        }
        int[] ind = new int[n + 1];
        for (int i = 1; i < n; i++) {
            final int a = e[i].a;
            final int b = e[i].b;
            final int r = e[i].r;
            v[a].d[ind[a]] = b;
            v[a].r[ind[a]++] = r;
            if (a != b) {
                v[b].d[ind[b]] = a;
                v[b].r[ind[b]++] = r;
            }
        }
        return v;
    }
    
    private static int[][] makeRangeMatrix(int n, Vertex[] v) {
        int[][] m = new int[n + 1][n + 1];
        boolean[] visited = new boolean[n + 1];
        int[] stack = new int[n * n];
        int size = 0;
        for (int i = 1; i <= n; i++) {
            size = 0;
            Arrays.fill(visited, false);
            visited[i] = true;
            for (int j = 0; j < v[i].l; j++) {
                final int d = v[i].d[j];
                if (!visited[d]) {
                    stack[size++] = d;
                    m[i][d] = v[i].r[j];
                }
            }
            while (size > 0) {
                int subV = stack[--size];
                if (!visited[subV]) {
                    visited[subV] = true;
                    for (int j = 0; j < v[subV].l; j++) {
                        final int subD = v[subV].d[j];
                        if (!visited[subD]) {
                            stack[size++] = subD;
                            m[i][subD] = m[i][subV] + v[subV].r[j];
                        }
                    }
                }
            }
        }
        return m;
    }
    
    private static Target[] makeTargets(int n, Vertex[] v, int[][] m) {
        Target[] targets = new Target[n + 1];
        for (int i = 1; i <= n; i++) {
            targets[i] = new Target(Double.MAX_VALUE, 0);
        }
        boolean[] visited = new boolean[n + 1];
        targets[1].t = 0d;
        visited[1] = true;
        for (int i = 2; i <= n; i++) {
            targets[i].t = v[i].t + (double)m[1][i] / v[i].u;
            targets[i].p = 1;
        }
        for (int i = 1; i < n; i++) {
            double minT = Double.MAX_VALUE;
            int minV = 0;
            for (int j = 1; j <= n; j++) {
                if (!visited[j] && minT > targets[j].t) {
                    minT = targets[j].t;
                    minV = j;
                }
            }
            visited[minV] = true;
            for (int j = 1; j <= n; j++) {
                if (!visited[j]) {
                    double t = v[j].t + (double)m[minV][j] / v[j].u + minT;
                    if (targets[j].t > t) {
                        targets[j].t = t;
                        targets[j].p = minV;
                    }
                }
            }
        }
        return targets;
    }
    
    private static void writeAnswer(BufferedWriter writer, int n, Target[] targets)
            throws IOException {
        double maxT = 0d;
        int targetV = 0;
        for (int i = 2; i <= n; i++) {
            if (maxT < targets[i].t) {
                maxT = targets[i].t;
                targetV = i;
            }
        }
        writer.write(String.format("%.5f\n", maxT));
        writer.write(Integer.toString(targetV));
        for (int i = targets[targetV].p; i != 0; i = targets[i].p) {
            writer.write(" " + i);
        }
        writer.write('\n');
    }
    
}
/*
0) отмечаю вершину 1 и нахожу t для оставшихся (n-1) вершин;
1) выбираю min из (n-1) непомеченных, остается (n-2) вершина;
2) прохожусь от (n-2) непомеченных вершин до выбранной,
    обновляя значения t, если потребуется; и т.д.
*/
