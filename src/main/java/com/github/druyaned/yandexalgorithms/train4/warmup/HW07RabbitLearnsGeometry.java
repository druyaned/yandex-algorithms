package com.github.druyaned.yandexalgorithms.train4.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW07RabbitLearnsGeometry {
    
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
        int rows = readInt(reader);
        int columns = readInt(reader);
        int[][] bed = new int[rows + 2][columns + 2];
        for (int r = 1; r <= rows; ++r) {
            for (int c = 1; c <= columns; ++c) {
                bed[r][c] = readInt(reader);
            }
        }
        int maxSide = 0;
        for (int r = 1; r <= rows; ++r) {
            for (int c = 1; c <= columns; ++c) {
                int a1 = bed[r - 1][c - 1], a2 = bed[r - 1][c], a3 = bed[r][c - 1];
                if (bed[r][c] > 0 && a1 > 0 && a2 > 0 && a3 > 0) {
                    int minA = a1 < a2 ? a1 : a2;
                    minA = minA < a3 ? minA : a3;
                    bed[r][c] = minA + 1;
                }
                if (maxSide < bed[r][c]) {
                    maxSide = bed[r][c];
                }
            }
        }
        writer.write(maxSide + "\n");
    }
    
}
/*
input:
6 7
0 0 1 0 1 0 0
1 1 1 1 1 1 0
0 1 1 1 1 0 0
1 1 1 1 1 0 1
0 1 1 1 1 1 0
1 1 1 0 0 0 0
output:
4
*/