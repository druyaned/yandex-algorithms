package com.github.druyaned.yandexalgorithms.train2.divb.lesson1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.YearMonth;

public class HW3Dates {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static boolean canBeDate(int day, int month, int year) {
        if (month < 1 || 12 < month) {
            return false;
        }
        int dayAmount = YearMonth.of(year, month).lengthOfMonth();
        return 1 <= day && day <= dayAmount;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int x1 = Integer.parseInt(elems[0]);
        int x2 = Integer.parseInt(elems[1]);
        int year = Integer.parseInt(elems[2]);
        boolean way1 = canBeDate(x1, x2, year);
        boolean way2 = canBeDate(x2, x1, year);
        if (way1 && !way2 || !way1 && way2 || x1 == x2) {
            writer.write("1\n");
        } else {
            writer.write("0\n");
        }
    }
    
}
