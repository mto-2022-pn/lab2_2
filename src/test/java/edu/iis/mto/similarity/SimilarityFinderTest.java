package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iis.mto.searcher.SearchResult;

class SimilarityFinderTest {

    private static double IDENTICAL = 1.0d;
    private static double TOTTALLY_DIFFRENT = 0.0d;

    @Test
    void testEmptyArrays() {
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
        int seq1[] = {};
        int seq2[] = {};
        double res = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(IDENTICAL, res);
    }

    @Test
    void testFirstArrayAsEmpty() {
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
        int seq1[] = {};
        int seq2[] = {3,5,7,9};
        double res = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(TOTTALLY_DIFFRENT, res);
    }

    @Test
    void testSecondArrayAsEmpty() {
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
        int seq1[] = {1,6,9,15};
        int seq2[] = {};
        double res = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(TOTTALLY_DIFFRENT, res);
    }

    @Test
    void testArraysDontHaveCommonElements() {
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
        int seq1[] = {0,5,11,15};
        int seq2[] = {2,3,7,99};
        double res = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(TOTTALLY_DIFFRENT, res);
    }

}
