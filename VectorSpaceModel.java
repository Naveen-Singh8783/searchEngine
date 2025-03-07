import java.util.*;

public class VectorSpaceModel {
    private InvertedIndex index;

    public VectorSpaceModel(InvertedIndex index) {
        this.index = index;
    }

    // Compute TF-IDF vector for a query
    public Map<String, Double> computeQueryVector(List<String> queryTerms) {
        Map<String, Double> queryVector = new HashMap<>();

        // Compute TF (term frequency) for the query
        Map<String, Integer> termFrequency = new HashMap<>();
        for (String term : queryTerms) {
            termFrequency.put(term, termFrequency.getOrDefault(term, 0) + 1);
        }

        // Compute TF-IDF for each term in the query
        for (String term : termFrequency.keySet()) {
            int tf = termFrequency.get(term);
            int df = index.getDocumentFrequency(term); // Document frequency
            int totalDocuments = index.getTotalDocuments();

            // IDF (inverse document frequency)
            double idf = Math.log((double) totalDocuments / (df + 1));

            // TF-IDF
            queryVector.put(term, tf * idf);
        }

        return queryVector;
    }

    // Compute TF-IDF vector for a document
    public Map<String, Double> computeDocumentVector(int docId, List<String> queryTerms) {
        Map<String, Double> documentVector = new HashMap<>();

        for (String term : queryTerms) {
            int tf = index.getTermFrequency(term, docId); // Term frequency in the document
            int df = index.getDocumentFrequency(term); // Document frequency
            int totalDocuments = index.getTotalDocuments();

            // IDF (inverse document frequency)
            double idf = Math.log((double) totalDocuments / (df + 1));

            // TF-IDF
            documentVector.put(term, tf * idf);
        }

        return documentVector;
    }

    // Compute cosine similarity between a document vector and a query vector
    public double cosineSimilarity(Map<String, Double> docVector, Map<String, Double> queryVector) {
        double dotProduct = 0.0;
        double docMagnitude = 0.0;
        double queryMagnitude = 0.0;

        for (String term : queryVector.keySet()) {
            if (docVector.containsKey(term)) {
                dotProduct += docVector.get(term) * queryVector.get(term);
            }
            docMagnitude += Math.pow(docVector.getOrDefault(term, 0.0), 2);
            queryMagnitude += Math.pow(queryVector.get(term), 2);
        }

        docMagnitude = Math.sqrt(docMagnitude);
        queryMagnitude = Math.sqrt(queryMagnitude);

        return dotProduct / (docMagnitude * queryMagnitude);
    }
}