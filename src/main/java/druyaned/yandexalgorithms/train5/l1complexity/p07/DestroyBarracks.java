package druyaned.yandexalgorithms.train5.l1complexity.p07;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DestroyBarracks {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int mySoldiers = readInt(reader);
        int baracksHP = readInt(reader);
        int production = readInt(reader);
        System.out.printf("  b=%d m=%d p=%d\n", baracksHP, mySoldiers , production);//show
        Variables variables = new Variables(baracksHP, mySoldiers, production);
        variables.show();//show
        variables.runRound(0);
        variables.show();//show
        if (variables.isGoalReached()) {
            writer.write("1\n");
            System.out.print("1\n");//show
            return;
        }
        int minRound = Integer.MAX_VALUE;
        Variables copy = new Variables(baracksHP, mySoldiers, production);
        while (variables.canContinue()) {
            if (variables.canBaracksHPBeZero()) {
                variables.copyInto(copy);
                copy.runRoundWithZeroBaracksHP();
                if (copy.isGoalReached() && minRound > copy.r) {
                    minRound = copy.r;
                    System.out.print("\tWOW");//show
                }
                copy.showTabbed();//show
                while (copy.canContinue()) {
                    copy.runRoundWithMaxKillers();
                    if (copy.isGoalReached() && minRound > copy.r) {
                        minRound = copy.r;
                        System.out.print("\tWOW");//show
                    }
                    copy.showTabbed();//show
                }
            }
            variables.runRoundWithMaxKillers();
            if (variables.isGoalReached() && minRound > variables.r) {
                minRound = variables.r;
                System.out.print("WOW");//show
            }
            variables.show();//show
        }
        if (minRound == Integer.MAX_VALUE) {
            writer.write("-1\n");
            System.out.print("-1\n");//show
        } else {
            writer.write(Integer.toString(minRound) + "\n");
            System.out.print(Integer.toString(minRound) + "\n");//show
        }
    }
    
    private static class Variables {
        public int b, m, e, r; // baracksHP, mySoldiers, enemySoldiers, round, production
        public int prevB, prevE;
        public final int p;
        public Variables(int baracksHP, int mySoldiers, int production) {
            b = baracksHP;
            m = mySoldiers;
            p = production;
            e = r = prevB = prevE = 0;
        }
        public void runRound(int k) {
            prevB = b;
            prevE = e;
            b = b + k < m ? 0 : b + k - m;
            e -= k;
            m = m < e ? 0 : m - e;
            if (b > 0) {
                e += p;
            }
            r++;
        }
        public boolean isGoalReached() {
            return b == 0 && m > 0 && e == 0;
        }
        public boolean canContinue() {
            return (prevB > b || prevE > e) && (b != 0 || e != 0) && m > 0;
        }
        public boolean canBaracksHPBeZero() {
            return m >= b;
        }
        public void runRoundWithMaxKillers() {
            int k = m < e ? m : e;
            runRound(k);
        }
        public void runRoundWithZeroBaracksHP() {
            int k = m - b;
            runRound(k);
        }
        public void copyInto(Variables v) {
            v.prevB = prevB;
            v.prevE = prevE;
            v.b = b;
            v.m = m;
            v.e = e;
            v.r = r;
        }
        public void show() {//show
            System.out.printf("b=%d m=%d e=%d r=%d\n", b, m, e, r);//show
        }//show
        public void showTabbed() {//show
            System.out.printf("\tb=%d m=%d e=%d r=%d\n", b, m, e, r);//show
        }//show
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
/*
Задача для меня оказалась сложной потому, что ее трудно понять.
Для понимания и вывода алгоритма пришлось написать много примеров
на бумаге. В итоге я пришел к следующей идее.

Имею следующие действующие переменные:
    b -> baracksHP
    m -> mySoldiers
    e -> enemySoldiers
    r -> round
    p -> production
    k -> killers
    prevB -> previous baracksHP
    prevE -> previous enemySoldiers
Цель: достичь goal = (b==0 && m>0 && e==0),
    либо вывести -1, если невозможно досточь goal.
Раунд (без деталей):
1)  b -= (m - k)
2)  e -= k
3)  m -= e
4)  if (b>0) e += p
Загвоздка как выбрать k.
Ход решения:
1)  выполняю обязательный 1-ый раунд, формируя начальные значения переменных;
2)  покаМогу {
        еслиМогуЗанулить b {
            копирую переменные в copy
            выполняю раунд, чтоб занулить copy.b
            покаМогу {
                выполняю раунд с copy и с максимальным k
            }
        }
        выполняю раунд с максимальным k
    };
3)  после каждого раунда делаю проверку для minRound и при необходимости обновляю ее.

tests:
13 20 14
5

13 21 14
-1

7 14 3
3

10 11 15
4

5 14 3
6

1 2 1
-1

1 1 1
1

25 200 10
13

5000 10000 4999
1917
*/
