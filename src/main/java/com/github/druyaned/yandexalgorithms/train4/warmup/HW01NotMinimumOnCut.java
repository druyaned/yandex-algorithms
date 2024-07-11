package com.github.druyaned.yandexalgorithms.train4.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW01NotMinimumOnCut {
    
    private static final char[] buf = new char[16];
    
    private static int readInt(BufferedReader reader) throws IOException {
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
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader); // seq len
        int m = readInt(reader); // req cnt
        int[] a = new int[n]; // a[i] in [0, 1000]
        for (int i = 0; i < n; ++i) {
            a[i] = readInt(reader);
        }
        for (int i = 0; i < m; ++i) {
            int l = readInt(reader);
            int r = readInt(reader);
            int minElem = a[l];
            for (int j = l; j <= r; ++j) { // 1000 * 100 = 10^5
                if (minElem > a[j]) {
                    minElem = a[j];
                }
            }
            boolean found = false;
            for (int j = l; j <= r; ++j) {
                if (a[j] > minElem) {
                    writer.write(a[j] + "\n");
                    found = true;
                    break;
                }
            }
            if (!found) {
                writer.write("NOT FOUND\n");
            }
        }
    }
    
}
