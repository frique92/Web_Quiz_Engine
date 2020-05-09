import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();

        int[] numbers = new int[count];

        for (int i = 0; i < count; i++) {
            numbers[i] = scanner.nextInt();
        }

        int result = scanner.nextInt();
        boolean findResult = false;
        for (int i = 0; i < count; i++) {
            if (numbers[i] == result) {
                findResult = true;
                break;
            }
        }

        System.out.println(findResult);
    }
}