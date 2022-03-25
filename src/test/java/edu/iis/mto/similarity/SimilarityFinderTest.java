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
    void SetUpTests() {
        basicSimilarityFinder = new SimilarityFinder((element, sequence) -> SearchResult.builder().build());
    }
    @Test
    void TestBothSequencesEmpty() {
        int[] sequence1 = new int[0];
        int[] sequence2 = new int[0];
        double expected = 1;
        double result = basicSimilarityFinder.calculateJackardSimilarity(sequence1, sequence2);
        assertEquals(expected, result);
    }
    @Test
    void TestFirstSequenceEmpty() {
        int[] sequence1 = new int[0];
        int[] sequence2 = {1, 5, 8};
        double expected = 0;
        double result = basicSimilarityFinder.calculateJackardSimilarity(sequence1, sequence2);
        assertEquals(expected, result);
    }
    @Test
    void TestSecondSequenceEmpty() {
        int[] sequence1 = {-4, -5, 2, 4};
        int[] sequence2 = new int[0];
        double expected = 0;
        double result = basicSimilarityFinder.calculateJackardSimilarity(sequence1, sequence2);
        assertEquals(expected, result);
    }
    @Test
    void TestSequencesHaveNotCommonElements() {
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
    void TestSequencesHaveCommonElements() {
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
    void TestSequencesAreTheSame() {
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
}
