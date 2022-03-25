package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    SimilarityFinder similarityFinder;
    @BeforeEach
     void setUp(){
        similarityFinder = new SimilarityFinder((elem, sequence) -> SearchResult.builder().build());
    }

    @Test
    void SentencesAreEmpty() {
        int seq [] = new int[0];
        int seq1 [] = new int[0];
        double result = similarityFinder.calculateJackardSimilarity(seq,seq1);
        assertEquals(1,result);
    }

    @Test
    void OneSentenceIsEmpty() {
        int seq [] = new int[]{1,2,3};
        int seq1 [] = new int[0];
        double result = similarityFinder.calculateJackardSimilarity(seq,seq1);
        assertEquals(0,result);
        result = similarityFinder.calculateJackardSimilarity(seq1,seq);
        assertEquals(0,result);
    }

}
