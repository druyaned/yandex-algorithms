package druyaned.yandexalgorithms.train1.l1complexity;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Лекция 1, задача №1.
 * <p>Дана строка (UTF-8). Найти символ, встречающийся наибольшее количество раз.
 * <p><b>Решение</b>
 * <p>В UTF-8 больше 1 млн символов.
 * Создаю хэш-множество символов к количеству их в тексте.
 * После заполнения множества считаю максимальное количество.
 * 
 * @author druyaned
 */
public class Task1CountingCodePoints {
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Task1CountingCodePoints task1 = new Task1CountingCodePoints();
        String str = scanner.nextLine();
        Map<Integer, Integer> codePointToCount = task1.codePointToCount(str);
        System.out.println(task1.maxCount(codePointToCount));
    }
    
    public Map<Integer, Integer> codePointToCount(String str) {
        final int CODE_POINT_COUNT = str.codePointCount(0, str.length());
        Map<Integer, Integer> codePointToCount = new HashMap<>();
        for (int i = 0; i < CODE_POINT_COUNT; ++i) {
            Integer currentCodePoint = str.codePointAt(i);
            if (!codePointToCount.containsKey(currentCodePoint)) {
                codePointToCount.put(currentCodePoint, 1);
            } else {
                Integer currentCount = codePointToCount.get(currentCodePoint);
                codePointToCount.put(currentCodePoint, currentCount + 1);
            }
        }
        return codePointToCount;
    }
    
    public int maxCount(Map<Integer, Integer> codePointToCount) {
        Integer maxCount = 0;
        for (Map.Entry<Integer, Integer> entry : codePointToCount.entrySet()) {
            Integer currentCount = entry.getValue();
            if (maxCount.compareTo(currentCount) < 0) {
                maxCount = currentCount;
            }
        }
        return maxCount;
    }

}
