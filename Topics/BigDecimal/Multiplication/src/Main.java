import java.math.BigDecimal;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        String number1 = scanner.nextLine();
        String number2 = scanner.nextLine();

        BigDecimal bigDecimal1 = new BigDecimal(number1);
        BigDecimal bigDecimal2 = new BigDecimal(number2);
        System.out.println(bigDecimal1.multiply(bigDecimal2));
    }
}