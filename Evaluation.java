import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Evaluation {
    public static void generateOutputFile(List<Integer> rankedDocs, String queryId, String model, Map<Integer, Double> scores, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath, true)) { // Append mode
            int rank = 1; // Start ranking from 1
            for (int docId : rankedDocs) {
                //if(rank > 100) break; // Limit output to top 100 documents
                // Generate the line in trec_eval format
                String line = String.format(
                        "%s 0 %d %d %.4f %s_run\n", // Format: query_id iter document_id rank similarity run_id
                        queryId, // query_id
                        docId,   // document_id
                        rank,    // rank
                        scores.getOrDefault(docId, 0.0), // similarity score (default to 0.0 if not found)
                        model    // run_id
                );

                // Write the line to the file
                writer.write(line);

                // Increment the rank for the next document
                rank++;
                //if(rank > 100)break;
            }
            System.out.println("Results appended to output file: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        }
    }
}