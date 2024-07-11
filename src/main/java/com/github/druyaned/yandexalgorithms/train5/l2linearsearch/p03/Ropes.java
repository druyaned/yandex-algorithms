package com.github.druyaned.yandexalgorithms.train5.l2linearsearch.p03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Ropes {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int ropeCount = readInt(reader);
        int[] ropes = new int[ropeCount];
        for (int i = 0; i < ropeCount; i++) {
            ropes[i] = readInt(reader);
        }
        int maxRope = ropes[0];
        int maxRopeIndex = 0;
        for (int i = 1; i < ropeCount; i++) {
            if (maxRope < ropes[i]) {
                maxRope = ropes[i];
                maxRopeIndex = i;
            }
        }
        int sumOthers = 0;
        for (int i = 0; i < ropeCount; i++) {
            if (i != maxRopeIndex) {
                sumOthers += ropes[i];
            }
        }
        int mashaTook = sumOthers < maxRope ? maxRope - sumOthers : maxRope + sumOthers;
        writer.write(Integer.toString(mashaTook) + "\n");
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
Итак, на столе могут быть какие веревочки?
    1) 1 большая длиной l, и тогда все остальные в сумме дадут {l - x}, где x > 0;
    2) маленькие отрезки в сумме l, ведь Маша забрала большую длиной l.
Надо найти min(x), где x - длина веревочки, которую взяла Маша.
Сначала рассмотрю 1-ый случай. Нахожу максимум: l.
    Потом оставшиеся (n - 1) суммирую: sum. Если sum < l, то такой варик возможен
    и надо юзать, пока можно (x = l - sum).
Иначе рассмотрю 2-й случай. Суммирую всех и {x = sum}.
*/