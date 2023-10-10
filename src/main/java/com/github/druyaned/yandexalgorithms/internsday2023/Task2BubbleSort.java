package com.github.druyaned.yandexalgorithms.internsday2023;

/**
 * Задача 2 "Сортировка пузырьком".
 * Дан массив из различных целых чисел: [5, 11, 13, 3, 7, 9, X].
 * Единственная разрешенная операция - обмен соседних чисел.
 * Найдите минимальное значение X такое,
 * чтобы массив мог быть упорядочен по возрастанию значений
 * элементов ровно за 11 операций.
 * 
 * @author druyaned
 */
public class Task2BubbleSort {

    public static void main(String[] args) {
        int[] array = {5, 11, 13, 3, 7, 9};
        int totalOperationCount = 11;
        int currentOperationCount = 0;
        for (int i = 0; i < array.length; ++i) {
            for (int j = 1; j < array.length - i; ++j) {
                if (array[j] < array[j - 1]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                    ++currentOperationCount;
                }
            }
        }
        int leftOperationCount = totalOperationCount - currentOperationCount;
        int xIndex = array.length - leftOperationCount;
        int x = array[xIndex - 1] + 1;
        System.out.println(x);
    }

}
/*
5 11 13 3 7 9 x
5 11 3 7 9 13 x | 3
5 3 7 9 11 13 x | 6
3 5 7 9 11 13 x | 7
3 5 x 7 9 11 13 | 11
xMin = 5 + 1 = 6
*/
