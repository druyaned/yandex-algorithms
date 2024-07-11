package com.github.druyaned.yandexalgorithms.train4.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW05MidLvl {
    
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
        int[] a = new int[n + 1];
        int[] sums = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            a[i] = readInt(reader);
            sums[i] = sums[i - 1] + a[i];
        }
        int lvl = a[1] * 1 - sums[1] + sums[n] - sums[1] - a[1] * (n - 1);
        writer.write(Integer.toString(lvl));
        for (int i = 2; i <= n; ++i) {
            lvl = a[i] * i - sums[i] + sums[n] - sums[i] - a[i] * (n - i);
            writer.write(" " + lvl);
        }
        writer.write('\n');
    }
    
}
/*
Задача на префиксные суммы, вроде как.
Сначала считаю суммы до i-го элемента.
Потом по формуле считаю суммарный уровень недовольства:
lvl = a[i] * i - sums[i] + sums[n] - sums[i] - a[i] * (n - i).

input:
6
1 2 3 3 4 5
output:
12 8 6 6 8 12
*/