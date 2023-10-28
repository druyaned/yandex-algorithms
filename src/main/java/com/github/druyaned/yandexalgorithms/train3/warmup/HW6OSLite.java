package com.github.druyaned.yandexalgorithms.train3.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW6OSLite {
    
    private static final char[] buf = new char[16];
    
    private static int readInt(BufferedReader reader) throws IOException {
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
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Cut {
        public final int in, out;
        public boolean active;
        public Cut(int in, int out) {
            this.in = in;
            this.out = out;
            this.active = true;
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        readInt(reader); // m
        int n = readInt(reader);
        Cut[] cuts = new Cut[n];
        for (int i = 0; i < n; ++i) {
            Cut cut = new Cut(readInt(reader), readInt(reader));
            for (int j = 0; j < i; ++j) {
                boolean befor = cuts[j].in < cut.in && cuts[j].out < cut.in;
                boolean after = cuts[j].in > cut.out && cuts[j].out > cut.out;
                if (!befor && !after) {
                    cuts[j].active = false;
                }
            }
            cuts[i] = cut;
        }
        int cnt = 0;
        for (int i = 0; i < n; ++i) {
            if (cuts[i].active) {
                ++cnt;
            }
        }
        writer.write(cnt + "\n");
    }
    
}
/*
Сложность n^2.
Сортировка не нужна из-за важности очередности ввода.
При добавлении нового отрезка те отрезки,
любая часть которых оказались между in и out, затираются.
То есть,
если предыдущий отрезок не полностью перед или за только что введенным,
предыдущий затирается.

12345678901234
    [   ]
[ ]
         [  ]
     [       ]
input:
14
4
5 9
1 3
10 13
6 14
output:
2

12345678901234
     [       ]
    [   ]
[ ]
         [  ]
input:
14
4
6 14
5 9
1 3
10 13
output:
3
*/