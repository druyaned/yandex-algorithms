package com.github.druyaned.yandexalgorithms.train5.l4binsearch.p04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Report {
    
    private static final char[] buffer = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int w = readInt(reader);
        int n = readInt(reader);
        int m = readInt(reader);
        int[] a = new int[n + 1];
        int[] b = new int[m + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = readInt(reader);
        }
        for (int i = 1; i <= m; i++) {
            b[i] = readInt(reader);
        }
        int left = 1;
        int right = w - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            int h1 = partHeight(mid, a, n);
            if (h1 != -1) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        int leftW1 = left;
        left = 1;
        right = w - 1;
        while (left < right) {
            int mid = (left + right + 1) / 2;
            int h2 = partHeight(w - mid, b, m);
            if (h2 != -1) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        int rightW1 = right;
        while (leftW1 < rightW1) {
            int midW1 = (leftW1 + rightW1) / 2;
            int h1 = partHeight(midW1, a, n);
            int h2 = partHeight(w - midW1, b, m);
            if (h1 <= h2) {
                rightW1 = midW1;
            } else {
                leftW1 = midW1 + 1;
            }
        }
        int w1 = leftW1;
        int h0 = partHeight(w1 - 1, a, n);
        int h1 = partHeight(w1, a, n);
        int h2 = partHeight(w - w1, b, m);
        int h = h0 != -1 && h1 < h2 ? Integer.min(h0, h2) : Integer.max(h1, h2);
        writer.write(Integer.toString(h) + "\n");
    }
    
    private static int partHeight(int w, int[] a, int n) {
        int h = 1;
        int len = a[1];
        if (len > w) {
            return -1;
        }
        for (int i = 2; i <= n; i++) {
            int newLen = len + 1 + a[i];
            if (newLen > w) {
                if (a[i] > w) {
                    return -1;
                }
                h++;
                len = a[i];
            } else {
                len = newLen;
            }
        }
        return h;
    }
    
    private static int readInt(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Integer.parseInt(new String(buffer, 0, bufferLength));
    }
    
    private static int nextTokenInBuffer(Reader reader) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        buffer[0] = (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            buffer[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}
