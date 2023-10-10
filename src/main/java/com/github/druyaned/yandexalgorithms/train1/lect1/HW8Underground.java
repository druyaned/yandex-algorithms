package com.github.druyaned.yandexalgorithms.train1.lect1;

import java.util.Scanner;

public class HW8Underground {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt(); // интервал меджу поездами на 1-м пути
        int b = scanner.nextInt(); // интервал на 2-м
        int n = scanner.nextInt(); // кол-во поездов на 1-м пути
        int m = scanner.nextInt(); // кол-во на 2-м
        T t = findT(a, b, n, m);
        if (t.min == -1) {
            System.out.println("-1");
        } else {
            System.out.println(t.min + " " + t.max);
        }
    }
    
    public static class T {
        
        public final int min, max;
        
        public T(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }
    
    public static T findT(int a, int b, int n, int m) {
        int t1Min = a * (n - 1) + n;
        int t1Max = a * (n + 1) + n;
        int t2Min = b * (m - 1) + m;
        int t2Max = b * (m + 1) + m;
        if (t1Max < t1Min || t1Max < t2Min) {
            return new T(-1, -1);
        }
        if (t2Max < t1Min || t2Max < t2Min) {
            return new T(-1, -1);
        }
        return new T(Integer.max(t1Min, t2Min), Integer.min(t1Max, t2Max));
    }

}
