package druyaned.yandexalgorithms.train5.l2linearsearch.p04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Chessboard {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int MOVE_COUNT = 4;
        final int SIDE_LENGTH = 8;
        final int[] rowMove = { +1, 0, -1, 0 }; // up, right, down, left
        final int[] colMove = { 0, +1, 0, -1 };
        int cellCount = readInt(reader);
        boolean[][] chessboard = new boolean[SIDE_LENGTH + 2][SIDE_LENGTH + 2];
        for (int i = 0; i < cellCount; i++) {
            int row = readInt(reader);
            int column = readInt(reader);
            chessboard[row][column] = true;
        }
        int perimeter = 0;
        for (int r = 1; r <= SIDE_LENGTH; r++) {
            for (int c = 1; c <= SIDE_LENGTH; c++) {
                if (chessboard[r][c]) {
                    for (int i = 0; i < MOVE_COUNT; i++) {
                        if (!chessboard[r + rowMove[i]][c + colMove[i]]) {
                            perimeter++;
                        }
                    }
                }
            }
        }
        writer.write(Integer.toString(perimeter) + "\n");
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
