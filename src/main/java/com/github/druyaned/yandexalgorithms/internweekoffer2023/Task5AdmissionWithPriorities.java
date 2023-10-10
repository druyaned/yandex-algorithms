package com.github.druyaned.yandexalgorithms.internweekoffer2023;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Task5AdmissionWithPriorities {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class Entrant implements Comparable<Entrant> {
        private final int id;
        private final int rank;
        private final int[] priorities;
        private Entrant(int id, int rank, int[] priorities) {
            this.id = id;
            this.rank = rank;
            this.priorities = priorities;
        }
        @Override public int compareTo(Entrant st) {
            return rank - st.rank;
        }
    }
    
    private static void showEntrants(Entrant[] entrants) {
        for (int i = 0; i < entrants.length; ++i) {
            System.out.printf("rank=%d id=%d priorities={", entrants[i].rank, entrants[i].id);
            System.out.printf("%d", entrants[i].priorities[0]);
            for (int j = 1; j < entrants[i].priorities.length; ++j) {
                System.out.printf(" %d", entrants[i].priorities[j]);
            }
            System.out.println('}');
        }
    }
    
    private static void showRankCounts(int[] rankCounts, int entrantN) {
        System.out.printf("rankCount={1(%d)", rankCounts[1]);
        for (int i = 2; i <= entrantN; ++i) {
            System.out.printf(" %d(%d)", i, rankCounts[i]);
        }
        System.out.println('}');
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int entrantN = Integer.parseInt(elems[0]);
        int programN = Integer.parseInt(elems[1]);
        int[] programPlaces = new int[programN + 1];
        elems = reader.readLine().split(" ");
        for (int i = 0; i < programN; ++i) {
            programPlaces[i + 1] = Integer.parseInt(elems[i]);
        }
        Entrant[] entrants = new Entrant[entrantN];
        for (int i = 0; i < entrantN; ++i) {
            elems = reader.readLine().split(" ");
            int rank = Integer.parseInt(elems[0]);
            int priorityN = Integer.parseInt(elems[1]);
            int[] priorities = new int[priorityN];
            for (int j = 0; j < priorityN; ++j) {
                priorities[j] = Integer.parseInt(elems[2 + j]);
            }
            entrants[i] = new Entrant(i, rank, priorities);
        }
        // solve
        Arrays.sort(entrants);
        System.out.printf("entrantN=%d programN%d\n", entrantN, programN); // TODO: debug
        showEntrants(entrants); // TODO: debug
        int[] rankCounts = new int[entrantN + 1];
        for (int i = 0; i < entrantN; ++i) {
            ++rankCounts[entrants[i].rank];
        }
        showRankCounts(rankCounts, entrantN); // TODO: debug
        int[] places = new int[entrantN];
        for (int i = 0; i < entrantN; ++i) {
            final int id = entrants[i].id;
            final int rank = entrants[i].rank;
            final int[] priorities = entrants[i].priorities;
            final int priorityN = priorities.length;
            places[id] = -1;
            for (int j = 0; j < priorityN; ++j) {
                int program = priorities[j];
                if (rankCounts[rank] <= programPlaces[program]) {
                    --programPlaces[program];
                    places[id] = program;
                    break;
                } else {
                    programPlaces[program] = 0;
                }
            }
            --rankCounts[rank];
        }
        writer.write(Integer.toString(places[0]));
        System.out.print(Integer.toString(places[0])); // TODO: debug
        for (int i = 1; i < entrantN; ++i) {
            writer.write(" " + places[i]);
            System.out.print(" " + places[i]); // TODO: debug
        }
        writer.write('\n');
        System.out.println(); // TODO: debug
    }
    
}
/*
Условие:
    все места на более желаемых им программах
    заняты людьми с рейтингом выше чем у этого абитуриента.
Условие неодназначно, рассмотрю на примере.

entrantN=7 programN=3
programPlaces={2 3 4}
rank[1]=1 priorities[1]={3 2 1}
rank[2]=2 priorities[2]={3 2 1}
rank[3]=3 priorities[3]={3 2}
rank[4]=3 priorities[4]={3 2}
rank[5]=3 priorities[5]={3 2}
rank[6]=4 priorities[6]={2 1}
rank[7]=5 priorities[7]={3 2 1}

Количество мест на 3-й программе = 4.
Сначала туда зачисляются 1-й и 2-й в рейтинге и остается 2 места,
а оставшихся желающих с одинаковым рейтингом 3 (всего 4).
Если зачислить 2-х любых студентов с одинаковым рейтингом,
будет несправедливо по отношению к не зачисленному.
А если не зачислить всех, то условие тоже не выполняется,
ведь не все места останутся занятыми.
В итоге, вменяемое распределение для данного примера выглядит так:
entrant: 1 2 3 4 5 6 7
program: 3 3 2 2 2 1 1

input:
7 3
2 3 4
1 3 3 2 1
2 3 3 2 1
3 2 3 2
3 2 3 2
3 2 3 2
4 2 2 1
5 3 3 2 1
output:
3 3 2 2 2 1 1

input:
7 3
1 3 3
1 3 3 2 1
2 3 3 2 1
3 2 3 2
3 2 3 2
3 2 3 2
4 2 2 1
5 3 3 2 1
output:
3 3 2 2 2 1 -1

Каков проход? По отсортированным студентам i in [0, n].
Для каждого студента внутренним циклом смотрю, сколько позиций осталось и
смотрю на количество.
*/
