package com.github.druyaned.yandexalgorithms.train3.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW7SNTP {
    
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
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int DAY_SECONDS = 24 * 60 * 60;
        int a = readInt(reader) * 60 * 60 + readInt(reader) * 60 + readInt(reader);
        int b = readInt(reader) * 60 * 60 + readInt(reader) * 60 + readInt(reader);
        int c = readInt(reader) * 60 * 60 + readInt(reader) * 60 + readInt(reader);
        int totalDelay = c >= a ? c - a : DAY_SECONDS - a + c;
        int delay = (totalDelay - 1) / 2 + 1; // round
        int client = (b + delay) % DAY_SECONDS;
        int h = client / 60 / 60;
        int m = (client / 60) % 60;
        int s = client % 60;
        String clientTime = String.format("%1$02d:%2$02d:%3$02d\n", h, m, s);
        writer.write(clientTime);
    }
    
}
/*
Given:
a (клиент) - время отправления запроса;
b (сервер) - время получения запроса и отправления ответа;
c (клиент) - время получения ответа;
a -(d1)> b -(d2)> c; d1 = d2 = d (delay); 2 * d < DAY_SECONDS;
prec = 1 (sec), Math.round: {2.4 = 2; 2.5 = 3}.

Solution:
if (c >= a) {d = (c - a) / 2;}
else {d = (DAY_SECONDS - a + c) / 2;}
clientTime = b + d;
*/