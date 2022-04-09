package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    private SimilarityFinder similarityFinder;

    @BeforeEach
    void InitSimilarityFinder() {
        similarityFinder = new SimilarityFinder((elem, sequence) -> SearchResult.builder().build());
    }

    @Test
    void testEmptySequences() {
        int[] seq1 = {};
        int[] seq2 = {};
        assertEquals(1.0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void testNonEmptySameSequences() {
        int[] seq1 = {1, 2, 3, 4};
        int[] seq2 = {1, 2, 3, 4};
        similarityFinder=new SimilarityFinder((elem, sequence) -> {
           switch (elem){
               case 1:
                   return SearchResult.builder().withFound(true).withPosition(0).build();
               case 2:
                   return SearchResult.builder().withFound(true).withPosition(1).build();
               case 3:
                   return SearchResult.builder().withFound(true).withPosition(2).build();
               case 4:
                   return SearchResult.builder().withFound(true).withPosition(4).build();
           }
           return null;
        });

        assertEquals(1.0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void testEmptyFirstSequences() {
        int[] seq1 = {};
        int[] seq2 = {1, 2, 3, 4};
        assertEquals(0.0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void testEmptySecondSequences() {
        int[] seq1 = {1, 2, 3, 4};
        int[] seq2 = {};
        assertEquals(0.0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

}
