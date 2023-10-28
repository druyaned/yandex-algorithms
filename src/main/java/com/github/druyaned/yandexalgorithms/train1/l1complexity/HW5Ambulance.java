package com.github.druyaned.yandexalgorithms.train1.l1complexity;

import java.util.Scanner;

public class HW5Ambulance {
    
    public static void main(String[] args) { // TODO: что-то не учел
        Scanner scanner = new Scanner(System.in);
        int k1 = scanner.nextInt(); // квартира 1
        int m = scanner.nextInt();  // этажей в подъезде
        int k2 = scanner.nextInt(); // квартира 2
        int p2 = scanner.nextInt(); // подъезд квартиры 2
        int n2 = scanner.nextInt(); // этаж квартиры 2
        P1N1 p1n1 = findP1N1(k1, m, k2, p2, n2);
        System.out.println(p1n1.p1 + " " + p1n1.n1);
    }
    
    public static class P1N1 {
        
        public final int p1, n1;
        
        public P1N1(int p1, int n1) {
            this.p1 = p1;
            this.n1 = n1;
        }
        
    }
    
    public static P1N1 findP1N1(int k1, int m, int k2, int p2, int n2) {
        int f2 = m * (p2 - 1) + n2; // этаж в мнимом одноподъездном доме кв. 2
        if (m < n2 || k2 < f2) { // недопустимый вход
            return new P1N1(-1, -1);
        }
        // ⊐ r - кол-во квартир на этаже (rMin - минимальное кол-во квартир на этаже)
        if (f2 == 1) { // непонятный r
            int f1Max = divUp(k1, k2);
            int p1Max = divUp(f1Max, m);
            int p1 = (p1Max == 1) ? 1 : 0;
            int n1 = (m == 1 || f1Max == 1) ? 1 : 0;
            return new P1N1(p1, n1);
        } // else (1 < f2)
        int rMin = divUp(k2, f2);
        int rMax = divUp(k2, f2 - 1) - 1;
        if (rMax < rMin) { // недопустимый вход
            return new P1N1(-1, -1);
        }
        if (k1 == k2) { // спец-кейс
            return new P1N1(p2, n2);
        }
        if (rMin < rMax) { // неодназначный вход
            int f1Min = divUp(k1, rMax);
            int f1Max = divUp(k1, rMin);
            int p1Min = divUp(f1Min, m);
            int p1Max = divUp(f1Max, m);
            int p1 = (p1Min == p1Max) ? p1Min : 0;
            int n1Min = (f1Min - 1) % m + 1;
            int n1Max = (f1Max - 1) % m + 1;
            int n1 = (n1Min == n1Max) ? n1Min : 0;
            return new P1N1(p1, n1);
        } else { // порядок
            int f1 = divUp(k1, rMin);
            int p1 = divUp(f1, m);
            int n1 = (f1 - 1) % m + 1;
            return new P1N1(p1, n1);
        }
    }
    
    private static int divUp(int numerator, int denominator) {
        return (numerator - 1) / denominator + 1;
    }

}
