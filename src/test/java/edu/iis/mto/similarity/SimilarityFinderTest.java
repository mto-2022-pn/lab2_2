package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    private SimilarityFinder finder;

    @Test
    void testBothSequencesEmpty() {
        finder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        int[] seq1 = {};
        int[] seq2 = {};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, result);
    }

    @Test
    void testFirstSequenceEmpty() {
        finder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        int[] seq1 = {};
        int[] seq2 = {1,2,3};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.0d, result);
    }

    @Test
    void testSecondSequenceEmpty() {
        finder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        int[] seq1 = {};
        int[] seq2 = {1,2,3};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.0d, result);
    }

    @Test
    void testSequencesEquals() {
        finder = new SimilarityFinder((elem, seq) -> {
            SearchResult result;
            switch(elem){
                case 1:
                case 2:
                case 3:
                    result = SearchResult.builder().withFound(true).build();
                    break;
                default:
                    result = SearchResult.builder().withFound(false).build();
            }
            return result;
        });
        int[] seq1 = {1,2,3};
        int[] seq2 = {1,2,3};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, result);
    }

}
