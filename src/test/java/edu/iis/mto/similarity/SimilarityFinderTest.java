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

    @Test
    void sameThreeElemSequences_shouldReturnOne() {
        int[] seq1 = {11,220,5};
        int[] seq2 = {11,220,5};

        double result = simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(1.0d, result);
    }

    @Test
    void differentThreeElemSequences_shouldReturnZero() {
        int[] seq1 = {1,22,333};
        int[] seq2 = {4444,55555,666666};

        double result = simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(0.0d, result);
    }

    @Test
    void oneElemSameInThreeElemSequences_shouldReturnPointTwo() {
        int[] seq1 = {100,20,300};
        int[] seq2 = {400,100,50};

        // 1 / (3 + 3 - 1) = 0.2
        double result = simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(0.2d, result);
    }

}
