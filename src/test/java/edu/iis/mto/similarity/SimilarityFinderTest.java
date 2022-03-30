package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    @Test
    void emptySequences() {
        int[] seq1 = new int[0];
        int[] seq2 = new int[0];

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        double res = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1, res);
    }

    @Test
    void firstSeqEmpty() {
        int[] seq1 = new int[0];
        int[] seq2 = new int[]{1, 4};

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        double res = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, res);
    }

    @Test
    void secondSeqEmpty() {
        int[] seq1 = new int[]{6, 7};
        int[] seq2 = new int[0];

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        double res = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, res);
    }

    @Test
    void indenticalSeq() {
        int[] seq1 = new int[]{1, 5, 9, 3};
        int[] seq2 = new int[]{1, 5, 9, 3};

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> {
            SearchResult searchResult = SearchResult.builder().build();
            if (elem == 1) {
                searchResult = SearchResult.builder().withPosition(0).withFound(true).build();
            } else if (elem == 5) {
                searchResult = SearchResult.builder().withPosition(1).withFound(true).build();
            } else if (elem == 9) {
                searchResult = SearchResult.builder().withPosition(2).withFound(true).build();
            } else if (elem == 3) {
                searchResult = SearchResult.builder().withPosition(4).withFound(true).build();
            }
            return searchResult;
        });
        double res = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1, res);
    }
}
