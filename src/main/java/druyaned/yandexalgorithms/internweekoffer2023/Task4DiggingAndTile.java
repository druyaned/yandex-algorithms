package druyaned.yandexalgorithms.internweekoffer2023;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Task4DiggingAndTile {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int k = Integer.parseInt(elems[0]);
        int n = Integer.parseInt(elems[1]);
        int m = Integer.parseInt(elems[2]);
        int[] days = new int[n];
        int[] sidewalks = new int[n];
        for (int i = 0; i < n; ++i) {
            elems = reader.readLine().split(" ");
            days[i] = Integer.parseInt(elems[0]);
            sidewalks[i] = Integer.parseInt(elems[1]);
        }
        // solve
        boolean[] involved = new boolean[k + 1];
        int involvedN = 0;
        for (int i = n; i > 0; --i) {
            int sidewalk = sidewalks[i - 1];
            if (!involved[sidewalk]) {
                ++involvedN;
            }
            involved[sidewalk] = true;
        }
        if (involvedN > m) {
            writer.write("-1\n");
            System.out.print("-1\n"); // TODO: debug
            return;
        }
        int[] prevDays = new int[k + 1];
        int left = m - involvedN;
        int[] sorrows = new int[n];
        int sorrowsN = 0;
        for (int i = n; i > 0; --i) {
            int day = days[i - 1];
            int sidewalk = sidewalks[i - 1];
            if (involved[sidewalk]) {
                involved[sidewalk] = false;
            } else if (prevDays[sidewalk] != day) {
                sorrows[sorrowsN++] = prevDays[sidewalk] - day;
            }
            prevDays[sidewalk] = day;
        }
        Arrays.sort(sorrows, 0, sorrowsN);
        for (int i = 0; i < sorrowsN; ++i) { // TODO: debug
            System.out.printf(" %d", sorrows[i]); // TODO: debug
        } // TODO: debug
        System.out.println(); // TODO: debug
        int totalSorrow = 0;
        for (int i = 0; i < sorrowsN - left; ++i) {
            totalSorrow += sorrows[i];
        }
        writer.write(totalSorrow + "\n");
        System.out.print(totalSorrow + "\n"); // TODO: debug
    }
    
}
/*
k - количество тротуаров;
n - количество планируемых раскопок;
m - количество укладок;
от каждого тротуара без плитки 1 единицу печали в день;
a - день раскопки, b - день укладки => получено b - a единиц печали;
a может быть == b (тогда b - a == 0);
тротуар, на котором проводились раскопки, может быть снова раскопан;
все раскопки и укладки должны законичться до выборов;
план раскопок: номер тротуара в конкретный день;
d[i] - день раскопок;
w[i] - номер тротуара для раскопки.
minSadness - найти наименьшее количество печали до выборов;

Ввод:
k n m
d[1] w[1]
...
d[n] w[n] (в порядке неубывания d[i])
Вывод:
minSadness (-1, если останутся раскопанные тротуары)
Note:
Описка в предпоследнем абзаце, 1-й строке условия: "в руке" ->  "в руки".

Ход решения #1 (во время контеста, когда могз был не свежий):
Надо идти с конца ввода.
Постепенно будут укладываться тротуары.
Когда закончатся укладки, а тротуар еще не встречался, то вывод -1.
Если встретиться уже уложенный и останутся укладки,
то добавлю к печали разницу prevDay[sidewalk] - curDay.

Нашел ошибку. Сначала надо пройтись по 1 разу.
Повторы не трогать. А потом добить для улучшения ответа.

Ход решения #2.
Сначала посчитаю, сколько тротуаров было задействовано.
if sidewalkN > m, то -1.
Далее считаю печаль.
А вот как считать печаль - это интересный вопрос.
Надо в виде отрезков это сделать.
Начало где будет? Например, рассмотрю для 2-го
тротуара дни [5 2]. Короче, среди оставшихся надо выбрать тех,
у которых разница начала и конца максимальна.
Надо сделать еще 1 массив разниц и отсортировать его.
Но сначала надо убедиться, что обязательное количество обслужено.
Т.е. решение в 3 этапа.

input:
5 6 4
1 3
1 2
2 3
4 2
4 1
5 2
output:
2
*/
