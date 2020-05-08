import java.util.*;

class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> words = new HashMap<>();

        while (scanner.hasNext()) {
            String key = scanner.next().toLowerCase();
            if (words.containsKey(key)) {
                words.put(key, words.get(key) + 1);
            } else words.put(key, 1);
        }

        SortedSet<String> keys = new TreeSet<>(words.keySet());
        for (String key : keys) {
            System.out.println(key + " " + words.get(key));
        }

    }
}