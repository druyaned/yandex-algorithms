package com.github.druyaned.yandexalgorithms.train1.lect6;

import java.util.function.IntPredicate;

public class BinarySearch {
    
    /**
     * Searches in the {@code sortedArray} the first {@code element}
     * which applies the expression {@code key <= element} ({@code bad -> good}).
     * <br><i>Example</i>:<br>
     * <code>
     * arr:  2  4  6  7<br>
     * ind:  0  1  2  3<br>
     * key:  1  2  3  4  5  6  7  8<br>
     * ans:  0  0  1  1  2  2  3 -1
     * </code>
     * 
     * @param sortedArray a sorted array to search the first acceptable element
     * @param key a value to search the first acceptable element
     * @return index of the first acceptable element or {@code -1} if there are no such elements
     */
    public static int left(int[] sortedArray, int key) {
        if (sortedArray.length == 0) {
            return -1;
        }
        int left = 0, right = sortedArray.length - 1, mid;
        while (left < right) {
            mid = (left + right) / 2;
            if (key <= sortedArray[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return key > sortedArray[left] ? -1 : left;
    }
    
    /**
     * Searches in the {@code sortedArray} the last {@code element}
     * which applies the expression {@code key >= element} ({@code good -> bad}).
     * <br><i>Example</i>:<br>
     * <code>
     * arr:  2  4  6  7<br>
     * ind:  0  1  2  3<br>
     * key:  1  2  3  4  5  6  7  8<br>
     * ans: -1  0  0  1  1  2  3  3
     * </code>
     * 
     * @param sortedArray a sorted array to search the last acceptable element
     * @param key a value to search the last acceptable element
     * @return index of the last acceptable element or {@code -1} if there are no such elements
     */
    public static int right(int[] sortedArray, int key) {
        if (sortedArray.length == 0) {
            return -1;
        }
        int left = 0, right = sortedArray.length - 1, mid;
        while (left < right) {
            mid = (left + right + 1) / 2;
            if (key >= sortedArray[mid]) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return key < sortedArray[right] ? -1 : right;
    }
    
    /**
     * Searches in the {@code sortedArray} the first {@code element}
     * which applies the expression <pre>comparator.test(element)</pre>.
     * The equivalent of the expression is {@code key <= element}.
     * 
     * @param sortedArray a sorted array to search the first acceptable element
     * @param comparator to apply {@code comparator.test(element)}
     * @return index of the first acceptable element or {@code -1} if there are no such elements
     */
    public static int left(int[] sortedArray, IntPredicate comparator) {
        if (sortedArray.length == 0) {
            return -1;
        }
        int left = 0, right = sortedArray.length - 1, mid;
        while (left < right) {
            mid = (left + right) / 2;
            if (comparator.test(sortedArray[mid])) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return !comparator.test(sortedArray[left]) ? -1 : left;
    }
    
    /**
     * Searches in the {@code sortedArray} the last {@code element}
     * which applies the expression <pre>comparator.test(element)</pre>.
     * The equivalent of the expression is {@code key >= element}.
     * 
     * @param sortedArray a sorted array to search the last acceptable element
     * @param comparator to apply {@code comparator.test(element)}
     * @return index of the last acceptable element or {@code -1} if there are no such elements
     */
    public static int right(int[] sortedArray, IntPredicate comparator) {
        if (sortedArray.length == 0) {
            return -1;
        }
        int left = 0, right = sortedArray.length - 1, mid;
        while (left < right) {
            mid = (left + right + 1) / 2;
            if (comparator.test(sortedArray[mid])) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return !comparator.test(sortedArray[right]) ? -1 : right;
    }
    
}
