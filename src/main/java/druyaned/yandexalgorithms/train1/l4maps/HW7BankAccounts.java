package druyaned.yandexalgorithms.train1.l4maps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class HW7BankAccounts {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String line;
        HashMap<String, Long> accounts = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(" ");
            String command = words[0];
            switch (command) {
                case "DEPOSIT" -> {
                    String name = words[1];
                    long sum = Long.parseLong(words[2]);
                    Long balance = accounts.get(name);
                    if (balance == null) {
                        accounts.put(name, sum);
                    } else {
                        accounts.put(name, balance + sum);
                    }
                }
                case "WITHDRAW" -> {
                    String name = words[1];
                    long sum = Long.parseLong(words[2]);
                    Long balance = accounts.get(name);
                    if (balance == null) {
                        accounts.put(name, -sum);
                    } else {
                        accounts.put(name, balance - sum);
                    }
                }
                case "TRANSFER" -> {
                    String name1 = words[1];
                    String name2 = words[2];
                    long sum = Long.parseLong(words[3]);
                    Long balance1 = accounts.get(name1);
                    if (balance1 == null) {
                        accounts.put(name1, -sum);
                    } else {
                        accounts.put(name1, balance1 - sum);
                    }
                    Long balance2 = accounts.get(name2);
                    if (balance2 == null) {
                        accounts.put(name2, sum);
                    } else {
                        accounts.put(name2, balance2 + sum);
                    }
                }
                case "INCOME" -> {
                    long percent = Long.parseLong(words[1]);
                    for (String name : accounts.keySet()) {
                        long balance = accounts.get(name);
                        if (balance > 0) {
                            long newBalance = (balance * percent) / 100 + balance;
                            accounts.put(name, newBalance);
                        }
                    }
                }
                case "BALANCE" -> {
                    String name = words[1];
                    Long balance = accounts.get(name);
                    if (balance == null) {
                        writer.write("ERROR\n");
                    } else {
                        writer.write(balance + "\n");
                    }
                }
                default -> throw new IllegalArgumentException("unknown command '" + command + "'");
            }
        }
    }
    
}
/*
Формат ввода
Входной файл содержит последовательность операций.
Возможны следующие операции:
BALANCE name - узнать остаток средств на счету клиента name.
DEPOSIT name sum - зачислить сумму sum на счет клиента name.
    Если у клиента нет счета, то счет создается.
WITHDRAW name sum - снять сумму sum со счета клиента name.
    Если у клиента нет счета, то счет создается.
TRANSFER name1 name2 sum - перевести сумму sum со счета клиента name1 на счет клиента name2.
    Если у какого-либо клиента нет счета, то ему создается счет.
INCOME p - начислить всем клиентам, у которых открыты счета, p% от суммы счета.
    Проценты начисляются только клиентам с положительным остатком на счету,
    если у клиента остаток отрицательный, то его счет не меняется.
    После начисления процентов сумма на счету остается целой,
    то есть начисляется только целое число денежных единиц.
    Дробная часть начисленных процентов отбрасывается.

Формат вывода
Для каждого запроса BALANCE программа должна вывести остаток на счету данного клиента.
Если же у клиента с запрашиваемым именем не открыт счет в банке, выведите ERROR.
*/
