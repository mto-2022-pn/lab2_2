package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    @Test
    void firstSeqEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
        int[] seq1 = {};
        int[] seq2 = {1, 2, 3};
        assertEquals(0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void secondSeqEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {};
        assertEquals(0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void bothSeqsEmpty() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
        int[] seq1 = {};
        int[] seq2 = {};
        assertEquals(1, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void seqsAreTheSame() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            switch (elem) {
                case 1:
                case 2:
                case 3:
                    return SearchResult.builder().withFound(true).build();
                default:
                    return SearchResult.builder().build();
            }
        }));
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {1, 2, 3};
        assertEquals(1, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void seqsAreDifferent() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {4, 5, 6};
        assertEquals(0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void seqsHaveSomeSimilarities() {
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            switch (elem) {
                case 2:
                case 3:
                    return SearchResult.builder().withFound(true).build();
                default:
                    return SearchResult.builder().build();
            }
        }));
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {2, 3, 4};
        assertEquals(2.0/(seq1.length + seq2.length - 2), similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

}
