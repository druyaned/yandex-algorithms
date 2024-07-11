package com.github.druyaned.yandexalgorithms.train5.l1complexity.p05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProfitableStartup {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final long N = readInt(reader);
        final long K = readInt(reader);
        final int D = readInt(reader);
        for (long n = N * 10L; n <= N * 10L + 9; n++) {
            if (n % K == 0) {
                writer.write(Long.toString(n));
                for (int i = 0; i < D - 1; i++) {
                    writer.write('0');
                }
                writer.write('\n');
                return;
            }
        }
        writer.write("-1\n");
    }
    
    private static final char[] buf = new char[16];
    private static int readInt(Reader reader) throws IOException {
        int c;
        while ((c = reader.read()) != -1 && c != '+' && c != '-' && !('0' <= c && c <= '9')) {}
        if (c == -1) {
            throw new NumberFormatException("wrong or empty input");
        }
        buf[0] = (char)c;
        int l = 1;
        while ((c = reader.read()) != -1 && '0' <= c && c <= '9') {
            buf[l++] = (char)c;
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
}
