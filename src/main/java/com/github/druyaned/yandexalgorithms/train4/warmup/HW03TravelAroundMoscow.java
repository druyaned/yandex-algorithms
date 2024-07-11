package com.github.druyaned.yandexalgorithms.train4.warmup;

import static java.lang.Math.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW03TravelAroundMoscow {
    
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
    
    private static double ang(double x, double y) {
        if (x == 0D && y == 0D) {
            return 0D;
        }
        double r = sqrt(x * x + y * y);
        if (x >= 0D && y >= 0D) {
            return asin(y / r);
        }
        if (x < 0D && y >= 0D) {
            return PI - asin(y / r);
        }
        if (x < 0D && y < 0D) {
            return PI + asin(-y / r);
        }
        if (x >= 0D && y < 0D) {
            return 2D * PI -  asin(-y / r);
        }
        return -1D;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        double x1 = readInt(reader), y1 = readInt(reader);
        double x2 = readInt(reader), y2 = readInt(reader);
        double r1 = sqrt(x1 * x1 + y1 * y1);
        double r2 = sqrt(x2 * x2 + y2 * y2);
        if (r1 > r2) { // r_min = r1; r_max = r2
            double temp = x1;
            x1 = x2;
            x2 = temp;
            temp = y1;
            y1 = y2;
            y2 = temp;
            temp = r1;
            r1 = r2;
            r2 = temp;
        }
        if (r1 == 0D) {
            writer.write(String.format("%.6f", r2) + "\n");
            return;
        }
        double d1 = r1 + r2;
        double a1 = ang(x1, y1);
        double a2 = ang(x2, y2);
        double a = a2 > a1 ? a2 - a1 : a1 - a2;
        double d2 = r2 - r1 + a * r1;
        double d = d1 < d2 ? d1 : d2;
        writer.write(String.format("%.6f", d) + "\n");
    }
    
}
/*
Всего есть 2 варианта:
1) от A до центра и от центра до B;
2) от A до окружености B и после этого до самой B.

input:
2 3 3 4
output:
1.594551
*/