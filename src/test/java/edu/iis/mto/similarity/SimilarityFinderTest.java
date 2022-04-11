package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    @Test
    void bothArrayEmpty() {
        int[] seq1 = {};
        int[] seq2 = {};
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
        assertEquals(1.0d, finder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void firstArrayEmpty() {
        int[] seq1 = {};
        int[] seq2 = {1, 2, 3, 4};
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
        assertEquals(0.0d, finder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void secondArrayEmpty() {
        int[] seq1 = {1, 2, 3, 4};
        int[] seq2 = {};
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
        assertEquals(0.0d, finder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void arraysDontHaveAnyElementsInCommon() {
        int[] seq1 = {1, 2, 3, 4};
        int[] seq2 = {5, 6, 7, 8};
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
        assertEquals(0.0d, finder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void arraysHaveSomeCommonElements() {
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> {
            SearchResult result = SearchResult.builder().build();
            switch (element) {
                case 1:
                    result = SearchResult.builder().withPosition(0).withFound(true).build();
                    break;
                case 2:
                    result = SearchResult.builder().withPosition(1).withFound(false).build();
                    break;
                case 3:
                    result = SearchResult.builder().withPosition(2).withFound(true).build();
                    break;
                case 4:
                    result = SearchResult.builder().withPosition(3).withFound(true).build();
                    break;
                case 5:
                    result = SearchResult.builder().withPosition(4).withFound(true).build();
                    break;
                case 6:
                    result = SearchResult.builder().withPosition(5).withFound(false).build();
                    break;
            }
            return result;
        });
        int[] seq1 = {1, 2, 3, 4, 5, 6};
        int[] seq2 = {1, 4, 3, 9, 5, 7};
        assertEquals(0.5d, finder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void arraysAreIdentical() {
        SimilarityFinder finder = new SimilarityFinder((element, sequence) -> {
            SearchResult result = SearchResult.builder().build();
            switch (element) {
                case 1:
                    result = SearchResult.builder().withPosition(0).withFound(true).build();
                    break;
                case 2:
                    result = SearchResult.builder().withPosition(1).withFound(true).build();
                    break;
                case 3:
                    result = SearchResult.builder().withPosition(2).withFound(true).build();
                    break;
            }
            return result;
        });
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {1, 2, 3};
        assertEquals(1.0d, finder.calculateJackardSimilarity(seq1, seq2));
    }


}
