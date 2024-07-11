package com.github.druyaned.yandexalgorithms.train5.l4binsearch.p08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Election {
    
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
        int partyCount = readInt(reader);
        Party[] sources = new Party[partyCount];
        Party[] parties = new Party[partyCount];
        for (int i = 0; i < partyCount; i++) {
            int ctz = readInt(reader);
            int brb = readInt(reader);
            sources[i] = parties[i] = new Party(i, ctz, brb);
        }
        Arrays.sort(parties, (p1, p2) -> p2.ctz - p1.ctz);
        Projection[] projs = makeProjs(parties);
        Object[] pairBundle = makePairs(projs);
        int pairCount = (Integer)pairBundle[0];
        Pair[] pairs = (Pair[])pairBundle[1];
        findIncreases(projs, pairCount, pairs);
        findSums(parties, projs);
        Object[] bestBundle = findBest(parties, projs);
        Party bestParty = (Party)bestBundle[0];
        Projection bestProj = (Projection)bestBundle[1];
        int[] citizens = updatedCitizens(parties, bestParty, bestProj);
        writer.write(Integer.toString(bestParty.sum) + "\n");
        writer.write(Integer.toString(bestParty.id + 1) + "\n");
        writer.write(Integer.toString(citizens[0]));
        for (int i = 1; i < partyCount; i++) {
            writer.write(" " + citizens[i]);
        }
        writer.write('\n');
    }
    
    private static Projection[] makeProjs(Party[] parties) {
        int projCount = 1;
        for (int i = 0; i < parties.length - 1; i++) {
            if (parties[i].ctz != parties[i + 1].ctz) {
                projCount++;
            }
        }
        Projection[] projs = new Projection[projCount];
        int cnt = 1;
        int j = 0; // index of the projections
        for (int i = 0; i < parties.length - 1; i++) {
            if (parties[i].ctz != parties[i + 1].ctz) {
                projs[j++] = new Projection(parties[i].ctz, cnt);
                cnt = 1;
            } else {
                cnt++;
            }
        }
        projs[j] = new Projection(parties[parties.length - 1].ctz, cnt);
        return projs;
    }
    
    private static Object[] makePairs(Projection[] projs) {
        final int MAX_SIZE = projs[0].val + 1;
        Pair[] pairs = new Pair[MAX_SIZE];
        int transition = 0; // transitive increase
        int othersMax = projs[0].val;
        int totCnt = projs[0].cnt;
        pairs[0] = new Pair(transition, othersMax);
        int i = 1; // index of the pairs
        int j = 1; // index of the projections
        for (; i <= projs[0].val && transition <= othersMax; i++) {
            transition += totCnt;
            othersMax--;
            if (j < projs.length && projs[j].val == othersMax) {
                totCnt += projs[j].cnt;
                j++;
            }
            pairs[i] = new Pair(transition, othersMax);
        }
        return new Object[] { i, pairs };
    }
    
    private static void findIncreases(Projection[] projs, int pairCount, Pair[] pairs) {
        if (projs[0].cnt == 1) {
            projs[0].othersMax = projs[0].val - 1;
            projs[0].increase = 0;
        } else {
            projs[0].othersMax = projs[0].val;
            projs[0].increase = 1;
        }
        for (int i = 1; i < projs.length; i++) {
            int val = projs[i].val;
            int left = 0;
            int right = pairCount - 1;
            while (left < right) {
                int mid = (left + right) / 2;
                if (pairs[mid].transition > pairs[mid].othersMax - val) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            int transition = pairs[left].transition;
            int othersMax = pairs[left].othersMax;
            int prevMax = othersMax + 1;
            if (transition - 1 > prevMax - val) {
                projs[i].transition = pairs[left - 1].transition;
                projs[i].othersMax = prevMax;
                projs[i].increase = prevMax - val + 1;
            } else {
                projs[i].transition = transition;
                projs[i].othersMax = othersMax;
                projs[i].increase = transition;
            }
        }
    }
    
    private static void findSums(Party[] parties, Projection[] projs) {
        int i = 0; // index of the parties
        int j = 0; // index of the projections
        for (; i < parties.length; i++) {
            if (parties[i].ctz != projs[j].val) {
                j++;
            }
            if (parties[i].brb != -1) {
                parties[i].sum = projs[j].increase + parties[i].brb;
            }
        }
    }
    
    private static Object[] findBest(Party[] parties, Projection[] projs) {
        Party bestParty = null;
        Projection bestProj = null;
        int i = 0; // index of the parties
        int j = 0; // index of the projections
        for (; i < parties.length; i++) {
            if (parties[i].ctz != projs[j].val) {
                j++;
            }
            if (parties[i].brb != -1 && (bestParty == null || bestParty.sum > parties[i].sum)) {
                bestParty = parties[i];
                bestProj = projs[j];
            }
        }
        return new Object[] { bestParty, bestProj };
    }
    
    private static int[] updatedCitizens(Party[] parties, Party bestParty, Projection bestProj) {
        int[] citizens = new int[parties.length];
        citizens[bestParty.id] = bestProj.val + bestProj.increase;
        int stepCount = bestProj.increase - bestProj.transition;
        for (Party party : parties) {
            if (party.id == bestParty.id) {
                continue;
            }
            if (party.ctz >= bestProj.othersMax) {
                if (stepCount > 0) {
                    citizens[party.id] = bestProj.othersMax - 1;
                    stepCount--;
                } else {
                    citizens[party.id] = bestProj.othersMax;
                }
            } else {
                citizens[party.id] = party.ctz;
            }
        }
        return citizens;
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

class Party {
    
    public final int id;
    public final int ctz; // citizen count
    public final int brb; // bribe
    public int sum;
    
    public Party(int id, int ctz, int brb) {
        this.id = id;
        this.ctz = ctz;
        this.brb = brb;
    }
    
}

class Projection {
    
    public final int val; // val equals ctz from the Party
    public final int cnt; // val count
    public int transition;
    public int othersMax;
    public int increase;
    
    public Projection(int val, int cnt) {
        this.val = val;
        this.cnt = cnt;
    }
    
}

class Pair {
    
    public final int transition;
    public final int othersMax;
    
    public Pair(int transition, int othersMax) {
        this.transition = transition;
        this.othersMax = othersMax;
    }
    
}
/*
Solve {
    
    Parties:
      i| 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18
    ---+--------------------------------------------------------
    ctz| 9  9  8  6  5  5  5  4  3  3  3  2  2  2  2  2  1  1  1
    
    Projections:
    val:  9  8  6  5  4  3  2  1
    cnt:  2  1  1  3  1  3  5  3
    
               |  all values (othersMax)
    -----------+------------------------
    transition |  counts for the values
    
     |  9  8  7  6  5  4  3  2  1  0
    -+------------------------------
    0|  2  1  0  1  3  1  3  5  3  0
    2|  0  3  0  1  3  1  3  5  3  0
    5|  0  0  3  1  3  1  3  5  3  0
    8|  0  0  0  4  3  1  3  5  3  0
    Дальше считать смысла нет, т.к. { transition > othersMax == 8 > 6 == true }.
    
    Pairs (transition, othersMax):
    transition| othersMax
    ----------+----------
             0| 9
             2| 8
             5| 7
             8| 6
    
    Задача: найти добавочное количество граждан (increase) для каждой партии,
        которое нужно для победы.
    Алгоритм решения задачи {
        1) нахожу increase отдельно для наибольшего значения val = proj[0].val;
        2) для всех следующих:
            2.1) нахожу левым бинпоиском transition : { transition > otherMax - val == good };
            2.2) проверяю, можно ли уменьшить transition:
                if (transition - 1 > othersMax - val + 1) increase = othersMax - val + 2;
                else increase = transition;
            2.3) запоминаю transition и othersMax, они поднадобятся для обновления граждан.
        Пример расчета:
        val=9 {
            if (cnt == 1) increase=0;
            else increase=1;
        }
        val=8 {
            2 > 0 == true;
            1 > 1 == false;
            increase=2;
        }
        val=6 {
            5 > 1 == true;
            4 > 2 == true;
            increase=3;
        }
        val=5 {
            5 > 2 == true;
            4 > 3 == true;
            increase=4;
        }
        val=4 {
            5 > 3 == true;
            4 > 4 == false;
            increase=5;
        }
        val=3 {
            5 > 4 == true;
            4 > 5 == false;
            increase=5;
        }
        val=2 {
            8 > 4 == true;
            7 > 5 == true;
            increase=6;
        }
        val=1 {
            8 > 5 == true;
            7 > 6 == true;
            increase=7;
        }
    }
    
    Обновление граждан {
        ctz[best.id] = best.val + best.increase;
        steps = best.increase - best.transition;
        loop through parties {
            if (party.id == best.id) continue;
            if (party.ctz >= best.othersMax) {
                if (steps > 0) {
                    ctz[party.id] = best.othersMax - 1;
                    steps--;
                } else {
                    ctz[party.id] = best.othersMax;
                }
            } else {
                ctz[party.id] = party.ctz;
            }
        }
    }
        
}
*/
