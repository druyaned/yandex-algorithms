package com.github.druyaned.yandexalgorithms.train3.l3dinamicprog1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4Tickets {
    
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
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int[] a = n < 4 ? new int[4] : new int[n + 1];
        int[] b = n < 4 ? new int[4] : new int[n + 1];
        int[] c = n < 4 ? new int[4] : new int[n + 1];
        int[] dp = n < 4 ? new int[4] : new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            a[i] = readInt(reader);
            b[i] = readInt(reader);
            c[i] = readInt(reader);
        }
        dp[1] = a[1];
        dp[2] = a[2] + dp[1];
        if (dp[2] > b[1]) {
            dp[2] = b[1];
        }
        dp[3] = a[3] + dp[2];
        if (dp[3] > b[2] + dp[1]) {
            dp[3] = b[2] + dp[1];
        }
        if (dp[3] > c[1]) {
            dp[3] = c[1];
        }
        for (int i = 4; i <= n; ++i) {
            dp[i] = a[i] + dp[i - 1];
            if (dp[i] > b[i - 1] + dp[i - 2]) {
                dp[i] = b[i - 1] + dp[i - 2];
            }
            if (dp[i] > c[i - 2] + dp[i - 3]) {
                dp[i] = c[i - 2] + dp[i - 3];
            }
        }
        writer.write(dp[n] + "\n");
    }
    
}
/*
input:
5
5 10 15
2 10 15
5 5 5
20 20 1
20 1 1
output:
12

Как попасть в dp[i]?
    from dp[i-1] if a[i-1]
    from dp[i-2] if b[i-2]
    from dp[i-3] if c[i-3]
*/
