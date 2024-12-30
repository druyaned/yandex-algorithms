package druyaned.yandexalgorithms.train5.l4binsearch.util;

import java.util.function.IntPredicate;

public class BinarySearch {
    
    /**
     * Left binary search returns the <u>first</u> index of element
     * in the {@code sortedArray} that satisfies the {@code condition}
     * or {@code sortedArray size} if there is no such an element.
     * 
     * <P><i>Example</i>:<br><code>
     * condition: { key &lt;= elem }<br>
     * ind: 0 1 2 3<br>
     * arr: 2 4 6 7<br>
     * left(arr, 1)=0<br>
     * left(arr, 2)=0<br>
     * left(arr, 3)=1<br>
     * left(arr, 4)=1<br>
     * left(arr, 8)=4<br>
     * </code>
     * 
     * @param sortedArray where to search
     * @param condition to find an element that satisfies it
     * @return the <u>first</u> index of element in the {@code sortedArray}
     *      that satisfies the {@code condition}
     *      or {@code sortedArray size} if there is no such an element
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
     * Right binary search returns the <u>last</u> index of element
     * in the {@code sortedArray} that satisfies the {@code condition}
     * or {@code -1} if there is no such an element.
     * 
     * <P><i>Example</i>:<br><code>
     * condition: { elem &lt;= key }<br>
     * ind: 0 1 2 3<br>
     * arr: 2 4 6 7<br>
     * left(arr, 1)=-1<br>
     * left(arr, 2)=0<br>
     * left(arr, 3)=0<br>
     * left(arr, 4)=1<br>
     * </code>
     * 
     * @param sortedArray where to search
     * @param condition to find an element that satisfies it
     * @return the <u>last</u> index of element in the {@code sortedArray}
     *      that satisfies the {@code condition}
     *      or {@code -1} if there is no such an element
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
