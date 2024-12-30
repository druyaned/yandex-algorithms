package druyaned.yandexalgorithms.train2.divb.lesson1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4BuildingSchool {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static long[] getHomes(BufferedReader reader, int n) throws IOException {
        long[] homes = new long[n];
        StringBuilder builder = new StringBuilder();
        int chVal, i = 0;
        while ((chVal = reader.read()) != -1) {
            char ch = (char)chVal;
            if ('0' <= ch && ch <= '9' || ch == '-') {
                builder.append(ch);
            } else if (builder.length() != 0) {
                homes[i++] = Long.parseLong(builder.toString());
                builder.setLength(0);
            }
        }
        return homes;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        long[] homes = getHomes(reader, n);
        // solve
        long left = homes[0], right = homes[n - 1], mid;
        while (left <= right) {
            mid = (left + right) / 2;
            long nextMid = mid + 1;
            long sum = 0, nextSum = 0;
            for (int i = 0; i < n; ++i) {
                sum += mid < homes[i] ? homes[i] - mid : mid - homes[i];
                nextSum += nextMid < homes[i] ? homes[i] - nextMid : nextMid - homes[i];
            }
            if (sum < nextSum) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        writer.write(left + "\n");
    }
    
}
/*
input:
5
-5 2 4 9 17
output:
4
*/