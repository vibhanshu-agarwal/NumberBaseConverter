import java.math.BigInteger;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        BigInteger number = scanner.nextBigInteger();
        //Find the smallest number with factorial greater than or equal to number
        BigInteger factorial = BigInteger.ONE;
        BigInteger i = BigInteger.ONE;
        while (factorial.compareTo(number) < 0) {
            factorial = factorial.multiply(i);
            i = i.add(BigInteger.ONE);
        }
        System.out.println(i.subtract(BigInteger.ONE));
    }
}