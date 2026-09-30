package framework.util;

import java.util.Scanner;

/**
 * Utility untuk membaca input string dari keyboard.
 * Berada di layer framework karena bergantung pada System.in.
 */
public class InputUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static String input(String info) {
        System.out.print(info + " : ");
        return scanner.nextLine();
    }
}