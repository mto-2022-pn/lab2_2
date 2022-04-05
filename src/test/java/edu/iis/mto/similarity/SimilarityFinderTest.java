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

    @Test
    void twoSameElementsInFourElemSequences_shouldReturnOneThird() {
        int[] seq1 = {111,2,30,44};
        int[] seq2 = {111,2,55,6};

        // 2 / (4 + 4 - 2) = 0.3333...
        double result = simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(1.0d / 3.0d, result);
    }

    @Test
    void differentSizeSequencesShorterSeqFirst_shouldReturnTwoThirds() {
        int[] seq1 = {30,440,51,67777};
        int[] seq2 = {1,11,30,440,51,67777};

        // 4 / (4 + 6 - 4) = 0.66...
        double result = simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(4.0d / 6.0d, result);
    }

    @Test
    void differentSizeSequencesLongerSeqFirst_shouldReturnTwoThirds() {
        int[] seq1 = {10,2,33,4000,15,600};
        int[] seq2 = {10,2,33,4000};

        // 4 / (6 + 4 - 4) = 0.66...
        double result = simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(4.0d / 6.0d, result);
    }

    @Test
    void zeroElemSequences_searchMethodShouldNotBeInvoked() {
        int[] seq1 = new int[0];
        int[] seq2 = new int[0];

        MockSequenceSearcher mock = new MockSequenceSearcher();
        simFinder = new SimilarityFinder(mock);
        simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(0, mock.getSearchInvokeCounter());
    }

    @Test
    void oneElemSequences_searchShouldBeInvokedOnce() {
        int[] seq1 = {123};
        int[] seq2 = {205};

        MockSequenceSearcher mock = new MockSequenceSearcher();
        simFinder = new SimilarityFinder(mock);
        simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(1, mock.getSearchInvokeCounter());
    }

    @Test
    void fiveAndEightElemSequences_searchShouldBeInvokedSixTimes() {
        int[] seq1 = {123, 1, 99, 1245, 5652};
        int[] seq2 = {205, 146, 532, 556, 1562, 12, 146, 77};

        MockSequenceSearcher mock = new MockSequenceSearcher();
        simFinder = new SimilarityFinder(mock);
        simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(5, mock.getSearchInvokeCounter());
    }

    @Test
    void sevenAndFourElemSequences_searchShouldBeInvokedEightTimes() {
        int[] seq1 = {221, 142, 2, 56, 12, 1, 14};
        int[] seq2 = {123, 1, 99, 2, 22};

        MockSequenceSearcher mock = new MockSequenceSearcher();
        simFinder = new SimilarityFinder(mock);
        simFinder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(7, mock.getSearchInvokeCounter());
    }
}
