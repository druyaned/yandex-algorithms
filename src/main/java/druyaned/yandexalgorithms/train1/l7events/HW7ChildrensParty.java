package druyaned.yandexalgorithms.train1.l7events;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeSet;

public class HW7ChildrensParty {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Assistant implements Comparable<Assistant> {
        private final int t, z, y, id;
        private final int time, nextRestTime;
        private Assistant(int t, int z, int y, int id) {
            this.t = t;
            this.z = z;
            this.y = y;
            this.id = id;
            time = t;
            nextRestTime = t * z;
        }
        private Assistant(int t, int z, int y, int id, int time, int nextRestTime) {
            this.t = t;
            this.z = z;
            this.y = y;
            this.id = id;
            this.time = time;
            this.nextRestTime = nextRestTime;
        }
        public Assistant next() {
            if (time == nextRestTime) {
                return new Assistant(t, z, y, id, time + t + y, nextRestTime + (t * z + y));
            } else {
                return new Assistant(t, z, y, id, time + t, nextRestTime);
            }
        }
        @Override public int compareTo(Assistant a) {
            return time == a.time ?
                    t == a.t ? id - a.id : t - a.t :
                    time - a.time;
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int m = Integer.parseInt(elems[0]);
        int n = Integer.parseInt(elems[1]);
        TreeSet<Assistant> assistants = new TreeSet<>();
        for (int i = 0; i < n; ++i) {
            elems = reader.readLine().split(" ");
            int t = Integer.parseInt(elems[0]);
            int z = Integer.parseInt(elems[1]);
            int y = Integer.parseInt(elems[2]);
            assistants.add(new Assistant(t, z, y, i));
        }
        // solve
        int cnt = 0;
        int[] counts = new int[n];
        int time = 0;
        while (cnt < m) {
            Assistant a = assistants.pollFirst();
            time = a.time;
            Assistant nextA = a.next();
            assistants.add(nextA);
            ++counts[a.id];
            ++cnt;
        }
        writer.write(time + "\n" + counts[0]);
        for (int i = 1; i < n; ++i) {
            writer.write(" " + counts[i]);
        }
        writer.write('\n');
    }
    
}
/*
t z y
t - минут на 1 шарик
z - количество шариков, после которых на отдых
y - время отдыха
m - количество шариков
n - количество помощников
ans - время, за которое будут надуты все шарики
bls - количества шариков, надутые каждым из помощников
Распределять шарики можно как угодно.

input:
40 3
2 5 3
3 7 2
2 6 4
output:
36
15 11 14

for i in [0, +inf) {
    start = i * (t * z + y);
    finish = t * z + i * (t * z + y);
}

Point:
    t, z, y;
    time = t;
    nextRestTime = t * z; // i
    
    update():
        if (time == nextRestTime):
            nextRestTime += (t * z + y);
            time += y
        time += t;
*/
