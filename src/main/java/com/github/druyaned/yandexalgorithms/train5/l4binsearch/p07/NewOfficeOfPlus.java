package com.github.druyaned.yandexalgorithms.train5.l4binsearch.p07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NewOfficeOfPlus {
    
    private static final char[] buffer = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int height = readInt(reader);
        int width = readInt(reader);
        int[][] field = readField(reader, width, height);
        int leftRank = 1;
        int rightRank = Integer.min(width, height) / 3;
        while (leftRank < rightRank) {
            int midRank = (leftRank + rightRank + 1) / 2;
            if (checkPlus(width, height, field, midRank)) {
                leftRank = midRank;
            } else {
                rightRank = midRank - 1;
            }
        }
        int rank = rightRank;
        writer.write(Integer.toString(rank) + "\n");
    }
    
    private static boolean checkPlus(int width, int height, int[][] field, int k) {
        int square = k * k;
        for (int r = 1; r <= height - 3 * k + 1; r++) {
            for (int c = 1; c <= width - 3 * k + 1; c++) {
                int s1 = field[r + 3 * k - 1][c + 2 * k - 1] -
                        field[r - 1][c + 2 * k - 1] +
                        field[r - 1][c + k - 1] -
                        field[r + 3 * k - 1][c + k - 1];
                int s2 = field[r + 2 * k - 1][c + k - 1] -
                        field[r + k - 1][c + k - 1] +
                        field[r + k - 1][c - 1] -
                        field[r + 2 * k - 1][c - 1];
                int s3 = field[r + 2 * k - 1][c + 3 * k - 1] -
                        field[r + k - 1][c + 3 * k - 1] +
                        field[r + k - 1][c + 2 * k - 1] -
                        field[r + 2 * k - 1][c + 2 * k - 1];
                if (s1 == square * 3 && s2 == square && s3 == square) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static int[][] readField(Reader reader, int width, int height) throws IOException {
        int[][] f = new int[height + 2][width + 2];
        for (int r = 1; r <= height; r++) {
            for (int c = 1; c <= width; c++) {
                if (reader.read() == '#') {
                    f[r][c] = f[r][c - 1] + 1 + f[r - 1][c] - f[r - 1][c - 1];
                } else {
                    f[r][c] = f[r][c - 1] + f[r - 1][c] - f[r - 1][c - 1];
                }
            }
            reader.read(); // new line
        }
        return f;
    }
    
    private static int readInt(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Integer.parseInt(new String(buffer, 0, bufferLength));
    }
    
    private static int nextTokenInBuffer(Reader reader) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        buffer[0] = (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            buffer[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}
/*
height=9 width=12
   1 2 3 4 5 6 7 8 9 0 1 2
-+------------------------
1| . . . * * . * * * . . .
2| . . . * * . * * * . . .
3| . * * * * * * * * . . .
4| . * * * * * * * * * * *
5| . . . * * * * * * * * *
6| . . . * * * * * * * * *
7| . . . . . . * * * . . .
8| . . . . . . * * * . . .
9| . . . . . . * * * . . .

f[r][c] = f[r][c-1] + 1 + f[r-1][c] - f[r-1][c-1];

    1  2  3  4  5  6  7  8  9 10 11 12
--+------------------------------------
 1|  .  .  .  .  .  .  .  .  .  .  .  .
 2|  .  .  .  1  2  2  3  4  5  5  5  5
 3|  .  .  .  2  4  4  6  8 10 10 10 10
 4|  .  1  2  5  8  9 12 15 18 18 18 18
 5|  .  2  4  8 12 14 18 22 26 27 28 29
 6|  .  2  4  9 14 17 22 27 32 34 36 38
 7|  .  2  4 10 16 20 26 32 38 41 44 47
 8|  .  2  4 10 16 20 27 34 41 44 47 50
 9|  .  2  4 10 16 20 28 36 44 47 50 53
10|  .  2  4 10 16 20 29 38 47 50 53 56

    1 2 3 4 5 6 7 8 9 0 1 2
--+------------------------
 1| . . . . . . . . . . . .
 2| . . . I . . * * * . . .
 3| . . . . . . * * * . . .
 4| . . . . . . * * * . . .
 5| . . . * * * * * * * * *
 6| . . . * * * * * * * * *
 7| . . . * * * * * * * * *
 8| . . . . . . * * * . . .
 9| . . . . . . * * * . . .
10| . . . . . . * * * . . .

s1 = f[r+3k-1][c+2k-1] - f[r-1][c+2k-1] + f[r-1][c+k-1] - f[r+3k-1][c+k-1]
s2 = f[r+2k-1][c+k-1] - f[r+k-1][c+k-1] + f[r+k-1][c-1] - f[r+2k-1][c-1]
s3 = f[r+2k-1][c+3k-1] - f[r+k-1][c+3k-1] + f[r+k-1][c+2k-1] - f[r+2k-1][c+2k-1]
*/
