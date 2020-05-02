import java.time.LocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        LocalDate date = LocalDate.ofYearDay(scanner.nextInt(), scanner.nextInt());

        int dateOfEndMonth = date.getMonth().length(date.isLeapYear());

        boolean result = date.equals(LocalDate.of(date.getYear(), date.getMonth(), dateOfEndMonth));

        System.out.println(result ? "true" : "false");
    }
}