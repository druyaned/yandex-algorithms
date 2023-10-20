package com.github.druyaned.yandexalgorithms.train2.diva.lesson2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3ChessBoard {
    
    private static final char[] buf = new char[16];
    
    private static int readInt(BufferedReader reader) throws IOException {
        int chVal = reader.read();
        while (chVal != -1 && chVal != '-' && (chVal < '0' || '9' < chVal)) {
            chVal = reader.read();
        }
        int l = 0;
        buf[l++] = (char)chVal;
        chVal = reader.read();
        while ('0' <= chVal && chVal <= '9') {
            buf[l++] = (char)chVal;
            chVal = reader.read();
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void showBoard(boolean[][] board, final int SIDE_SIZE) {
        for (int y = 1; y <= SIDE_SIZE; ++y) {
            for (int x = 1; x <= SIDE_SIZE; ++x) {
                if (board[y][x]) {
                    System.out.print(" O");
                } else {
                    System.out.print(" *");
                }
            }
            System.out.println();
        }
    }
    
    private static int openSideCount(boolean[][] board, final int SIDE_SIZE, int y, int x) {
        int cnt = 0;
        if (!board[y + 1][x]) {
            ++cnt;
        }
        if (!board[y][x + 1]) {
            ++cnt;
        }
        if (!board[y - 1][x]) {
            ++cnt;
        }
        if (!board[y][x - 1]) {
            ++cnt;
        }
        return cnt;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        final int SIDE_SIZE = 8;
        boolean[][] board = new boolean[SIDE_SIZE + 2][SIDE_SIZE + 2];
        for (int i = 0; i < n; ++i) {
            int y = readInt(reader);
            int x = readInt(reader);
            board[y][x] = true;
        }
        int p = 0;
        for (int y = 1; y <= SIDE_SIZE; ++y) {
            for (int x = 1; x <= SIDE_SIZE; ++x) {
                if (board[y][x]) {
                    p += openSideCount(board, SIDE_SIZE, y, x);
                }
            }
        }
        writer.write(p + "\n");
    }
    
}
