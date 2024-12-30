package druyaned.yandexalgorithms.internweekoffer2023;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Task3RepositoryInheritance {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        final int MAX_REPO = 100000;
        int[] depths = new int[MAX_REPO + 1];
        int maxDepth = 0;
        int maxRepo = 0;
        for (int i = 1; i <= n; ++i) {
            int repo = Integer.parseInt(reader.readLine());
            depths[i] = depths[repo] + 1;
            if (depths[i] > maxDepth) {
                maxDepth = depths[i];
                maxRepo = i;
            }
        }
        writer.write(maxRepo + "\n");
    }
    
}
/*
0 < - 1 < - 3
^
|
2
n - количество наследованных repos (1 <= n <= 100000);
r[i] - индекс repo, наследником которого является repo[i] (0 <= r[i] < i);
maxRepo - нужно найти такой репозиторий, что изменение оказались
в как можно большем количестве репозиториев.
Короче, надо найти самый глубокий элемент в дереве.
Но можно без дерева (т.е. без рекурсии).
Для этого просто буду хранить глубину для каждого следующего
подключаемого элемента.

Ввод:
n
r[1]
r[2]
...
r[n]
Вывод:
maxRepo
*/
