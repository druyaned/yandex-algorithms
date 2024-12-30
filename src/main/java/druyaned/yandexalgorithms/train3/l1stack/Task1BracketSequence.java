package druyaned.yandexalgorithms.train3.l1stack;

public class Task1BracketSequence {
    
    public static void main(String[] args) {
        String[] arr = {
            "{[][([]({}))]}",
            "{[][(][]({}))}",
            "(()",
            "())",
            "([]())",
            "([(]))",
        };
        for (int i = 0; i < arr.length; ++i) {
            System.out.printf("%s: %s\n", arr[i], check(arr[i]));
        }
    }
    
    private static String check(String s) {
        char[] arr = s.toCharArray();
        char[] stack = new char[arr.length];
        int size = 0;
        for (int i = 0; i < arr.length; ++i) {
            char c = arr[i];
            if (c == '(' || c == '{' || c == '[') {
                stack[size++] = c;
            }
            if (size == 0 && (c == ')' || c == '}' || c == ']')) {
                return "incorrect";
            }
            if (c == ')' && stack[size - 1] != '(' ||
                    c == '}' && stack[size - 1] != '{' ||
                    c == ']' && stack[size - 1] != '[') {
                return "incorrect";
            }
            if (c == ')' && stack[size - 1] == '(' ||
                    c == '}' && stack[size - 1] == '{' ||
                    c == ']' && stack[size - 1] == '[') {
                --size;
            }
        }
        return size > 0 ? "incorrect" : "correct";
    }
    
}
