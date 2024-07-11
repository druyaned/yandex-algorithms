package com.github.druyaned.yandexalgorithms.train5.l2linearsearch.p10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TwoRectangles {
    
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
        int[][] field = new int[rowCount + 2][columnCount + 2];
        for (int r = 1; r <= rowCount; r++) {
            for (int c = 1; c <= columnCount; c++) {
                char ch = (char)reader.read();
                if (ch == '#') {
                    field[r][c] = -1; // -1 => raw cell, 1 => 1st rect, 2 => 2nd rect
                }
            }
            reader.read(); // new line
        }
        showField(rowCount, columnCount, field);//show
        int rectangleCount = 0;
        for (int r = 1; r <= rowCount; r++) {
            for (int c = 1; c <= columnCount; c++) {
                if (field[r][c] == -1) {
                    if (rectangleCount >= 2) {
                        writer.write("NO\n");
                        System.out.print("NO\n");//show
                        return;
                    }
                    markRectangle(rowCount, columnCount, field, r, c, ++rectangleCount);
                    System.out.println();//show
                    showField(rowCount, columnCount, field);//show
                }
            }
        }
        System.out.println();//show
        showField(rowCount, columnCount, field);//show
        if (rectangleCount == 0) {
            writer.write("NO\n");
            System.out.print("NO\n");//show
            return;
        }
        if (rectangleCount == 1) {
            if (!divideRectangle(rowCount, columnCount, field)) {
                writer.write("NO\n");
                System.out.print("NO\n");//show
                return;
            }
        }
        writer.write("YES\n");
        System.out.print("YES\n");//show
        for (int r = 1; r <= rowCount; r++) {
            for (int c = 1; c <= columnCount; c++) {
                switch (field[r][c]) {
                    case 1 -> {
                        writer.write('a');
                        System.out.print('a');//show
                    }
                    case 2 -> {
                        writer.write('b');
                        System.out.print('b');//show
                    }
                    default -> {
                        writer.write('.');
                        System.out.print('.');//show
                    }
                }
            }
            writer.write('\n');
            System.out.print('\n');//show
        }
    }
    
    private static void markRectangle(int rowCount, int columnCount, int[][] field,
            int row, int column, int rectangleNumber) {
        int columnLimit = column;
        for (; columnLimit <= columnCount && field[row][columnLimit] == -1; columnLimit++) {
            field[row][columnLimit] = rectangleNumber;
        }
        for (int r = row + 1; r <= rowCount; r++) {
            if (field[r][column - 1] != 0 && field[r][columnLimit] != 0) {
                return;
            }
            for (int c = column; c < columnLimit; c++) {
                if (field[r][c] != -1) {
                    return;
                }
            }
            for (int c = column; c < columnLimit; c++) {
                field[r][c] = rectangleNumber;
            }
        }
    }
    
    private static boolean divideRectangle(int rowCount, int columnCount, int[][] field) {
        int row = 0, column = 0;
        for (int r = 1; r <= rowCount; r++) {
            for (int c = 1; c <= columnCount; c++) {
                if (field[r][c] == 1) {
                    row = r;
                    column = c;
                    break;
                }
            }
            if (row != 0 && column != 0) {
                break;
            }
        }
        int columnLimit = column;
        for (; columnLimit <= columnCount && field[row][columnLimit] != 0; columnLimit++) {}
        int rowLimit = row + 1;
        for (; rowLimit <= rowCount && field[rowLimit][column] != 0; rowLimit++) {}
        int height = rowLimit - row;
        int width = columnLimit - column;
        if (height == 1 && width == 1) {
            return false;
        }
        if (height == 1) {
            boolean firstWritten = false;
            for (int r = 1; r <= rowCount; r++) {
                for (int c = 1; c <= columnCount; c++) {
                    if (field[r][c] == 1) {
                        if (firstWritten) {
                            field[r][c] = 2;
                        } else {
                            firstWritten = true;
                        }
                    }
                }
            }
        } else {
            for (int r = 1; r <= rowCount; r++) {
                for (int c = 1; c <= columnCount; c++) {
                    if (field[r][c] == 1 && r != row) {
                        field[r][c] = 2;
                    }
                }
            }
        }
        return true;
    }
    //show
    private static void showField(int rowCount, int columnCount, int[][] field) {//show
        for (int r = 1; r <= rowCount; r++) {//show
            System.out.printf("%-2d", field[r][1]);//show
            for (int c = 2; c <= columnCount; c++) {//show
                System.out.printf(" %-2d", field[r][c]);//show
            }//show
            System.out.println();//show
        }//show
    }//show
    
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
Ход решения
1)  заполнение ячеечек поля:
        -1: raw cell
        0: empty cell
        1: first rectangle
        2: second rectangle
2)  прохожу по всем ячеечкам {
        if (cell == -1) {
            if (rectCount >= 2) { return NO; }
            markRect()
        }
    }
    void markRect(cellRow, cellCol, rectNum) {
        colLimit : while (cell == -1) { colLimit++; cell = rectNum; }
        for (r in [cellRow; rowCount]) {
            if (empty cells in the row [cellCol, colLimit)) { return; }
            if (cell[r][cellCol - 1] != 0 && cell[r][colLimit] != 0) { return; }
            fill cells [cellCol, colLimit) by rectNum;
        }
    }
3)  if (rectCount == 0) { return NO; }
    if (rectCount == 1) { divideRect(); }
    if (rectCount == 2) { return YES; }

tests:
5 5
.....
.###.
#####
.###.
.....

5 5
.....
.###.
.###.
#####
#####

5 5
.....
.###.
####.
#####
#####

5 5
.....
.###.
.####
#####
#####

5 5
..###
#####
#####
..###
.....

5 5
.####
#####
#####
..###
.....

5 5
.....
..###
#####
#####
.####

5 5
.####
.####
..##.
..##.
.....

5 5
####.
####.
.###.
.##..
.....

5 5
.....
####.
####.
###..
.##..

5 5
.....
##...
####.
####.
##...

5 5
##...
####.
####.
###..
.....

5 5
###..
####.
####.
##...
.....

1 1
.

1 1
#

2 2
..
..

2 2
..
.#

2 2
..
##

2 2
.#
.#

2 2
##
.#

2 2
##
##
*/
