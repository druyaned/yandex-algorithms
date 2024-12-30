package druyaned.yandexalgorithms.train4.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW06Elevator {
    
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
        int k = readInt(reader);
        int n = readInt(reader);
        int[] ppls = new int[n + 1];
        for (int lvl = 1; lvl <= n; ++lvl) {
            ppls[lvl] = readInt(reader);
        }
        int free = k;
        int elevator = 0;
        BigInteger moves = BigInteger.ZERO;
        for (int lvl = n; lvl >= 1; --lvl) {
            if (ppls[lvl] >= free) {
                ppls[lvl] -= free;
                free = k;
                moves = moves.add(BigInteger.valueOf(elevator > lvl ? elevator : 2 * lvl - elevator));
                elevator = 0; 
            }
            moves = moves.add(BigInteger.valueOf((ppls[lvl] / k) * 2L * lvl));
            ppls[lvl] %= k;
            if (ppls[lvl] > 0) {
                moves = moves.add(BigInteger.valueOf(elevator > lvl ? elevator - lvl : lvl - elevator));
                elevator = lvl;
                free -= ppls[lvl];
                ppls[lvl] = 0;
            }
        }
        if (elevator != 0) {
            moves = moves.add(BigInteger.valueOf(elevator));
        }
        writer.write(moves.toString() + "\n");
    }
    
}
/*
input:
2
3
6 7 8
output:
46

input:
2
3
7 8 9
output:
52
*/