import java.math.BigInteger;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String[] numbers = scanner.nextLine().split(" ");

        BigInteger a = new BigInteger(numbers[0]);
        BigInteger b = new BigInteger(numbers[1]);
        BigInteger c = new BigInteger(numbers[2]);
        BigInteger d = new BigInteger(numbers[3]);

        BigInteger output = a.negate().multiply(b).add(c).subtract(d);
        System.out.println(output);
    }
}