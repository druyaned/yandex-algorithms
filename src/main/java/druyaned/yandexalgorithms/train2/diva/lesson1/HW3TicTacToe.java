package druyaned.yandexalgorithms.train2.diva.lesson1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3TicTacToe {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int SIZE = 3;
        int[][] field = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            String[] elems = reader.readLine().split(" ");
            for (int j = 0; j < SIZE; ++j) {
                field[i][j] = Integer.parseInt(elems[j]);
            }
        }
        int crossCnt = 0;
        int zeroCnt = 0;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (field[i][j] == 1) {
                    ++crossCnt;
                }
                if (field[i][j] == 2) {
                    ++zeroCnt;
                }
            }
        }
        int crossLineCnt = 0;
        int zeroLineCnt = 0;
        for (int i = 0; i < SIZE; ++i) {
            if (field[i][0] == 1 && field[i][0] == field[i][1] && field[i][0] == field[i][2]) {
                ++crossLineCnt; // cross horizontal
            }
            if (field[i][0] == 2 && field[i][0] == field[i][1] && field[i][0] == field[i][2]) {
                ++zeroLineCnt; // zero horizontal
            }
            if (field[0][i] == 1 && field[0][i] == field[1][i] && field[0][i] == field[2][i]) {
                ++crossLineCnt; // cross vertical
            }
            if (field[0][i] == 2 && field[0][i] == field[1][i] && field[0][i] == field[2][i]) {
                ++zeroLineCnt; // zero vertical
            }
        }
        if (field[0][0] == 1 && field[0][0] == field[1][1] && field[0][0] == field[2][2]) {
            ++crossLineCnt; // cross diagonal1
        }
        if (field[0][0] == 2 && field[0][0] == field[1][1] && field[0][0] == field[2][2]) {
            ++zeroLineCnt; // zero diagonal1
        }
        if (field[0][2] == 1 && field[0][2] == field[1][1] && field[0][2] == field[2][0]) {
            ++crossLineCnt; // cross diagonal2
        }
        if (field[0][2] == 2 && field[0][2] == field[1][1] && field[0][2] == field[2][0]) {
            ++zeroLineCnt; // zero diagonal2
        }
        int diff = crossCnt - zeroCnt;
        boolean cond1 = crossLineCnt == 0 && zeroLineCnt == 0 && 0 <= diff && diff <= 1;
        boolean cond2 = 1 <= crossLineCnt && crossLineCnt <= 2 && zeroLineCnt == 0 && diff == 1;
        boolean cond3 = crossLineCnt == 0 && zeroLineCnt == 1 && diff == 0;
        if (cond1 || cond2 || cond3) {
            writer.write("YES\n");
        } else {
            writer.write("NO\n");
        }
    }
    
}
/*
Короче, условия возможности для игровой ситуации:
cond1 = crossLineCnt == 0 && zeroLineCnt == 0 && 0 <= diff && diff <= 1;
cond2 = 1 <= crossLineCnt && crossLineCnt <= 2 && zeroLineCnt == 0 && diff == 1;
cond3 = crossLineCnt == 0 && zeroLineCnt == 1 && diff == 0;

1 2 1
2 1 2
1 2 1

2 1 1
2 1 2
1 0 0

1 1 1
1 2 2
1 2 2

1 1 1
0 1 2
2 0 2

2 1 0
1 2 1
2 1 2

2 2 2
0 1 1
2 1 1

0 1 2
0 0 2
0 0 0
*/