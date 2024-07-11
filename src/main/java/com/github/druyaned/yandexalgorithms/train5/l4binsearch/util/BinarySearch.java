package com.github.druyaned.yandexalgorithms.train5.l4binsearch.util;

import java.util.function.IntPredicate;

public class BinarySearch {
    
    public static void main(String[] args) {
        System.out.println("Testing BinarySearch (lft and rht)...");
        System.out.println("\nConditions {");
        System.out.println("  forLft={ key <= elem };");
        System.out.println("  forRht={ elem <= key };");
        System.out.println('}');
        int[] a1 = { 2, 4, 6, 7 };
        int[] a2 = { 2, 4, 6, 8, 9 };
        int minKey1 = -1, maxKey1 = 9;
        int minKey2 = -1, maxKey2 = 11;
        int[] l1 = new int[maxKey1 - minKey1 + 1];
        int[] l2 = new int[maxKey2 - minKey2 + 1];
        int[] r1 = new int[maxKey1 - minKey1 + 1];
        int[] r2 = new int[maxKey2 - minKey2 + 1];
        for (int key = minKey1, i = 0; key <= maxKey1; key++, i++) {
            int keyCopy = key;
            l1[i] = left(a1, elem -> keyCopy <= elem);
            r1[i] = right(a1, elem -> elem <= keyCopy);
        }
        for (int key = minKey2, i = 0; key <= maxKey2; key++, i++) {
            int keyCopy = key;
            l2[i] = left(a2, elem -> keyCopy <= elem);
            r2[i] = right(a2, elem -> elem <= keyCopy);
        }
        // array#1
        System.out.print("\nind:");
        for (int i = 0; i < a1.length; i++) {
            System.out.printf(" %2d", i);
        }
        System.out.print("\n a1:");
        for (int i = 0; i < a1.length; i++) {
            System.out.printf(" %2d", a1[i]);
        }
        System.out.print("\nkys:");
        for (int key = minKey1; key <= maxKey1; key++) {
            System.out.printf(" %2d", key);
        }
        System.out.print("\nlft:");
        for (int i = 0; i < l1.length; i++) {
            System.out.printf(" %2d", l1[i]);
        }
        System.out.print("\nrht:");
        for (int i = 0; i < r1.length; i++) {
            System.out.printf(" %2d", r1[i]);
        }
        System.out.println();
        // array#2
        System.out.print("\nind:");
        for (int i = 0; i < a2.length; i++) {
            System.out.printf(" %2d", i);
        }
        System.out.print("\n a2:");
        for (int i = 0; i < a2.length; i++) {
            System.out.printf(" %2d", a2[i]);
        }
        System.out.print("\nkys:");
        for (int key = minKey2; key <= maxKey2; key++) {
            System.out.printf(" %2d", key);
        }
        System.out.print("\nlft:");
        for (int i = 0; i < l2.length; i++) {
            System.out.printf(" %2d", l2[i]);
        }
        System.out.print("\nrht:");
        for (int i = 0; i < r2.length; i++) {
            System.out.printf(" %2d", r2[i]);
        }
        System.out.println();
    }
    
    /**
     * Left binary search returns the <b>first</b> index of element
     * in the {@code sortedArray} that satisfies the {@code condition}
     * or {@code sortedArray size} if there is no such an element.
     * <p><i>Example</i>:<br><code>
     * condition: { key &lt;= elem }<br>
     * ind: 0 1 2 3<br>
     * arr: 2 4 6 7<br>
     * left(arr, 1)=0<br>
     * left(arr, 2)=0<br>
     * left(arr, 3)=1<br>
     * left(arr, 4)=1<br>
     * left(arr, 8)=4<br>
     * </code></p>
     * 
     * @param sortedArray   where to search
     * @param condition to find an element that satisfies it
     * @return  the <b>first</b> index of element in the {@code sortedArray}
     *          that satisfies the {@code condition}
     *          or {@code sortedArray size} if there is no such an element
     */
    public static int left(int[] sortedArray, IntPredicate condition) {
        if (sortedArray == null || sortedArray.length == 0) {
            return 0;
        }
        int left = 0;
        int right = sortedArray.length - 1;
        while (left < right) {
            int mid = (left + right) / 2; // (l r)=(0 1) => mid=0
            if (condition.test(sortedArray[mid])) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return condition.test(sortedArray[left]) ? left : sortedArray.length;
    }
    
    /**
     * Right binary search returns the <b>last</b> index of element
     * in the {@code sortedArray} that satisfies the {@code condition}
     * or {@code -1} if there is no such an element.
     * <p><i>Example</i>:<br><code>
     * condition: { elem &lt;= key }<br>
     * ind: 0 1 2 3<br>
     * arr: 2 4 6 7<br>
     * left(arr, 1)=-1<br>
     * left(arr, 2)=0<br>
     * left(arr, 3)=0<br>
     * left(arr, 4)=1<br>
     * </code></p>
     * 
     * @param sortedArray   where to search
     * @param condition to find an element that satisfies it
     * @return  the <b>last</b> index of element in the {@code sortedArray}
     *          that satisfies the {@code condition}
     *          or {@code -1} if there is no such an element
     */
    public static int right(int[] sortedArray, IntPredicate condition) {
        if (sortedArray == null || sortedArray.length == 0) {
            return -1;
        }
        int left = 0;
        int right = sortedArray.length - 1;
        while (left < right) {
            int mid = (left + right + 1) / 2; // (l r)=(0 1) => mid=1
            if (condition.test(sortedArray[mid])) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return condition.test(sortedArray[right]) ? right : -1;
    }
    
}
