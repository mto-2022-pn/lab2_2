package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
    private SimilarityFinder basicSimilarityFinder;
    @BeforeEach
    void setUpTests() {
        basicSimilarityFinder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
    }
    @Test
    void testBothSequencesEmpty() {
        int[] sequence1 = new int[0];
        int[] sequence2 = new int[0];
        double expected = 1;
        double result = basicSimilarityFinder.calculateJackardSimilarity(sequence1, sequence2);
        assertEquals(expected, result);
    }
    @Test
    void testFirstSequenceEmpty() {
        int[] sequence1 = new int[0];
        int[] sequence2 = {1, 5, 8};
        double expected = 0;
        double result = basicSimilarityFinder.calculateJackardSimilarity(sequence1, sequence2);
        assertEquals(expected, result);
    }
    @Test
    void testSecondSequenceEmpty() {
        int[] sequence1 = {-4, -5, 2, 4};
        int[] sequence2 = new int[0];
        double expected = 0;
        double result = basicSimilarityFinder.calculateJackardSimilarity(sequence1, sequence2);
        assertEquals(expected, result);
    }
    @Test
    void testSequencesHaveNotCommonElements() {
        int[] sequence1 = {-4, -5, 2};
        int[] sequence2 = {24, 21, -14, 2, 33, 34};
        double expected = 0;
        SimilarityFinder similarityFinder = new SimilarityFinder((element, sequence) -> {
            switch (element) {
                case -4:
                    return SearchResult.builder().withPosition(0).withFound(false).build();
                case -5:
                    return SearchResult.builder().withPosition(1).withFound(false).build();
                case 2:
                    return SearchResult.builder().withPosition(2).withFound(false).build();
                default:
                    return null;
            }
        });
        double result = similarityFinder.calculateJackardSimilarity(sequence1, sequence2);
        assertEquals(expected, result);
    }
    @Test
    void testSequencesHaveCommonElements() {
        int[] sequence1 = {-14, -35, 22, 2, 0};
        int[] sequence2 = {4, 1, -14, 2, 22, 34};
        int commonElements = 3;
        double expected = (double) commonElements / (sequence1.length + sequence2.length - commonElements);
        SimilarityFinder similarityFinder = new SimilarityFinder((element, sequence) -> {
            switch (element) {
                case -14:
                    return SearchResult.builder().withPosition(0).withFound(true).build();
                case -35:
                    return SearchResult.builder().withPosition(1).withFound(false).build();
                case 22:
                    return SearchResult.builder().withPosition(2).withFound(true).build();
                case 2:
                    return SearchResult.builder().withPosition(3).withFound(true).build();
                case 0:
                    return SearchResult.builder().withPosition(4).withFound(false).build();
                default:
                    return null;
            }
        });
        double result = similarityFinder.calculateJackardSimilarity(sequence1, sequence2);
        assertEquals(expected, result);
    }
    @Test
    void testSequencesAreTheSame() {
        int[] sequence1 = {-1, -3, 2, 4, 0};
        int[] sequence2 = {-1, -3, 2, 4, 0};
        double expected = 1;
        SimilarityFinder similarityFinder = new SimilarityFinder((element, sequence) -> {
            switch (element) {
                case -1:
                    return SearchResult.builder().withPosition(0).withFound(true).build();
                case -3:
                    return SearchResult.builder().withPosition(1).withFound(true).build();
                case 2:
                    return SearchResult.builder().withPosition(2).withFound(true).build();
                case 4:
                    return SearchResult.builder().withPosition(3).withFound(true).build();
                case 0:
                    return SearchResult.builder().withPosition(4).withFound(true).build();
                default:
                    return null;
            }
        });
        double result = similarityFinder.calculateJackardSimilarity(sequence1, sequence2);
        assertEquals(expected, result);
    }
    @Test
    void testSearchMethodInvokedFourTimes() {
        int[] sequence1 = {-1, -13, -4, 10};
        int[] sequence2 = {-1, -3, 2, 4, 0};
        int expected = 4;
        final int[] invocationCounter = { 0 };
        SimilarityFinder similarityFinder = new SimilarityFinder((element, sequence) -> {
            invocationCounter[0]++;
            switch (element) {
                case -1:
                    return SearchResult.builder().withPosition(0).withFound(true).build();
                case -13:
                    return SearchResult.builder().withPosition(1).withFound(true).build();
                case -4:
                    return SearchResult.builder().withPosition(3).withFound(true).build();
                case 10:
                    return SearchResult.builder().withPosition(4).withFound(true).build();
                default:
                    return null;
            }
        });
        similarityFinder.calculateJackardSimilarity(sequence1, sequence2);
        assertEquals(expected, invocationCounter[0]);
    }
    @Test
    void testSearchMethodNeverInvoked() {
        int[] sequence1 = new int[0];
        int[] sequence2 = {-1, 3, 22, -34, 30};
        int expected = 0;
        final int[] invocationCounter = { 0 };
        SimilarityFinder similarityFinder = new SimilarityFinder((element, sequence) -> {
            invocationCounter[0]++;
            return null;
        });
        similarityFinder.calculateJackardSimilarity(sequence1, sequence2);
        assertEquals(expected, invocationCounter[0]);
    }
}
