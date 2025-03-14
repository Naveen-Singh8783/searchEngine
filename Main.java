import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Paths to the XML files
        String documentsFilePath = "/C:/Users/91951/Desktop/searchEngine/Dataset/cran.all.1400.xml";
        String queriesFilePath = "/C:/Users/91951/Desktop/searchEngine/cran.qry_modified.xml";

        // Parse the documents and queries
        List<Document> documents = XMLParser.parseDocuments(documentsFilePath);
        List<Query> queries = XMLParser.parseQueries(queriesFilePath);

       // Example: Debug tokens for the first document and query
        // Document doc = documents.get(0);
        // Query query = queries.get(0);
        // List<String> docTokens = Preprocessor.preprocess(doc.getBody());
        // List<String> queryTokens = Preprocessor.preprocess(query.getText());
        // System.out.println("Doc Tokens: " + docTokens);
        // System.out.println("Query Tokens: " + queryTokens);

        // Build the inverted index
        InvertedIndex index = new InvertedIndex();
        for (Document doc : documents) {
            List<String> tokens = Preprocessor.preprocess(doc.getBody());
            index.addDocument(doc.getId(), tokens);
        }

        // Create the search engine
        SearchEngine searchEngine = new SearchEngine(index);

        // Process each query and generate results for all models
        for (Query query : queries) {
            // Preprocess query
            List<String> queryTokens = Preprocessor.preprocess(query.getText());

            // Perform search using all models
            List<Integer> resultsVSM = searchEngine.search(queryTokens, "VSM");
            List<Integer> resultsBM25 = searchEngine.search(queryTokens, "BM25");
            List<Integer> resultsLM = searchEngine.search(queryTokens, "LM");

            // Compute scores for each model
            Map<Integer, Double> scoresVSM = searchEngine.computeScores(queryTokens, resultsVSM, "VSM");
            Map<Integer, Double> scoresBM25 = searchEngine.computeScores(queryTokens, resultsBM25, "BM25");
            Map<Integer, Double> scoresLM = searchEngine.computeScores(queryTokens, resultsLM, "LM");

            // Generate output files for trec_eval
            Evaluation.generateOutputFile(resultsVSM, String.valueOf(query.getId()), "VSM", scoresVSM, "vsm_results.txt");
            Evaluation.generateOutputFile(resultsBM25, String.valueOf(query.getId()), "BM25", scoresBM25, "bm25_results.txt");
            Evaluation.generateOutputFile(resultsLM, String.valueOf(query.getId()), "LM", scoresLM, "lm_results.txt");
            
        }

        System.out.println("Results generated. Run trec_eval to evaluate.");
    }
}