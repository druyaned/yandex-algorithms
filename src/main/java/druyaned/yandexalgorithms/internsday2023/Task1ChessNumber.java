package druyaned.yandexalgorithms.internsday2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Задача 1 "Шахматный номер".
 * Номера набираются ходом коня, последовательно. Например, ходом коня
 * набирается номер 340-49-27. При этом телефонный номер не может начинаться
 * ни с 0, ни с 8.
 * <br>Определите сколько четырехзначных телефонных номеров набирается ходом коня.
 * <pre>
 * 7 8 9
 * 4 5 6
 * 1 2 3
 *   0  
 * </pre>
 * 
 * @author druyaned
 */
public class Task1ChessNumber {
    
    public static final int LENGTH = 4; // length of a telephone number
    public static final int DIGITS = 10;
    private static final Map<Integer, List<Integer>> PREV_NEXT = new HashMap<>();
    
    static {
        final int[] PREV_DIGITS = {0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 4, 6, 6, 6, 7, 7, 8, 8, 9, 9};
        final int[] NEXT_DIGITS = {4, 6, 6, 8, 7, 9, 4, 8, 0, 3, 9, 0, 1, 7, 2, 6, 1, 3, 2, 4};
        for (int i = 0; i < PREV_DIGITS.length; ++i) {
            List<Integer> nextDigits = PREV_NEXT.get(PREV_DIGITS[i]);
            if (nextDigits == null) {
                nextDigits = new ArrayList<>();
                PREV_NEXT.put(PREV_DIGITS[i], nextDigits);
            }
            nextDigits.add(NEXT_DIGITS[i]);
        }
    }
    
    public static void main(String[] args) {
        int count = 0;
        for (int prevDigit = 1; prevDigit < DIGITS; ++prevDigit) {
            if (prevDigit != 5 && prevDigit != 8) {
                count += count(1, prevDigit);
            }
        }
        System.out.println(count);
    }
    
    private static int count(int lvl, int prevDigit) {
        if (LENGTH == lvl) {
            return 1;
        }
        int count = 0;
        for (int nextDigit : PREV_NEXT.get(prevDigit)) {
            count += count(lvl + 1, nextDigit);
        }
        return count;
    }
    
}
/*
7 8 9
4 5 6
1 2 3
  0  

0: 4 6
1: 6 8
2: 7 9
3: 4 8
4: 0 3 9
6: 0 1 7
7: 2 6
8: 1 3
9: 2 4

1 2 3 4 6 7 9
1 1 1 1 1 1 1 | 5
    0 1 2 3 4 6 7 8 9
    2 1 2 1 2 2 2 2 2 | 16
        0 1 2 3 4 6 7 8 9
        6 2 4 4 5 5 4 2 4 | 36
            0  1 2 3 4  6  7 8 9
            10 7 8 7 14 12 9 6 9 | 82
*/
