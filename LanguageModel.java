import java.util.*;

public class LanguageModel {
    private InvertedIndex index;
    private static final double MU = 1000.0;
    private static final double EPSILON = 1e-10; // Small value to avoid log(0)

    public LanguageModel(InvertedIndex index) {
        this.index = index;
    }

    public double score(List<String> queryTerms, int docId) {
        double score = 0.0;
        int docLength = index.getDocumentLength(docId);

        for (String term : queryTerms) {
            int collFreq = index.getCollectionFrequency(term);
            if (collFreq == 0) continue; // Skip OOV terms

            int tf = index.getTermFrequency(term, docId);
            int totalTerms = index.getTotalTermsInCollection();

            double collProb = (double) collFreq / totalTerms;
            double prob = (tf + MU * collProb) / (docLength + MU);
            prob = Math.max(prob, EPSILON); // Ensure prob > 0
            score += Math.log(prob);
        }
        return score;
    }
}