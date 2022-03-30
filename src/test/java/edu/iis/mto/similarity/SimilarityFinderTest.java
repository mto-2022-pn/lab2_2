package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    @Test
    void emptySequences(){
        int[] seq1 = new int[0];
        int[] seq2 = new int[0];

        SimilarityFinder similarityFinder = new SimilarityFinder((elem,seq)->SearchResult.builder().build());
        double res = similarityFinder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(1,res);
    }

}
