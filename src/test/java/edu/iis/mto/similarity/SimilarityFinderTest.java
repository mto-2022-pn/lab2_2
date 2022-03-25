package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    private SimilarityFinder similarityFinder;

    @BeforeEach
    void SetUpTest() {
        similarityFinder = new SimilarityFinder(
                (element, sequence) -> SearchResult.builder().build());
    }

    @Test
    void BothSeqEmpty() {
        int[] seq1 = {};
        int[] seq2 = {};
        double expected = 1.0d;
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(expected, result);
    }
}
