package com.github.druyaned.yandexalgorithms.train5.l1complexity.p04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BishopsAndRooks {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = 8;
        char[][] field = inputField(reader, n);
        showField(n, field);//show
        int[] bishops = new int[n * n];
        int[] rooks = new int[n * n];
        int bishopCount = 0, rookCount = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (field[i][j] == 'B') {
                    bishops[bishopCount++] = getPosition(n, i, j);
                }
                if (field[i][j] == 'R') {
                    rooks[rookCount++] = getPosition(n, i, j);
                }
            }
        }
        showBishops(n, bishopCount, bishops);//show
        showRooks(n, rookCount, rooks);//show
        for (int i = 0; i < bishopCount; i++) {
            int row = getRow(n, bishops[i]), column = getColumn(n, bishops[i]);
            goUpRight(n, field, row, column);
            goDownRight(n, field, row, column);
            goDownLeft(n, field, row, column);
            goUpLeft(field, row, column);
        }
        for (int i = 0; i < rookCount; i++) {
            int row = getRow(n, rooks[i]), column = getColumn(n, rooks[i]);
            goUp(field, row, column);
            goDown(n, field, row, column);
            goRight(n, field, row, column);
            goLeft(field, row, column);
        }
        showField(n, field);//show
        int freeCount = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (field[i][j] == '*') {
                    freeCount++;
                }
            }
        }
        System.out.printf("freeCount=%d\n", freeCount);//show
        writer.write(Integer.toString(freeCount) + "\n");
    }
    
    private static char[][] inputField(BufferedReader reader, int n) throws IOException {
        char[][] field = new char[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                field[i][j] = (char)reader.read();
            }
            int ch;
            while ((ch = reader.read()) != -1 && ch != '\n') {}
        }
        return field;
    }
    
    private static int getPosition(int n, int row, int column) {
        return (row - 1) * n + column - 1;
    }
    
    private static int getRow(int n, int position) {
        return position / n + 1;
    }
    
    private static int getColumn(int n, int position) {
        return position % n + 1;
    }
    
    private static void goUp(char[][] field, int row, int column) {
        for (int i = row - 1; i > 0 && field[i][column] != 'B' && field[i][column] != 'R'; i--) {
            field[i][column] = '#';
        }
    }
    
    private static void goDown(int n, char[][] field, int row, int column) {
        for (int i = row + 1; i <= n && field[i][column] != 'B' && field[i][column] != 'R'; i++) {
            field[i][column] = '#';
        }
    }
    
    private static void goRight(int n, char[][] field, int row, int column) {
        for (int j = column + 1; j <= n && field[row][j] != 'B' && field[row][j] != 'R'; j++) {
            field[row][j] = '#';
        }
    }
    
    private static void goLeft(char[][] field, int row, int column) {
        for (int j = column - 1; j > 0 && field[row][j] != 'B' && field[row][j] != 'R'; j--) {
            field[row][j] = '#';
        }
    }
    
    private static void goUpRight(int n, char[][] field, int row, int column) {
        for (int i = row - 1, j = column + 1; i > 0 && j <= n &&
                field[i][j] != 'B' && field[i][j] != 'R'; i--, j++) {
            field[i][j] = '#';
        }
    }
    
    private static void goDownRight(int n, char[][] field, int row, int column) {
        for (int i = row + 1, j = column + 1; i <= n && j <= n &&
                field[i][j] != 'B' && field[i][j] != 'R'; i++, j++) {
            field[i][j] = '#';
        }
    }
    
    private static void goDownLeft(int n, char[][] field, int row, int column) {
        for (int i = row + 1, j = column - 1; i <= n && j > 0 &&
                field[i][j] != 'B' && field[i][j] != 'R'; i++, j--) {
            field[i][j] = '#';
        }
    }
    
    private static void goUpLeft(char[][] field, int row, int column) {
        for (int i = row - 1, j = column - 1; i > 0 && j > 0 &&
                field[i][j] != 'B' && field[i][j] != 'R'; i--, j--) {
            field[i][j] = '#';
        }
    }
    
    private static void showField(int n, char[][] field) {
        System.out.println("field:");
        for (int i = 1; i <= n; i++) {
            System.out.printf("%c", field[i][1]);
            for (int j = 2; j <= n; j++) {
                System.out.printf(" %c", field[i][j]);
            }
            System.out.println();
        }
    }
    
    private static void showBishops(int n, int bishopCount, int[] bishops) {
        System.out.print("bishops:");
        for (int i = 0; i < bishopCount; i++) {
            System.out.printf(" (%d %d)", getRow(n, bishops[i]), getColumn(n, bishops[i]));
        }
        System.out.println();
    }
    
    private static void showRooks(int n, int rookCount, int[] rooks) {
        System.out.print("rooks:");
        for (int i = 0; i < rookCount; i++) {
            System.out.printf(" (%d %d)", getRow(n, rooks[i]), getColumn(n, rooks[i]));
        }
        System.out.println();
    }
    
}
