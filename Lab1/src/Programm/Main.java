package Programm;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str;
        int[] numbers = new int[2];

        do{
            str = in.nextLine();

            while (str.length() > 17) {
                System.out.println("Your string is too long");
                str = in.nextLine();
            }

            if (str.equals("--h")) {
                printInstruction();
            }

            else if (isNumbers(str)){
                double square = Math.pow(Float.parseFloat(str), 2);
                System.out.printf("Квадрат = %.3f\nКорень = %.3f\n", square, Math.cbrt(square));
            }

            else if (withTwoNumbers(str, numbers)) {
                System.out.printf("%.3f\n", (float) numbers[0] / numbers[1]);
            }

            else {
                printSorted(str);
            }

        } while (!str.equals("q"));
    }
    public static void printInstruction(){
        System.out.println("Данная программа преобразовывает вашу строку, в зависимости от ее вида.");
        System.out.println("Функции данной программы:");
        System.out.println("Если на вход подали число, то программа выводит квадрат числа и кубический корень" +
                " квадрата числа, с точностью до трех знаков после запятой.");
        System.out.println("Если в начале строки подали два числа, разделенных пробелом то программа" +
                " опускает все нечисловые знаки и делит первое число на второе число и выводит результат" +
                " с точностью до трех знаков после запятой.");
        System.out.println("Если на вход подана строка, то программа сортирует символы в строке в" +
                " лексикографическом порядке (в порядке кодировки юникод) и вводит отсортированную строку" +
                " и число уникальных символов в строке.");

    }
    public static boolean isNumbers(String str){
        if (str == null || str.length() == 0) return false;

        int i = 0;
        if (str.charAt(0) == '-') {
            if (str.length() == 1) {
                return false;
            }
            i++;
        }

        char c;
        for (; i < str.length(); i++) {
            c = str.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    public static boolean withTwoNumbers(String str, int[] list){
        if (str == null || str.length() == 0) return false;

        int border = str.indexOf(' ');
        if (border < 1 || (border + 1) == str.length()) return false;

        int i = 0;
        if (str.charAt(0) == '-') {
            if (border == 1) {
                return false;
            }
            i++;
        }

        char c;
        for (; i < border; i++) {
            c = str.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        i = border + 1;
        if (str.charAt(border + 1) == '-') {
            if (border + 2 == str.length()) return false;
            if (!Character.isDigit(str.charAt(border+2))) return false;
            i++;
        }
        for (; i < str.length(); i++) {
            c = str.charAt(i);
            if (!Character.isDigit(c)) {
                break;
            }
        }
        list[0] = Integer.parseInt(str.substring(0, border));
        if (i == str.length() - 1){
            list[1] = Integer.parseInt(str.substring(border+1));
        }
        else {
            list[1] = Integer.parseInt(str.substring(border + 1, i));
        }
        return true;
    }
    public static void printSorted(String str){
        if (str == null || str.length() == 0 || str.equals("q")) return;

        char[] arr = str.toCharArray();
        Arrays.sort(arr);
        str = new String(arr);
        System.out.println(str);
    }
}
