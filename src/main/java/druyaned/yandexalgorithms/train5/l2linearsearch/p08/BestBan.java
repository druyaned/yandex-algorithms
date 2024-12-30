package druyaned.yandexalgorithms.train5.l2linearsearch.p08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BestBan {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int rowCount = readInt(reader);
        int columnCount = readInt(reader);
        int[][] valueTable = new int[rowCount + 1][columnCount + 1];
        int max1Row = 0, max1Column = 0, max1Value = 0;
        for (int r = 1; r <= rowCount; r++) {
            for (int c = 1; c <= columnCount; c++) {
                valueTable[r][c] = readInt(reader);
                if (max1Value < valueTable[r][c]) {
                    max1Row = r;
                    max1Column = c;
                    max1Value = valueTable[r][c];
                }
            }
        }
        int max2Row = 0, max2Column = 0, max2Value = 0;
        for (int r = 1; r <= rowCount; r++) {
            if (r != max1Row) {
                for (int c = 1; c <= columnCount; c++) {
                    if (c != max1Column && max2Value < valueTable[r][c]) {
                        max2Row = r;
                        max2Column = c;
                        max2Value = valueTable[r][c];
                    }
                }
            }
        }
        int max3Row1Value = 0;
        int max3Column1Value = 0;
        for (int r = 1; r <= rowCount; r++) {
            if (r != max2Row) {
                for (int c = 1; c <= columnCount; c++) {
                    if (c != max1Column && max3Row1Value < valueTable[r][c]) {
                        max3Row1Value = valueTable[r][c];
                    }
                }
            }
            if (r != max1Row) {
                for (int c = 1; c <= columnCount; c++) {
                    if (c != max2Column && max3Column1Value < valueTable[r][c]) {
                        max3Column1Value = valueTable[r][c];
                    }
                }
            }
        }
        int rowToRemove = max1Row;
        int columnToRemove = max1Column;
        if (max2Value >= max3Row1Value || max2Value >= max3Column1Value) {
            if (max3Row1Value >= max3Column1Value) {
                columnToRemove = max2Column;
            } else {
                rowToRemove = max2Row;
            }
        }
        writer.write(String.format("%d %d\n", rowToRemove, columnToRemove));
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
Идея решения:
1)  ищу максимум среди всех: m1;
2)  ищу 2-ой максимум: m2 (r != r1 && c != c1);
3)  рассматриваю 3 точки: (r1 c1), (r1 c2), (r2 c1);
    там, где найду наименьший 3-ий максимум, там и ответ.
*/
