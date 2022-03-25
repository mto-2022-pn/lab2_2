package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    @Test
    void SentencesAreEmpty() {
        int seq [] = new int[0];
        int seq1 [] = new int[0];
        SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence) -> SearchResult.builder().build());
        double result = similarityFinder.calculateJackardSimilarity(seq,seq1);
        assertEquals(1,result);
    }

}
