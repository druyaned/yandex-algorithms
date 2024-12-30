package druyaned.yandexalgorithms.train5.l1complexity.p08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class RunningAroundStadium {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int l = readInt(reader);
        double x1 = readInt(reader);
        double u1 = readInt(reader);
        double x2 = readInt(reader);
        double u2 = readInt(reader);
        if (x1 == x2) {
            writer.write("YES\n");
            writer.write("0.0\n");
            return;
        }
        if (u1 == 0.0 && u2 == 0.0) {
            writer.write("NO\n");
            return;
        }
        int n = 10;
        double[] t = new double[n];
        Arrays.fill(t, -1.0);
        if (u1 != -u2) {
            t[0] = (+2.0 * l - (x1 + x2)) / (u1 + u2);
            t[1] = (-2.0 * l - (x1 + x2)) / (u1 + u2);
            t[2] = (+l - (x1 + x2)) / (u1 + u2);
            t[3] = (-l - (x1 + x2)) / (u1 + u2);
            t[4] = -(x1 + x2) / (u1 + u2);
        }
        if (u1 != u2) {
            t[5] = (+2.0 * l - (x1 - x2)) / (u1 - u2);
            t[6] = (-2.0 * l - (x1 - x2)) / (u1 - u2);
            t[7] = (+l - (x1 - x2)) / (u1 - u2);
            t[8] = (-l - (x1 - x2)) / (u1 - u2);
            t[9] = -(x1 - x2) / (u1 - u2);
        }
        double minT = t[0];
        for (int i = 1; i < n; i++) {
            if (minT < t[i]) {
                minT = t[i];
            }
        }
        for (int i = 0; i < n; i++) {
            if (t[i] >= 0.0 && minT > t[i]) {
                minT = t[i];
            }
        }
        if (minT >= 0.0) {
            writer.write("YES\n");
            writer.write(String.format("%.10f\n", minT));
        } else {
            writer.write("NO\n");
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
    
}
