import java.util.*;

public class SearchEngine {
    private InvertedIndex index;
    private VectorSpaceModel vsm;
    private BM25 bm25;
    private LanguageModel lm;

    public SearchEngine(InvertedIndex index) {
        this.index = index;
        this.vsm = new VectorSpaceModel(index);
        this.bm25 = new BM25(index); // Pass index to BM25
        this.lm = new LanguageModel(index); // Pass index to LM
    }

    public List<Integer> search(List<String> queryTerms, String model) {
        // Aggregate documents for all query terms
        List<Integer> relevantDocs = new ArrayList<>();
        for (String term : queryTerms) {
            relevantDocs.addAll(index.search(term));
        }

        // Remove duplicates but retain document IDs for scoring
        Set<Integer> uniqueDocs = new LinkedHashSet<>(relevantDocs);
        relevantDocs = new ArrayList<>(uniqueDocs);

        // Rank documents
        switch (model) {
            case "VSM":
                return rankDocumentsVSM(queryTerms, relevantDocs);
            case "BM25":
                return rankDocumentsBM25(queryTerms, relevantDocs);
            case "LM":
                return rankDocumentsLM(queryTerms, relevantDocs);
            default:
                throw new IllegalArgumentException("Invalid model: " + model);
        }
    }

    public Map<Integer, Double> computeScores(List<String> queryTerms, List<Integer> docIds, String model) {
        switch (model) {
            case "VSM":
                return computeScoresVSM(queryTerms, docIds);
            case "BM25":
                return computeScoresBM25(queryTerms, docIds);
            case "LM":
                return computeScoresLM(queryTerms, docIds);
            default:
                throw new IllegalArgumentException("Invalid model: " + model);
        }
    }

    // ================== VSM ==================
    private List<Integer> rankDocumentsVSM(List<String> queryTerms, List<Integer> docIds) {
        Map<Integer, Double> scores = computeScoresVSM(queryTerms, docIds);
        return sortDocumentsByScore(docIds, scores);
    }

    private Map<Integer, Double> computeScoresVSM(List<String> queryTerms, List<Integer> docIds) {
        Map<Integer, Double> scores = new HashMap<>();
        Map<String, Double> queryVector = vsm.computeQueryVector(queryTerms);

        for (int docId : docIds) {
            Map<String, Double> docVector = vsm.computeDocumentVector(docId, queryTerms);
            scores.put(docId, vsm.cosineSimilarity(docVector, queryVector));
        }
        return scores;
    }

    // ================== BM25 ==================
    private List<Integer> rankDocumentsBM25(List<String> queryTerms, List<Integer> docIds) {
        Map<Integer, Double> scores = computeScoresBM25(queryTerms, docIds);
        return sortDocumentsByScore(docIds, scores);
    }

    private Map<Integer, Double> computeScoresBM25(List<String> queryTerms, List<Integer> docIds) {
        Map<Integer, Double> scores = new HashMap<>();
        for (int docId : docIds) {
            scores.put(docId, bm25.score(queryTerms, docId));
        }
        return scores;
    }

    // ================== LM ==================
    private List<Integer> rankDocumentsLM(List<String> queryTerms, List<Integer> docIds) {
        Map<Integer, Double> scores = computeScoresLM(queryTerms, docIds);
        return sortDocumentsByScore(docIds, scores);
    }

    private Map<Integer, Double> computeScoresLM(List<String> queryTerms, List<Integer> docIds) {
        Map<Integer, Double> scores = new HashMap<>();
        for (int docId : docIds) {
            scores.put(docId, lm.score(queryTerms, docId));
        }
        return scores;
    }

    // ================== Helper ==================
    private List<Integer> sortDocumentsByScore(List<Integer> docIds, Map<Integer, Double> scores) {
        docIds.sort((docId1, docId2) -> Double.compare(scores.get(docId2), scores.get(docId1)));
        return docIds;
    }
}