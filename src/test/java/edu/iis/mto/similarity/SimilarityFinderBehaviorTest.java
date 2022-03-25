package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimilarityFinderBehaviorTest {
    @Test
    void TestMethodInvokedFourTimes() {
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
    void TestMethodInvokedZeroTimes() {
        int[] sequence1 = new int[0];
        int[] sequence2 = {-1, -3, 2, 4, 0};
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
