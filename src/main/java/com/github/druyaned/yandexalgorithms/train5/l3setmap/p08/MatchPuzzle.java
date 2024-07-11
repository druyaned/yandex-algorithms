package com.github.druyaned.yandexalgorithms.train5.l3setmap.p08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchPuzzle {
    
    private static final char[] BUFFER = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        Vector[] a = new Vector[n];
        Vector[] b = new Vector[n];
        for (int i = 0; i < n; i++) {
            int x1 = readInt(reader);
            int y1 = readInt(reader);
            int x2 = readInt(reader);
            int y2 = readInt(reader);
            a[i] = new Vector(-1, i + 1, x1, y1, x2, y2);
        }
        for (int i = 0; i < n; i++) {
            int x1 = readInt(reader);
            int y1 = readInt(reader);
            int x2 = readInt(reader);
            int y2 = readInt(reader);
            b[i] = new Vector(-1, i + 1, x1, y1, x2, y2);
        }
        Map<Vector, Map<Vector, List<Vector>>> basisToMovedMap = new HashMap(n);
        for (int i = 0; i < n; i++) {
            int dx = -b[i].x1;
            int dy = -b[i].y1;
            Vector basis = b[i].moved(i + 1, dx, dy);
            Map<Vector, List<Vector>> movedMap = basisToMovedMap.get(basis);
            if (movedMap == null) {
                movedMap = new HashMap(n);
                basisToMovedMap.put(basis, movedMap);
            }
            for (int j = i + 1; j < n; j++) {
                Vector moved = b[j].moved(i + 1, dx, dy);
                List<Vector> similar = movedMap.get(moved);
                if (similar == null) {
                    similar = new ArrayList(4);
                    movedMap.put(moved, similar);
                }
                similar.add(moved);
            }
        }
        int[] counts = new int[n + 1];
        int maxCount = 0;
        for (int i = 0; i < n; i++) {
            int dx = -a[i].x1;
            int dy = -a[i].y1;
            Map<Vector, List<Vector>> movedMap = basisToMovedMap.get(a[i].moved(i + 1, dx, dy));
            if (movedMap == null) {
                continue;
            }
            Arrays.fill(counts, 1);
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    List<Vector> similar = movedMap.get(a[j].moved(i + 1, dx, dy));
                    if (similar != null) {
                        similar.forEach(vector -> counts[vector.basisId]++);
                    }
                }
            }
            for (int j = 1; j <= n; j++) {
                if (maxCount < counts[j]) {
                    maxCount = counts[j];
                }
            }
            if (maxCount == n) {
                writer.write("0\n");
                return;
            }
        }
        writer.write(Integer.toString(n - maxCount) + "\n");
    }
    
    private static int readInt(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Integer.parseInt(new String(BUFFER, 0, bufferLength));
    }
    
    private static int nextTokenInBuffer(Reader reader) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        BUFFER[0] = (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            BUFFER[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}

class Vector {
    
    public final int basisId, id;
    public final int x1, y1;
    public final int x2, y2;
    public final int hash;
    
    public Vector(int basisId, int id, int x1, int y1, int x2, int y2) {
        this.basisId = basisId;
        this.id = id;
        if (x1 < x2 || x1 == x2 && y1 < y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        } else {
            this.x1 = x2;
            this.y1 = y2;
            this.x2 = x1;
            this.y2 = y1;
        }
        int h = this.x1 * 10069;
        h = 10061 * (h % 10067) + this.y1;
        h = 10037 * (h % 10039) + this.x2;
        h = 10007 * (h % 10009) + this.y2;
        hash = h >= 0 ? h : 10079 * 10091 + h;
    }
    
    public Vector moved(int basisId, int dx, int dy) {
        return new Vector(basisId, id, x1 + dx, y1 + dy, x2 + dx, y2 + dy);
    }
    
    @Override public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Vector other = (Vector)obj;
        return x1 == other.x1 && y1 == other.y1 &&
                x2 == other.x2 && y2 == other.y2;
    }
    
    @Override public int hashCode() {
        return hash;
    }
    
}
/*
Solve:
1)  create hash table of picture B called basisToMovedMap:
        basis - vector b[i] moved by dx=-x1 and dy=-y1 (ignore dublicates);
        movedMap - map of (b[j] moved reletive to the basis) to the list of dublicates;
2)  createBasisToMovedMap() {
        loop through B ( i in [0, n) ) {
            select b[i] as a basis (dx=-x1, dy=-y1);
            loop through B ( j in (i, n) ) {
                moved = basis.moved(dx, dy);
                dublicates.add(moved);
            }
        }
    }
3)  calculateMatchCount() {
        loop through A ( i in [0, n) ) {
            basisA: dx=-a[i].x1, dy=-a[i].y1;
            find the movedMap by basisA;
            loop through A ( j in [0, n) ) {
                find dublicates by moved a[j];
                dublicates.forEach(vector -> counts[vector.basisId]++);
            }
        }
    }

Tests:
6
3 5 1 3
-2 1 -2 3
3 3 1 1
1 4 1 6
2 3 0 1
0 2 0 4
7 2 5 0
5 4 7 4
5 3 5 1
6 2 8 4
10 3 8 1
8 2 8 4
output:
3

4
1 1 1 0
3 0 3 1
4 1 4 0
5 0 5 1
1 -1 1 -2
2 -2 2 -1
4 -1 4 -2
5 -2 5 -1
output:
1

10
0 0 0 1
0 1 1 1
1 1 1 2
1 2 2 2
2 2 2 3
2 3 3 3
3 3 3 4
3 4 3 5
3 5 3 6
3 6 4 6
0 0 1 0
1 0 1 1
1 1 2 1
2 1 3 1
3 1 4 1
4 1 4 2
4 2 4 3
4 3 4 4
4 4 4 5
4 5 5 5
output:
5
*/
