package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimilarityFinderTest {

    SimilarityFinder finder;
    @BeforeEach
    void initSearch() {
        finder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
    }

    @Test
    void testSameNonEmptySequences() {
        int[] seq1 = {0, 1, 2};
        int[] seq2 = {0, 1, 2};
        finder = new SimilarityFinder(((elem, sequence) -> {
            switch (elem) {
                case 0:
                    return SearchResult.builder().withPosition(0).withFound(true).build();
                case 1:
                    return SearchResult.builder().withPosition(1).withFound(true).build();
                case 2:
                    return SearchResult.builder().withPosition(2).withFound(true).build();
                default:
                    return null;
            }
        }));
        assertEquals(1.0, finder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void testEmptySequences() {
        int[] seq1 = {};
        int[] seq2 = {};
        assertEquals(1.0, finder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void testFirstEmptySequence() {
        int[] seq1 = {};
        int[] seq2 = {0, 1, 2};
        assertEquals(0.0, finder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void testSecondEmptySequence() {
        int[] seq1 = {0, 1, 2};
        int[] seq2 = {};
        assertEquals(0.0, finder.calculateJackardSimilarity(seq1, seq2));
    }
}
