package com.github.druyaned.yandexalgorithms.train5.l2linearsearch.p05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AmbitiousSnail {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int berryCount = readInt(reader);
        long[] up = new long[berryCount + 1];
        long[] down = new long[berryCount + 1];
        long[] difference = new long[berryCount + 1];
        for (int i = 1; i <= berryCount; i++) {
            up[i] = readInt(reader);
            down[i] = readInt(reader);
            difference[i] = up[i] - down[i];
        }
        long maxPosAtDayEnd = 0;
        for (int i = 1; i <= berryCount; i++) {
            if (difference[i] > 0) {
                maxPosAtDayEnd += difference[i];
            }
        }
        long maxPos = maxPosAtDayEnd;
        int[] berrySequence = new int[berryCount];
        int size = 0;
        int changeIndex = -1;
        for (int i = 1; i <= berryCount; i++) {
            if (difference[i] > 0) {
                long newMaxPos = maxPosAtDayEnd + down[i];
                if (maxPos < newMaxPos) {
                    maxPos = newMaxPos;
                    changeIndex = i;
                }
            }
        }
        for (int i = 1; i <= berryCount; i++) {
            if (i != changeIndex && difference[i] > 0) {
                berrySequence[size++] = i;
            }
        }
        if (changeIndex != -1) {
            berrySequence[size++] = changeIndex;
        }
        changeIndex = -1;
        for (int i = 1; i <= berryCount; i++) {
            if (difference[i] <= 0) {
                long newMaxPos = maxPosAtDayEnd + up[i];
                if (maxPos < newMaxPos) {
                    maxPos = newMaxPos;
                    changeIndex = i;
                }
            }
        }
        if (changeIndex != -1) {
            berrySequence[size++] = changeIndex;
        }
        for (int i = 1; i <= berryCount; i++) {
            if (i != changeIndex && difference[i] <= 0) {
                berrySequence[size++] = i;
            }
        }
        writer.write(Long.toString(maxPos) + "\n");
        writer.write(Integer.toString(berrySequence[0]));
        for (int i = 1; i < size; i++) {
            writer.write(" " + berrySequence[i]);
        }
        writer.write('\n');
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
Путь к идее решения:
1) придумывал пример на ходу;
2) нарисовал график движения Пети от времени (получились горочки);
3) под графиком написал значения: u (up), d (down), f (difference);
4) и тут родилась идея:
    4.1) прохожу по всем {f > 0} и записываю maxPosAtDayEnd;
    4.2) прохожу по всем {f > 0},
        обновляя, если нужно, maxPos значением {maxPosAtDayEnd + d[i]};
    4.3) прохожу по всем {f <= 0},
        обновляя, если нужно, maxPos значением {maxPosAtDayEnd + u[i]};
    4.4) answer == maxPos.

input:
7
2 1
3 2
2 2
1 0
2 3
1 2
6 6
output:
9
1 4 2 7 3 5 6
*/
