package programm;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str;
        float[] numbers = new float[2];

        do{
            str = in.nextLine();

            while (str.length() > 17) {
                System.out.println("Your string is too long");
                str = in.nextLine();
            }

            if (str.equals("--h")) {
                printInstruction();
            } else if (isNumbers(str)){
                double square = Math.pow(Float.parseFloat(str), 2);
                System.out.printf("Квадрат = %.3f\nКорень = %.3f\n", square, Math.cbrt(square));
            } else if (withTwoNumbers(str, numbers)) {
                if (numbers[1] == 0) {
                    System.out.println("Второе число 0, нельзя делить на 0");
                } else System.out.printf("%.3f\n", numbers[0] / numbers[1]);
            } else {
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
        if (str == null) return false;

        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(str).matches();
    }
    public static boolean withTwoNumbers(String str, float[] list){
        if (str == null) return false;

        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)? -?\\d+(\\.\\d+)?");
        if (!pattern.matcher(str).matches()) return false;

        pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(str);
        for(int i = 0; i < 2; i++){
            matcher.find();
            list[i] = Float.parseFloat(str.substring(matcher.start(),matcher.end()));
        }
        return true;
    }
    public static void printSorted(String str){
        if (str == null || str.length() == 0 || str.equals("q")) return;

        int count = 0;
        char[] arr = str.toCharArray();

        Arrays.sort(arr);
        str = new String(arr);
        if (arr[0] != arr[1]) count++;
        if (arr[str.length()-1] != arr[str.length()-2]) count++;

        for (int i = 1; i < str.length() - 1; i++){
            if (arr[i] != arr[i-1] && arr[i] != arr[i+1]){
                count++;
            }
        }
        System.out.println(str + ' ' + count);
    }
}
