package druyaned.yandexalgorithms.train5.l2linearsearch.p09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import static java.lang.Math.abs;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PiratesOfBarentsSea {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int[] shipRows = new int[n];
        int[] shipColumns = new int[n];
        for (int i = 0; i < n; i++) {
            shipRows[i] = readInt(reader);
            shipColumns[i] = readInt(reader);
        }
        int[] columnShipCounts = new int[n + 1];
        int[] rowShipCounts = new int[n + 1];
        for (int i = 0; i < n; i++) {
            columnShipCounts[shipColumns[i]]++;
            rowShipCounts[shipRows[i]]++;
        }
        int minRowMoves = n * n;
        for (int i = 1; i <= n; i++) {
            int rowMoves = 0;
            for (int c = 1; c <= n; c++) {
                rowMoves += columnShipCounts[c] * abs(i - c);
            }
            if (minRowMoves > rowMoves) {
                minRowMoves = rowMoves;
            }
        }
        int columnMoves = 0;
        for (int r = 1; r < n; r++) {
            int addNext = rowShipCounts[r] - 1;
            rowShipCounts[r + 1] += addNext;
            columnMoves += abs(addNext);
        }
        writer.write(Integer.toString(minRowMoves + columnMoves) + "\n");
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
4|1 * * 3
3|2 * * *
2|* * 4 *
1|* * * *
-+-------
 |1 2 3 4

c=1: 2[2] + 2[1]

Решение:
1)  нахожу minRowMoves для столбцов:
        columnShipCounts: 2 0 1 1
        c=1: 2*0 + 0*1 + 1*2 + 1*3 = 5 // minRowSteps
        c=2: 2*1 + 0*0 + 1*1 + 1*2 = 5
        c=3: 2*2 + 0*1 + 1*0 + 1*1 = 5
        c=3: 2*3 + 0*2 + 1*1 + 1*0 = 7
2)  нахожу columnMoves для строк:
        rowShipCounts: 0 1 1 2
        r[1]: 0 => r[2] += (r[1] - 1); columnMoves += abs(r[1] - 1)
        r[2]: 0 => r[3] += (r[2] - 1); columnMoves += abs(r[2] - 1)
        r[3]: 0 => r[4] += (r[3] - 1); columnMoves += abs(r[3] - 1)
        r[4]: 1
3)  totalMoves = minRowMoves + columnMoves.
*/
