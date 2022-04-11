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
        fail("Not yet implemented");
    }

    @Test
    void arraysAreIdenticals() {
        fail("Not yet implemented");
    }


}
