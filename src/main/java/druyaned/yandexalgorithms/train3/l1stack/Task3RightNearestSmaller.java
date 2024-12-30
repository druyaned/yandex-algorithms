package druyaned.yandexalgorithms.train3.l1stack;

public class Task3RightNearestSmaller {
    
    private static class Pair {
        public final int val, ind;
        public Pair(int val, int ind) {
            this.val = val;
            this.ind = ind;
        }
    }
    
    public static void main(String[] args) {
        int[] arr = {7, 2, 4, 5, 3, 2, 5, 1, 5, 4};
        int n = arr.length;
        int[] ans = new int[n];
        Pair[] stack = new Pair[n];
        int size = 0;
        for (int i = 0; i < n; ++i) {
            while (size > 0 && arr[i] < stack[size - 1].val) {
                ans[stack[--size].ind] = i;
            }
            stack[size++] = new Pair(arr[i], i);
        }
        while (size > 0) {
            ans[stack[--size].ind] = n;
        }
        System.out.printf("ind:  0");
        for (int i = 1; i < n; ++i) {
            System.out.printf(" %2d", i);
        }
        System.out.println();
        System.out.printf("arr: %2d", arr[0]);
        for (int i = 1; i < n; ++i) {
            System.out.printf(" %2d", arr[i]);
        }
        System.out.println();
        System.out.printf("ans: %2d", ans[0]);
        for (int i = 1; i < n; ++i) {
            System.out.printf(" %2d", ans[i]);
        }
        System.out.println();
    }
    
}
/*
Задача: найти индексы ближайшего меньшего справа для каждого элемента.
ind: 0  1  2  3  4  5  6  7  8  9
arr: 7  2  4  5  3  2  5  1  5  4
ans: 1  7  4  4  5  7  7 10  9 10
*/