package com.github.druyaned.yandexalgorithms.train5.l1complexity.p03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.IntUnaryOperator;

public class FileFormatting {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int TYPES = 4;
        int n = readInt(reader);
        IntUnaryOperator tapsByType = type -> type == 1 || type == 2 ? 2 : 1;
        long taps = 0L;
        for (int i = 0; i < n; i++) {
            int toAdd = readInt(reader);
            if (toAdd > 0) {
                int steps = (toAdd - 1) / TYPES;
                int type = (toAdd - 1) % TYPES;
                taps += steps + tapsByType.applyAsInt(type);
            }
        }
        writer.write(Long.toString(taps) + "\n");
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
TASK SOLVING

variants:
    space:      cnt += 1    (s)
    tab:        cnt += 4    (t)
    backspace:  cnt -= 1    (b)
needToAdd   taps    description
        0      0    
        1      1    s
        2      2    ss
        3      2    tb
        4      1    t

        5      2    ts
        6      3    tss
        7      3    ttb
        8      2    tt

        9      3    tts
       10      4    ttss
       ...
TYPES = 4
IntUnaryOperator tapsByType = type -> type == 1 || type == 2 ? 2 : 1;
if (a[i] > 0) {
    steps = (a[i] - 1) / TYPES
    type  = (a[i] - 1) % TYPES
    taps  = steps + tapsByType.applyAsInt(type);
}

EXAMPLE

input:
7
1 4 12 9 0 18 23
output:
21
*/
