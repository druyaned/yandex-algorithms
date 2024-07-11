package com.github.druyaned.yandexalgorithms.train5.l1complexity.p09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

public class Schedule {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int DAYS_IN_WEEK = 7;
        final int WEEK_COUNT = 365 / 7; // 366 / 7 == 365 / 7
        int n = readInt(reader);
        int year = readInt(reader);
        int[] busyDayCounts = new int[DAYS_IN_WEEK + 1];
        Arrays.fill(busyDayCounts, WEEK_COUNT);
        int firstDay = LocalDate.of(year, 1, 1).getDayOfWeek().getValue();
        busyDayCounts[firstDay]++;
        if (Year.isLeap(year)) {
            int secondDay = (firstDay % DAYS_IN_WEEK) + 1;
            busyDayCounts[secondDay]++;
        }
        for (int i = 0; i < n; i++) {
            int day = readInt(reader);
            int month = Month.valueOf(reader.readLine().toUpperCase()).getValue();
            busyDayCounts[LocalDate.of(year, month, day).getDayOfWeek().getValue()]--;
        }
        reader.readLine(); // firstDay
        int minCount = busyDayCounts[1];
        int minDay = 1;
        int maxCount = busyDayCounts[1];
        int maxDay = 1;
        for (int i = 2; i <= DAYS_IN_WEEK; i++) {
            if (minCount > busyDayCounts[i]) {
                minCount = busyDayCounts[i];
                minDay = i;
            }
            if (maxCount < busyDayCounts[i]) {
                maxCount = busyDayCounts[i];
                maxDay = i;
            }
        }
        String easyDay = DayOfWeek.of(maxDay).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String hardDay = DayOfWeek.of(minDay).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        writer.write(String.format("%s %s\n", easyDay, hardDay));
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
