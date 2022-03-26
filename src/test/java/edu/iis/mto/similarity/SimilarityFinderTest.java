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
        int seq2[] = { 3, 5, 7, 9 };
        double res = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(TOTTALLY_DIFFRENT, res);
    }

    @Test
    void testSecondArrayAsEmpty() {
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
        int seq1[] = { 1, 6, 9, 15 };
        int seq2[] = {};
        double res = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(TOTTALLY_DIFFRENT, res);
    }

    @Test
    void testArraysDontHaveCommonElements() {
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
        int seq1[] = { 0, 5, 11, 15 };
        int seq2[] = { 2, 3, 7, 99 };
        double res = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(TOTTALLY_DIFFRENT, res);
    }

    @Test
    void testArraysHaveSomeEleementInCommon() {
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> {
            SearchResult result = SearchResult.builder().build(); // default: not found (position doesn't really matter
                                                                  // to test Similarity)
            switch (element) {
                case -15:
                    result = SearchResult.builder().withPosition(0).withFound(true).build();
                    break;
                case 1:
                    result = SearchResult.builder().withPosition(1).withFound(true).build();
                    break;
                case 16:
                    result = SearchResult.builder().withPosition(4).withFound(true).build();
                    break;
            }
            return result;
        });
        int seq1[] = { -15, -8, 0, 1, 11, 16 };
        int seq2[] = { -15, 1, 8, 9, 16 };
        double res = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(3d / (seq1.length + seq2.length - 3), res);
    }

    @Test
    void testArraysAreTheSame() {
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> {
            SearchResult result = SearchResult.builder().build();
            switch (element) {
                case -55:
                    result = SearchResult.builder().withPosition(0).withFound(true).build();
                    break;
                case 0:
                    result = SearchResult.builder().withPosition(1).withFound(true).build();
                    break;
                case 33:
                    result = SearchResult.builder().withPosition(2).withFound(true).build();
                    break;
            }
            return result;
        });
        int seq1[] = { -55, 0, 33 };
        int seq2[] = { -55, 0, 33 };
        double res = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(IDENTICAL, res);
    }

}
