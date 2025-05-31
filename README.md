# Document Retrieval System - Search Engine Project

## Overview

This project implements a search engine that retrieves and ranks documents using three classic information retrieval models:

- **Vector Space Model (VSM)**
- **BM25**
- **Language Model with Dirichlet Smoothing**

The system is developed and tested on the **Cranfield dataset**, a standard benchmark in IR research. Evaluation is conducted using `trec_eval` with metrics such as MAP, P@5, and NDCG.

## Features

- XML parsing of Cranfield dataset (documents, queries, relevance judgments)
- Document preprocessing: tokenization, normalization, stopword removal, stemming
- Inverted index construction for fast lookup
- Query preprocessing aligned with document preprocessing
- Ranking models: VSM, BM25, LM
- Evaluation using standard IR metrics

## Project Structure

```
.
├── data/
│   ├── cran.all.1400.xml         # Document dataset
│   ├── cran.qry.xml              # Query dataset
│   └── cranqrel                  # Relevance judgments
├── src/
│   ├── XMLParser.java            # Parses documents and queries
│   ├── InvertedIndex.java        # Builds and manages inverted index
│   ├── SearchEngine.java         # Core search engine logic
│   ├── VectorSpaceModel.java     # VSM ranking logic
│   ├── BM25.java                 # BM25 ranking logic
│   └── LanguageModel.java        # Language model with Dirichlet smoothing
├── results/
│   ├── vsm_results.txt
│   ├── bm25_results.txt
│   └── lm_results.txt
├── trec_eval/                    # Directory containing trec_eval tool
├── README.md                     # Project documentation
└── ...
```

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Naveen-Singh8783/searchEngine.git
   cd searchEngine
   ```

2. **Compile the project:**
   Use your preferred Java IDE or command-line tools to compile all `.java` files in the `src/` directory.

3. **Run the Search Engine:**
   Execute `SearchEngine.java` with appropriate arguments (optional configuration of model selection and output paths).

4. **Evaluate the Results:**
   Use `trec_eval` to assess the system output:
   ```bash
   ./trec_eval/qrels.txt results/bm25_results.txt
   ./trec_eval/qrels.txt results/vsm_results.txt
   ./trec_eval/qrels.txt results/lm_results.txt
   ```

## Evaluation Metrics

- **Mean Average Precision (MAP)**
- **Precision at 5 (P@5)**
- **Normalized Discounted Cumulative Gain (NDCG)**

BM25 outperformed the other models across all metrics:

| Model | MAP    | P@5   | NDCG   |
|-------|--------|--------|--------|
| BM25  | 0.2911 | 0.3102 | 0.5409 |
| LM    | 0.2565 | 0.2640 | 0.5112 |
| VSM   | 0.1821 | 0.1787 | 0.4359 |

## Key Takeaways

- **BM25** proved the most effective for this dataset.
- The current implementation can benefit from enhancements in preprocessing and ranking logic.

## Future Enhancements

- Debug and fine-tune ranking algorithms for improved precision.
- Add additional evaluation metrics.
- Introduce n-gram analysis and advanced stemming.
- Extend the system to support larger datasets and multilingual corpora.

## Dataset & Tools

- **Cranfield Dataset:** [GitHub Repo](https://github.com/oussbenk/cranfield-trec-dataset)
- **TREC_Eval Tool:** [NIST GitHub](https://github.com/usnistgov/trec_eval)

## Author

**Naveen Singh**  
School of Computing, Dublin City University  
📧 Naveen.Singh2@mail.dcu.ie
