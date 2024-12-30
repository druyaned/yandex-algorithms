package druyaned.yandexalgorithms.train5.l2linearsearch.p02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FishSeller {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int days = readInt(reader);
        int shelfLife = readInt(reader);
        int[] prices = new int[days + 1];
        for (int day = 1; day <= days; day++) {
            prices[day] = readInt(reader);
        }
        int maxProfit = 0;
        for (int day = 1; day <= days; day++) {
            int maxNextDay = Integer.min(day + shelfLife, days);
            int maxPrice = prices[day];
            for (int nextDay = day + 1; nextDay <= maxNextDay; nextDay++) {
                if (maxPrice < prices[nextDay]) {
                    maxPrice = prices[nextDay];
                }
            }
            int profit = maxPrice - prices[day];
            if (maxProfit < profit) {
                maxProfit = profit;
            }
        }
        writer.write(Integer.toString(maxProfit) + "\n");
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
надо найти такой j in [i, i + k], что (a[j] - a[i]) будет максимальной и > 0
ну то есть надо найти максимум на отрезке [i, i + k]
сложность, увы, O(n * k)
*/
