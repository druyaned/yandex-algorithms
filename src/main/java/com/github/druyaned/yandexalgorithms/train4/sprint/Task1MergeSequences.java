package com.github.druyaned.yandexalgorithms.train4.sprint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Task1MergeSequences {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
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
    
    private static long[] merge(int n) {
        long c[] = new long[n + 1];
        long x1 = 1L, x2 = 1L;
        long p1 = (x1 * x1), p2 = (x2 * x2 * x2);
        for (int i = 1; i <= n; ++i) {
            if (p1 == p2) {
                c[i] = p1;
                x1++;
                x2++;
                p1 = (x1 * x1);
                p2 = (x2 * x2 * x2);
            } else if (p1 < p2) {
                c[i] = p1;
                x1++;
                p1 = (x1 * x1);
            } else {
                c[i] = p2;
                x2++;
                p2 = (x2 * x2 * x2);
            }
        }
        return c;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = (int)1e7;
        long[] c = merge(n);
        int x = readInt(reader);
        writer.write(c[x] + "\n");
    }
    
}
