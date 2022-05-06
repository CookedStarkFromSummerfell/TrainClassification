package pl.kalishak.trainclassif.util;

public class StringHelper {

    public static char[] filterUpperLetters(String words) {
        //return words.chars().mapToObj(i -> (char)i).filter(c -> Character.isUpperCase(c)).toList().toArray(new Character[0]);

        char[] result = new char[2];
        for (char c : words.toCharArray()) {
            int i = 0;
            if (Character.isUpperCase(c)) {
                result[i] = c;
                i++;
            }
        }

        return result;
    }

    public static char[] getInitials(String words) {
        return getInitials(words.split("\\s+"));
    }

    public static char[] getInitials(String... words) {
        //if (words.length == 0) {words = words[0].split("\\s+");}

        char[] result = new char[words.length + 2];
        for (int i = 0; i < words.length; i++) {
            result[i] = words[i].charAt(0);
        }

        return result;
    }
}
