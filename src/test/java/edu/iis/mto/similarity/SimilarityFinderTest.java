package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    @Test
    void bothSequencesAreEmpty() {
        int[] sequence = {};
        int[] secondSequence = {};
        SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence1) -> SearchResult.builder().build());

        double result = similarityFinder.calculateJackardSimilarity(sequence,secondSequence);
        assertEquals(1,result);
    }
}
