package com.github.druyaned.yandexalgorithms.train3.l1stack;

public class Task4Factorial {
    
    public static void main(String[] args) {
        for (int i = 1; i <= 5; ++i) {
            System.out.printf("fact(%d)=%d\n", i, fact(i));
        }
    }
    
    public static int fact(int a) {
        if (a <= 0 || 13 <= a) {
            return -1;
        }
        int[] stack = new int[a];
        int size = 0;
        for (int i = a; i > 0; --i) {
            stack[size++] = i;
        }
        int fact = stack[--size];
        while (size > 0) {
            fact *= stack[--size];
        }
        return fact;
    }
    
}
