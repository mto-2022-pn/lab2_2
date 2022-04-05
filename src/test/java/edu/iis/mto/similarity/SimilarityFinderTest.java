package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    SimilarityFinder simFinder;

    @BeforeEach
    void setup() {
        simFinder = new SimilarityFinder(new FakeSequenceSearcher());
    }

    @Test
    void zeroElemSequences_shouldReturnOne() {
        int[] seq1 = new int[0];
        int[] seq2 = new int[0];

        double result = simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(1.0d, result);
    }

    @Test
    void sameOneElemSequences_shouldReturnOne() {
        int[] seq1 = {1};
        int[] seq2 = {1};

        //1 / (1+1-1)
        double result = simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(1.0d, result);
    }

    @Test
    void differentOneElemSequences_shouldReturnZero() {
        int[] seq1 = {1};
        int[] seq2 = {0};

        double result = simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(0.0d, result);
    }

}
