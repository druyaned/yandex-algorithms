package druyaned.yandexalgorithms.train3.l6breadthfirstsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HW4WayOfSpeleologist {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[15];
    
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
    
    private static class Point {
        public final int i, j, k;
        public Point(int i, int j, int k) {
            this.i = i;
            this.j = j;
            this.k = k;
        }
    }
    
    private static void prepareCave(int n, int[][][] cave) {
        for (int j = 0; j < n + 2; ++j) {
            for (int k = 0; k < n + 2; ++k) {
                cave[0][j][k] = cave[n + 1][j][k] = -1;
            }
        }
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j < n + 2; ++j) {
                cave[i][j][0] = cave[i][j][n + 1] = -1;
            }
            for (int k = 1; k <= n; ++k) {
                cave[i][0][k] = cave[i][n + 1][k] = -1;
            }
        }
    }
    
    private static void show(int n, int[][][] cave, Point start) {
        System.out.print("\ncave:\n");
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                for (int k = 1; k <= n; ++k) {
                    if (i == start.i && j == start.j && k == start.k) {
                        System.out.printf(" %2c", 'S');
                    } else if (cave[i][j][k] == -1) {
                        System.out.printf(" %2c", '*');
                    } else {
                        System.out.printf(" %2d", cave[i][j][k]);
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int MAX_D = 30 * 30 * 30;
        final int MOVE_N = 6;
        final int[] moveI = {+1, +0, +0, -1, +0, +0};
        final int[] moveJ = {+0, +1, +0, +0, -1, +0};
        final int[] moveK = {+0, +0, +1, +0, +0, -1};
        int n = readInt(reader);
        int[][][] cave = new int[n + 2][n + 2][n + 2];
        prepareCave(n, cave);
        Point start = null;
        for (int i = 1; i <= n; ++i) {
            reader.read();
            for (int j = 1; j <= n; ++j) {
                for (int k = 1; k <= n; ++k) {
                    char ch = (char)reader.read();
                    if (ch == '#') {
                        cave[i][j][k] = -1;
                    }
                    if (ch == 'S') {
                        start = new Point(i, j, k);
                        cave[i][j][k] = -1;
                    }
                }
                reader.read();
            }
        }
        if (start != null) {
            System.out.printf("start: %d %d %d\n", start.i, start.j, start.k); // TODO: debug
        }
        List<List<Point>> pointsOfDist = new ArrayList(MAX_D + 1);
        for (int i = 0; i <= MAX_D; ++i) {
            pointsOfDist.add(new ArrayList());
        }
        pointsOfDist.get(0).add(start);
        for (int d = 0; !pointsOfDist.get(d).isEmpty(); ++d) {
            for (Point p : pointsOfDist.get(d)) {
                for (int ind = 0; ind < MOVE_N; ++ind) {
                    int i = p.i + moveI[ind], j = p.j + moveJ[ind], k = p.k + moveK[ind];
                    if (cave[i][j][k] == 0) {
                        cave[i][j][k] = d + 1;
                        pointsOfDist.get(d + 1).add(new Point(i, j, k));
                    }
                }
            }
        }
        show(n, cave, start); // TODO: debug
        int dist = MAX_D;
        for (int j = 1; j <= n; ++j) {
            for (int k = 1; k <= n; ++k) {
                if (start != null && start.i == 1 && start.j == j && start.k == k) {
                    writer.write("0\n");
                    System.out.print("0\n"); // TODO: debug
                    return;
                }
                if (cave[1][j][k] > 0 && dist > cave[1][j][k]) {
                    dist = cave[1][j][k];
                }
            }
        }
        writer.write(dist + "\n");
        System.out.print(dist + "\n"); // TODO: debug
    }
    
}
