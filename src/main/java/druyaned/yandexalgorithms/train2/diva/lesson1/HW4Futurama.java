package druyaned.yandexalgorithms.train2.diva.lesson1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4Futurama {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class PairTable {
        private final int n;
        private final boolean[] table;
        public PairTable(int n) {
            this.n = n;
            int tableSize = (n - 1) * n + n + 1;
            table = new boolean[tableSize];
        }
        public boolean contains(int i1, int i2) {
            int hash = i1 * n + i2;
            return table[hash];
        }
        public boolean add(int i1, int i2) {
            int hash = i1 * n + i2;
            return table[hash] ? false : (table[hash] = true);
        }
    }
    
    private static class PairStack {
        private final int[] stack1;
        private final int[] stack2;
        private int stackSize;
        public PairStack(int n) {
            int pairCount = (n - 1) * n / 2;
            stack1 = new int[pairCount];
            stack2 = new int[pairCount];
        }
        public int first(int i) {
            return stack1[i];
        }
        public int second(int i) {
            return stack2[i];
        }
        public void push(int fst, int snd) {
            stack1[stackSize] = fst;
            stack2[stackSize++] = snd;
        }
    }
    
    private static class Bodies {
        private final int n;
        private final int[] minds;
        public final PairTable pairTable;
        public final PairStack stack;
        public Bodies(int n) {
            this.n = n;
            minds = new int[n + 1];
            for (int i = 1; i <= n; ++i) {
                minds[i] = i;
            }
            pairTable = new PairTable(n);
            stack = new PairStack(n);
        }
        public int mindAt(int i) {
            return minds[i];
        }
        public boolean swap(int i1, int i2) {
            if (pairTable.add(i1, i2)) {
                minds[0] = minds[i1];
                minds[i1] = minds[i2];
                minds[i2] = minds[0];
                stack.push(i1, i2);
                return true;
            } else {
                return false;
            }
        }
        public void show() {
            System.out.print(minds[1]);
            for (int i = 2; i <= n; ++i) {
                System.out.printf(" %d", minds[i]);
            }
            System.out.println();
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int n = Integer.parseInt(elems[0]);
        int m = Integer.parseInt(elems[1]);
        Bodies bodies = new Bodies(n);
        for (int i = 0; i < m; ++i) {
            elems = reader.readLine().split(" ");
            int i1 = Integer.parseInt(elems[0]);
            int i2 = Integer.parseInt(elems[1]);
            bodies.swap(i1, i2);
        }
        for (int body = 1; body <= n - 2; ++body) {
            int mind = bodies.mindAt(body);
            if (mind != body) {
                bodies.swap(body, n - 1);
                int nextMind = bodies.mindAt(mind);
                while (!bodies.pairTable.contains(nextMind, n - 1)) {
                    bodies.swap(mind, n - 1);
                    mind = nextMind;
                    nextMind = bodies.mindAt(mind);
                }
                bodies.swap(mind, n);
                bodies.swap(nextMind, n);
                bodies.swap(mind, n - 1);
            }
        }
        if (bodies.mindAt(n - 1) != n - 1) {
            bodies.swap(n - 1, n);
        }
        for (int i = m; i < bodies.stack.stackSize; ++i) {
            writer.write(bodies.stack.first(i) + " " + bodies.stack.second(i) + "\n");
        }
    }
    
}
/*
Ход решения:
1. Создаю классы: PairTable, PairStack, Bodies, - в которых
    реализовываю всю необходимую логику.
2. PairTable - это хэш таблица задействованных пар,
    где hash = i1 * n + i2.
3. Пары всегда такие: (i1, i2) => i1 < i2.
4. PairStack - это стэк пар реализованных обменов.
5. Bodies - основной класс тел, содержащий:
    int[] minds, PairTable pairTable, PairStack stack.
    Функционал такой: boolean swap(int i1, int i2); int mindAt(int i).
    В методе swap обновляются minds, pairTable, stack.
6. Все необходимое для реализации алгоритма есть.
    Суть алгоритма: обход i in [1, n - 2];
    если mind != body, меняю тела [body] и [n - 1],
    и пока [nextMind] (nextMind = bodies.mindAt(mind)) не имеет
    задействованную пару с [n - 1], swap(mind, n - 1).
    После внутреннего цикла нельзя swap(mind, n - 1), ведь
    нельзя будет swap(nextMind, n - 1).
    Поэтому задействую [n].
    И в конце остается проверить [n - 1] и [n].
Понимание алгоритма пришло после разбора примеров на
интерактивчике FuturamaDraft.

input:
8 5
1 5
2 6
2 3
2 4
3 5
output:
1 7
5 7
6 7
2 7
4 7
3 8
1 8
3 7
7 8

input:
8 4
1 5
2 6
2 3
2 4
output:
1 7
5 8
1 8
5 7
2 7
4 7
3 7
6 8
2 8
6 7
*/
