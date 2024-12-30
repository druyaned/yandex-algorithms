package druyaned.yandexalgorithms.train3.l4dinamicprog2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW2MaximumCostRoute {
    
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
        int m = readInt(reader);
        int[][] table = new int[n + 2][m + 2];
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= m; ++j) {
                table[i][j] = readInt(reader);
            }
        }
        int[][] dp = new int[n + 2][m + 2];
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= m; ++j) {
                dp[i][j] = (dp[i-1][j] > dp[i][j-1] ? dp[i-1][j] : dp[i][j-1]) + table[i][j];
            }
        }
        char[] path = new char[n + m];
        int size = 0;
        for (int i = n, j = m; i != 1 || j != 1; ) {
            if (i == 1) {
                path[size++] = 'R';
                --j;
            } else if (j == 1) {
                path[size++] = 'D';
                --i;
            } else if (dp[i-1][j] > dp[i][j-1]) {
                path[size++] = 'D';
                --i;
            } else {
                path[size++] = 'R';
                --j;
            }
        }
        writer.write(dp[n][m] + "\n");
        if (size > 0) {
            writer.write(path[size - 1]);
        }
        for (int i = size - 1; i > 0; --i) {
            writer.write(" " + path[i - 1]);
        }
        writer.write('\n');
    }
    
}
