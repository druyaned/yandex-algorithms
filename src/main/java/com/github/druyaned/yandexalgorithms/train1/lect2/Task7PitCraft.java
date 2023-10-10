package com.github.druyaned.yandexalgorithms.train1.lect2;

import java.util.Scanner;

/**
 * Лекция 2, задача №7.
 * 
 * <p><b>Дано</b>
 * 
 * <p>В игре PitCraft двумерный мир состоит из блоков 1 на 1 метр.
 * Остров игрока - это набор столбцов из каменных блоков различной высоты
 * и окруженный морем. Над островом прошел дождь, который заполнил все низины,
 * а остальная вода утекла в море, не изменив его уровень.<br>
 * Определить количество блоков воды в низинах.
 * 
 * <p><b>Пример</b>
 * <pre>
 *        BwwB      0
 *        BwwB      2
 *   BwwwwBBwB      5
 *   BwwBwBBBBwwB   5
 * BwBwBBBBBBBBwBB  3
 * BwBBBBBBBBBBBBBB 1
 * BBBBBBBBBBBBBBBB 18
 * 3152343754732432 - only blocks 'B' without water 'w'
 * 3355555777744432 - with 'w'
 * 33555557777..... - max 'left->right'
 * ...........44432 - max 'right->left'
 * </pre>
 * 
 * <p><b>Решение</b>
 * 
 * <p>Суть решения представлена в разборе примера.<br>
 * Сначала нахожу правый (последний) максимум последовательности <i>maxH</i>.<br>
 * Потом прохожу слева направо до <i>maxH</i>, суммируя разности
 * <i>maxH</i> с текущим максимумом.
 * Аналогично прохожу справа налево.<br>
 * Результирующая сумма - это ответ.
 * 
 * @author druyaned
 */
public class Task7PitCraft {
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        int[] heights = new int[n];
        for (int i = 0; i < n; ++i) {
            heights[i] = scanner.nextInt();
        }
        Task7PitCraft task7 = new Task7PitCraft();
        int ind = task7.indexOfRightMaxHeight(heights);
        int left = task7.leftWaterCount(heights, ind);
        int right = task7.rightWaterCount(heights, ind);
        int waterCount = left + right;
        System.out.println(waterCount);
    }
    
    public int indexOfRightMaxHeight(int[] heights) {
        if (heights.length == 0) {
            return -1;
        }
        int indexOfRightMaxHeight = 0;
        int maxHeight = heights[0];
        for (int i = 1; i < heights.length; ++i) {
            if (maxHeight == heights[i]) {
                indexOfRightMaxHeight = i;
            } else if (maxHeight < heights[i]) {
                indexOfRightMaxHeight = i;
                maxHeight = heights[i];
            }
        }
        return indexOfRightMaxHeight;
    }
    
    public int leftWaterCount(int[] heights, int indexOfRightMaxHeight) {
        if (heights.length == 0) {
            return 0;
        }
        int leftWaterCount = 0;
        int currentMaxHeight = heights[0];
        for (int i = 1; i <= indexOfRightMaxHeight; ++i) {
            if (heights[i] < currentMaxHeight) {
                leftWaterCount += currentMaxHeight - heights[i];
            } else if (currentMaxHeight < heights[i]) {
                currentMaxHeight = heights[i];
            }
        }
        return leftWaterCount;
    }
    
    public int rightWaterCount(int[] heights, int indexOfRightMaxHeight) {
        if (heights.length == 0) {
            return 0;
        }
        int rightWaterCount = 0;
        int currentMaxHeight = heights[heights.length - 1];
        for (int i = heights.length - 2; indexOfRightMaxHeight < i; --i) {
            if (heights[i] < currentMaxHeight) {
                rightWaterCount += currentMaxHeight - heights[i];
            } else if (currentMaxHeight < heights[i]) {
                currentMaxHeight = heights[i];
            }
        }
        return rightWaterCount;
    }
    
}
