package com.github.druyaned.yandexalgorithms.train5.l4binsearch.p03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Saruman {
    
    private static final char[] BUFFER = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int m = readInt(reader);
        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = readInt(reader);
        }
        long[] sumTo = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            sumTo[i] = sumTo[i - 1] + a[i];
        }
        outer: for (int i = 0; i < m; i++) {
            int len = readInt(reader);
            long sum = readLong(reader);
            int leftStart = 0, rightStart = n - len;
            while (leftStart <= rightStart) {
                int midStart = (leftStart + rightStart) / 2;
                long midSum = sumTo[midStart + len] - sumTo[midStart];
                if (midSum == sum) {
                    writer.write(Integer.toString(midStart + 1) + "\n");
                    continue outer;
                } else if (midSum < sum) {
                    leftStart = midStart + 1;
                } else {
                    rightStart = midStart - 1;
                }
            }
            writer.write("-1\n");
        }
    }
    
    private static int readInt(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Integer.parseInt(new String(BUFFER, 0, bufferLength));
    }
    
    private static long readLong(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Long.parseLong(new String(BUFFER, 0, bufferLength));
    }
    
    private static int nextTokenInBuffer(Reader reader) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        BUFFER[0] = (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            BUFFER[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}
