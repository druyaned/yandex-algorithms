package druyaned.yandexalgorithms.train2.diva.lesson2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW5Lawn {
    
    private static final char[] buf = new char[16];
    
    private static int readInt(BufferedReader reader) throws IOException {
        int chVal = reader.read();
        while (chVal != -1 && chVal != '-' && (chVal < '0' || '9' < chVal)) {
            chVal = reader.read();
        }
        int l = 0;
        buf[l++] = (char)chVal;
        chVal = reader.read();
        while ('0' <= chVal && chVal <= '9') {
            buf[l++] = (char)chVal;
            chVal = reader.read();
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
        long x1 = readInt(reader);
        long y1 = readInt(reader);
        long x2 = readInt(reader);
        long y2 = readInt(reader);
        long x3 = readInt(reader);
        long y3 = readInt(reader);
        long r = readInt(reader);
        long cnt = 0;
        long yStart = y1 > y3 - r ? y1 : y3 - r;
        long yLimit = y2 < y3 + r ? y2 : y3 + r;
        for (long y = yStart; y <= yLimit; ++y) {
            long d1 = r * r;
            long d2 = (y - y3) * (y - y3);
            if (d1 >= d2) {
                long dx = (long)Math.sqrt(d1 - d2);
                long xLeft = x1 > -dx + x3 ? x1 : -dx + x3;
                long xRight = x2 < dx + x3 ? x2 : dx + x3;
                if (xRight >= xLeft) {
                    cnt += xRight - xLeft + 1;
                }
            }
        }
        writer.write(cnt + "\n");
    }
    
}
/*
input:
3 2 7 4
2 3 3
output:
7

Идея: прохожу по OY; для каждой координаты
нахожу xLeft и xRight и считаю количество cnt += xRight - xLeft + 1;
для этого реализовываю просетнкую функцию xDist(p3, y),
точнее left и right версии этой функции.
*/