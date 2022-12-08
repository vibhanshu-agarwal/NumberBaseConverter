package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    private static final boolean DEBUG = false;

    public static void main(String[] args) {
        // write your code here

        String input = "";
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("/exit")) {
                break;
            } else {
                String[] inputArray = input.split(" ");
                int sourceBase = Integer.parseInt(inputArray[0]);
                int targetBase = Integer.parseInt(inputArray[1]);
                String number = "";
                do {
                    System.out.println("Enter number in base " + sourceBase + " to convert to base " + targetBase + " (To go back type /back)");
                    number = scanner.next();
                    if (!number.equalsIgnoreCase("/back")) {
                        System.out.println("Conversion result: " + convert(number, sourceBase, targetBase));
                        System.out.println();
                    }
                } while (!number.equalsIgnoreCase("/back"));
            }
        } while (!input.equalsIgnoreCase("/exit"));
    }

    private static String convert(String number, int sourceBase, int targetBase) {
        String bigNumber = toDecimal(number, sourceBase);
        if (DEBUG)
            System.out.println("bigNumber = " + bigNumber);
        return fromDecimal(bigNumber, targetBase);
    }


    private static String toDecimal(String number, int base) {
        String[] numbers = number.split("\\.");

        BigInteger integerPart = BigInteger.ZERO;
        BigDecimal fractionalPartDecimal = BigDecimal.ZERO;

        if (numbers.length > 0) {

            integerPart = new BigInteger(numbers[0], base);
            fractionalPartDecimal = new BigDecimal("0");

            if (numbers.length > 1) {
                String fractionalPart = numbers[1];

                for (int i = 0; i < fractionalPart.length(); i++) {
                    int digit = Character.getNumericValue(fractionalPart.charAt(i));
                    fractionalPartDecimal = fractionalPartDecimal.add(
                            new BigDecimal(digit).divide(
                                    new BigDecimal(base).pow(i + 1), 5, RoundingMode.HALF_UP));
                }
            }
        }
        if (DEBUG) {
            System.out.println("integerPart = " + integerPart);
            System.out.println("fractionalPartDecimal = " + fractionalPartDecimal);
        }
        return new BigDecimal(integerPart).add(fractionalPartDecimal).toPlainString();
    }

    private static String fromDecimal(String number, int base) {
        String[] numbers = number.split("\\.");

        String integerPart = "";
        StringBuilder fractionalPartResult = new StringBuilder();

        if (numbers.length > 0) {

            integerPart = new BigInteger(numbers[0]).toString(base);

            if (numbers.length > 1) {
                String fractionalPart = "0." + numbers[1];
                BigDecimal fractionalPartCalc = new BigDecimal(fractionalPart).setScale(5, RoundingMode.HALF_UP);
                BigDecimal[] divResult = null;
                BigDecimal baseDecimal = new BigDecimal(base).setScale(5, RoundingMode.HALF_UP);

                do {
                    if (DEBUG)
                        System.out.println("fractionalPartCalc = " + fractionalPartCalc.toPlainString());

                    divResult = fractionalPartCalc
                            .multiply(baseDecimal)
                            .setScale(5, RoundingMode.HALF_UP)
                            .divideAndRemainder(BigDecimal.ONE);

                    if(DEBUG)
                        System.out.format("divResult[0]= %1$s, divResult[1]= %2$s.%n", divResult[0], divResult[1]);

                    fractionalPartResult.append(
                            Character.forDigit(divResult[0].intValue(), base));
                    fractionalPartCalc = divResult[1];

                } while (divResult[1].compareTo(BigDecimal.ZERO) != 0
                        && fractionalPartResult.length() < fractionalPartCalc.scale());

            }
        }
        if (DEBUG) {
            System.out.println("integerPart = " + integerPart);
            System.out.println("fractionalPartResult = " + fractionalPartResult);
        }

        //Make fractional part result to 5 digits
        if (!fractionalPartResult.isEmpty() && fractionalPartResult.length() < 5) {
            fractionalPartResult.append("0".repeat(5 - fractionalPartResult.length()));
        }
        return integerPart + (fractionalPartResult.isEmpty() ? "" : "." + fractionalPartResult);
    }
}

//    private static String fromDecimal(String number, int base) {
//        String[] numbers = number.split("\\.");
//
//        BigDecimal baseDecimal = new BigDecimal(base).setScale(5, RoundingMode.HALF_UP);
//        BigDecimal bigNumber = new BigDecimal(number);
//        bigNumber = bigNumber.multiply(baseDecimal.pow(6)).setScale(5, RoundingMode.HALF_UP);
//
//        StringBuilder result = new StringBuilder();
//        BigDecimal[] div = null;
//        do {
//            div = bigNumber.divideAndRemainder(baseDecimal);
//            result.append(Character.forDigit(div[1].intValue(), base));
//            bigNumber = div[0];
//        } while (div[1].compareTo(BigDecimal.ZERO) != 0);
//
//        return result.reverse().toString();
//    }
//}

//1. Divided the number into integer part and fractional part.
//        2. Converted the integer part with new BigInteger(integer, sourceBase).toString(targetBase);
//        3. Converted the fractional part(if not in 10) to 10. Used char to number conversion in the calculation. (Character.getNumericValue(char x)).
//        4. 10 converted to the right one. Using Character.forDigit(integer, targetBase) converted the numbers to the desired character and added to the final value.
//        5. Combine the integer part and the fractional part. Output the result. *
//
//        *In the task we are asked to round the fractional part (if any) to 5 digits. So when converting a fractional part from 10 to any other part, I stopped the conversion after 5 characters. Or if there are less characters, I add 0 to make it 5 characters. If 10 is already a target, then we just round up using setScale.
//        Probably not the most correct way, but it works)