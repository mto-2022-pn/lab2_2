package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

class SimilarityFinderTest {

    private static final double DIFFERENT = 0.0d;
    private static final double IDENTITY = 1.0d;

    private SimilarityFinder similarityFinder;
    private Random random = new Random();

    @BeforeEach
    void SetUpTest() {
        similarityFinder = new SimilarityFinder(
                (element, sequence) -> SearchResult.builder().build());
    }

    @Test
    void BothSeqEmpty() {
        int[] seq1 = {};
        int[] seq2 = {};
        double expected = IDENTITY;
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(expected, result);
    }

    @Test
    void FirstSeqEmpty() {
        int[] seq1 = {};
        int[] seq2 = random.ints(random.nextInt(9999) + 1).toArray();
        double expected = DIFFERENT;
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(expected, result);
    }
}
