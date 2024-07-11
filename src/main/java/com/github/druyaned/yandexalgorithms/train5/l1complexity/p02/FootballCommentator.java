package com.github.druyaned.yandexalgorithms.train5.l1complexity.p02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FootballCommentator {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int match1Team1 = readInt(reader);
        int match1Team2 = readInt(reader);
        int match2Team1 = readInt(reader);
        int match2Team2 = readInt(reader);
        int match1Home1 = readInt(reader);
        int total1 = match1Team1 + match2Team1;
        int total2 = match1Team2 + match2Team2;
        int guest1 = match1Home1 == 1 ? match2Team1 : match1Team1;
        int guest2 = match1Home1 == 1 ? match1Team2 : match2Team2;
        if (total1 > total2 || total1 == total2 && guest1 > guest2) {
            writer.write("0\n");
            return;
        }
        int team1Needs = total2 - total1;
        int guest1Predict = match1Home1 == 1 ? guest1 + team1Needs : guest1;
        if (guest1Predict <= guest2) {
            team1Needs++;
        }
        writer.write(Integer.toString(team1Needs) + "\n");
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
