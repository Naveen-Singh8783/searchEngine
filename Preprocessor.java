import java.util.*;

public class Preprocessor {
        private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
        "a", "an", "the", "and", "or", "but", "if", "in", "on", "with", 
        "as", "by", "for", "of", "to", "at", "be", "is", "are", "was", "were"
    ));

    public static List<String> preprocess(String text) {
        // Tokenization
        String[] tokens = text.toLowerCase().split("\\W+");

        // Stopword removal and stemming
        List<String> processedTokens = new ArrayList<>();
        for (String token : tokens) {
            if (!STOPWORDS.contains(token) && !token.isEmpty()) {
                processedTokens.add(stem(token));
            }
        }

        return processedTokens;
    }

    public static String stem(String word) {
        if (word.length() < 3) return word;

        // Step 1a: Plural and past tense endings
        if (word.endsWith("sses")) {
            word = word.substring(0, word.length() - 2);
        } else if (word.endsWith("ies")) {
            word = word.substring(0, word.length() - 2);
        } else if (word.endsWith("ss")) {
            // No change
        } else if (word.endsWith("s")) {
            word = word.substring(0, word.length() - 1);
        }

        // Step 1b: -ed and -ing suffixes
        if (word.endsWith("eed")) {
            if (measure(word.substring(0, word.length() - 3)) > 0) {
                word = word.substring(0, word.length() - 1);
            }
        } else if (word.endsWith("ed") && containsVowel(word.substring(0, word.length() - 2))) {
            word = word.substring(0, word.length() - 2);
        } else if (word.endsWith("ing") && containsVowel(word.substring(0, word.length() - 3))) {
            word = word.substring(0, word.length() - 3);
        }

        // Step 1c: Replace "y" with "i" if preceded by a vowel
        if (word.endsWith("y") && containsVowel(word.substring(0, word.length() - 1))) {
            word = word.substring(0, word.length() - 1) + "i";
        }

        return word;
    }

    // Measures the number of vowel-consonant sequences (m)
    private static int measure(String word) {
        int count = 0;
        boolean vowelSeen = false;
        
        for (int i = 0; i < word.length(); i++) {
            if (isVowel(word.charAt(i))) {
                vowelSeen = true;
            } else if (vowelSeen) {
                count++;
                vowelSeen = false;
            }
        }
        return count;
    }

    // Checks if a string contains a vowel
    private static boolean containsVowel(String word) {
        for (char c : word.toCharArray()) {
            if (isVowel(c)) return true;
        }
        return false;
    }

    // Checks if a character is a vowel
    private static boolean isVowel(char c) {
        return "aeiou".indexOf(c) != -1;
    }
}