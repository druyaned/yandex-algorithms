package druyaned.yandexalgorithms.train4.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW10GroupProject {
    
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
        int t = readInt(reader);
        for (int i = 0; i < t; ++i) {
            long number = readInt(reader);
            long divider = readInt(reader);
            long difference = readInt(reader) - divider;
            long integer = number / divider;
            long remainder = number % divider;
            if (difference * integer >= remainder) {
                writer.write("YES\n");
            } else {
                writer.write("NO\n");
            }
        }
    }
    
}
/*
number / divider = integer + remainder / divider;
difference = b - a;
if (difference * integer >= remainder) {good = true;}
else {good = false;}

23: [7 9]
23 / 7 = 3 + 2 / 7;
7 + 8 + 8 | YES

19: [7 9]
19 / 7 = 2 + 5 / 7
9 + 9 | NO

input:
6
10 2 3
11 7 8
28 4 6
3 1 2
19 7 9
23 7 9
output:
YES
NO
YES
YES
NO
YES
*/