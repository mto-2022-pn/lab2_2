package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    private SimilarityFinder finder;

    @BeforeEach
    void setUp() {
        finder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
    }

    @Test
    void testBothSequencesEmpty() {
        int[] seq1 = {};
        int[] seq2 = {};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, result);
    }

    @Test
    void testFirstSequenceEmpty() {
        int[] seq1 = {};
        int[] seq2 = {1,2,3};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.0d, result);
    }

    @Test
    void testSecondSequenceEmpty() {
        int[] seq1 = {};
        int[] seq2 = {1,2,3};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.0d, result);
    }

}
