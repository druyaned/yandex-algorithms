// https://contest.yandex.ru/contest/59541/problems/
package com.github.druyaned.yandexalgorithms.train5.l3setmap.p01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Playlists {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int MAX_SIZE = (int)2e6;
        int peopleCount = readInt(reader);
        HashMap<String, Integer> trackToCount = new HashMap(MAX_SIZE);
        int trackCount = readInt(reader);
        for (int j = 0; j < trackCount; j++) {
            trackToCount.put(readToken(reader), 1);
        }
        for (int i = 1; i < peopleCount; i++) {
            trackCount = readInt(reader);
            for (int j = 0; j < trackCount; j++) {
                String track = readToken(reader);
                Integer count = trackToCount.get(track);
                if (count != null) {
                    trackToCount.put(track, count + 1);
                }
            }
        }
        ArrayList<String> tracksAllLike = new ArrayList(MAX_SIZE);
        trackToCount.forEach((t, c) -> {
            if (c == peopleCount) {
                tracksAllLike.add(t);
            }
        });
        tracksAllLike.sort(String::compareTo);
        writer.write(Integer.toString(tracksAllLike.size()) + "\n");
        if (!tracksAllLike.isEmpty()) {
            writer.write(tracksAllLike.get(0));
        }
        for (int i = 1; i < tracksAllLike.size(); i++) {
            writer.write(" " + tracksAllLike.get(i));
        }
        writer.write('\n');
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
    
    private static String readToken(Reader reader) throws IOException {
        int c;
        while ((c = reader.read()) != -1 && (c == ' ' || c == '\n')) {}
        if (c == -1) {
            return null;
        }
        buf[0] = (char)c;
        int l = 1;
        while ((c = reader.read()) != -1 && c != ' ' && c != '\n') {
            buf[l++] = (char)c;
        }
        return new String(buf, 0, l);
    }
    
}
