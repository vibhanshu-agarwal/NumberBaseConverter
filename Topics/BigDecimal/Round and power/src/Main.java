import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        int power = scanner.nextInt();
        int scale = scanner.nextInt();

        BigDecimal number = scanner.nextBigDecimal();

        System.out.println(number.setScale(scale, RoundingMode.FLOOR)
                .pow(power));
    }
}