import java.util.*;

public class Preprocessor {
    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
            "a", "an", "the", "and", "or", "but", "if", "in", "on", "with", "as", "by", "for"
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

    private static String stem(String word) {
        // Apply a simplified version of the Porter Stemming Algorithm
        if (word.length() <= 2) {
            return word; // No stemming for very short words
        }

        // Step 1a: Remove plurals and -ed or -ing suffixes
        if (word.endsWith("sses")) {
            word = word.substring(0, word.length() - 2); // Replace "sses" with "ss"
        } else if (word.endsWith("ies")) {
            word = word.substring(0, word.length() - 2); // Replace "ies" with "i"
        } else if (word.endsWith("ss")) {
            // Do nothing (keep "ss")
        } else if (word.endsWith("s")) {
            word = word.substring(0, word.length() - 1); // Remove "s"
        }

        // Step 1b: Remove -ed and -ing suffixes
        if (word.endsWith("eed")) {
            // Do nothing (keep "eed")
        } else if (word.endsWith("ed")) {
            word = word.substring(0, word.length() - 2); // Remove "ed"
        } else if (word.endsWith("ing")) {
            word = word.substring(0, word.length() - 3); // Remove "ing"
        }

        // Step 2: Remove common suffixes
        if (word.endsWith("ational")) {
            word = word.substring(0, word.length() - 5) + "e"; // Replace "ational" with "ate"
        } else if (word.endsWith("tional")) {
            word = word.substring(0, word.length() - 2); // Replace "tional" with "tion"
        } else if (word.endsWith("izer")) {
            word = word.substring(0, word.length() - 1); // Replace "izer" with "ize"
        }

        // Step 3: Remove -al, -ence, etc.
        if (word.endsWith("al")) {
            word = word.substring(0, word.length() - 2); // Remove "al"
        } else if (word.endsWith("ence")) {
            word = word.substring(0, word.length() - 4); // Remove "ence"
        }

        // Step 4: Remove -ity, -able, etc.
        if (word.endsWith("ity")) {
            word = word.substring(0, word.length() - 3); // Remove "ity"
        } else if (word.endsWith("able")) {
            word = word.substring(0, word.length() - 4); // Remove "able"
        }

        // Step 5: Remove -ant, -ence, etc.
        if (word.endsWith("ant")) {
            word = word.substring(0, word.length() - 3); // Remove "ant"
        }

        // Step 6: Remove final 'e' if necessary
        if (word.length() > 1 && word.endsWith("e")) {
            word = word.substring(0, word.length() - 1); // Remove final "e"
        }

        return word;
    }
}