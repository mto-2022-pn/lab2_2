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
        fail("Not yet implemented");
    }

    @Test
    void secondArrayEmpty() {
        fail("Not yet implemented");
    }

    @Test
    void arraysDontHaveAnyElementsInCommon() {
        fail("Not yet implemented");
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
