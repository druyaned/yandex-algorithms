package druyaned.yandexalgorithms.train4.l4bruteforce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3MaximumCut {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader); // n in [2, 20];
        Vertex[] v = readVertices(reader, n);
        int maxSum = 0;
        int maxBitMask = 0;
        int lim = 1 << (n - 1);
        for (int bitmask = 1; bitmask < lim; bitmask++) {
            int sum = 0;
            for (int i = 1; i <= n; i++) {
                if (inCut(bitmask, i)) {
                    for (int j = 0; j < v[i].l; j++) {
                        if (!inCut(bitmask, v[i].d[j])) {
                            sum += v[i].w[j];
                        }
                    }
                }
            }
            if (maxSum < sum) {
                maxSum = sum;
                maxBitMask = bitmask;
            }
        }
        writeAnswer(writer, n, maxSum, maxBitMask);
    }
    
    private static class Vertex {
        public final int id, l;
        public final int d[]; // descendants
        public final int w[]; // weights
        public Vertex(int id, int l) {
            this.id = id;
            this.l = l;
            d = new int[l];
            w = new int[l];
        }
    }
    
    private static Vertex[] readVertices(BufferedReader reader, int n) throws IOException {
        Vertex[] v = new Vertex[n + 1];
        int[] inputWeights = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int l = 0;
            for (int j = 1; j <= n; j++) {
                inputWeights[j] = readInt(reader);
                if (inputWeights[j] != 0) {
                    l++;
                }
            }
            if (l != 0) {
                v[i] = new Vertex(i, l);
                for (int j = 1, ind = 0; j <= n; j++) {
                    if (inputWeights[j] != 0) {
                        v[i].d[ind] = j;
                        v[i].w[ind++] = inputWeights[j];
                    }
                }
            }
        }
        return v;
    }
    
    private static boolean inCut(final int bitmask, final int i) {
        return (bitmask & (1 << (i - 1))) > 0;
    }
    
    private static void writeAnswer(BufferedWriter writer, int n,
            int maxSum, int maxBitMask) throws IOException {
        writer.write(Integer.toString(maxSum));
        int[] cut = new int[n];
        for (int i = 0; i < n; i++) {
            if ((maxBitMask & (1 << i)) > 0) {
                cut[i] = 2;
            } else {
                cut[i] = 1;
            }
        }
        writer.write("\n" + cut[0]);
        for (int i = 1; i < n; i++) {
            writer.write(" " + cut[i]);
        }
        writer.write('\n');
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
Надо разделить вершины по долям 0 и 1 так, чтобы сумма ребер,
соединяющих вершины из разных долей, была максимальна.
Допустим, n = 3. Тогда все возможные варианты
распределений будут такими:
000, 001, 010, 011, 100, 101, 110, 111.
Ну это просто четырехзначное число в двоичной СС.
Для n = 20 возможных вариантов будет 2^20 = 1048576.
Т.е. для каждого набора я должен буду перебрать все возможные варианты.
Максимальное количество ребер max(m) = n * (n - 1) / 2.
Т.е. сложность будет O(n^2 * 2^n).
*/