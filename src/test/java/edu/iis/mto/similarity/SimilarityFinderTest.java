package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Test
    void SecondSeqEmpty() {
        int[] seq1 = random.ints(random.nextInt(9999) + 1).toArray();
        int[] seq2 = {};
        double expected = DIFFERENT;
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(expected, result);
    }

    @Test
    void NotHaveCommonElements() {
        int[] seq1 = {1,5,3};
        int[] seq2 = {6,8,4,2,9,7};
        double expected = DIFFERENT;
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            switch (elem) {
                case 1:
                    return SearchResult.builder().withPosition(0).withFound(false).build();
                case 5:
                    return SearchResult.builder().withPosition(1).withFound(false).build();
                case 3:
                    return SearchResult.builder().withPosition(2).withFound(false).build();
                default:
                    return null;
            }
        }));
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(expected, result);
    }

    @Test
    void HaveCommonElements() {
        int[] seq1 = {1,5,3,2};
        int[] seq2 = {6,8,4,2,9,7};
        int sameElem = 1;
        double expected = (double) sameElem / (seq1.length + seq2.length - sameElem);
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            switch (elem) {
                case 1:
                    return SearchResult.builder().withPosition(0).withFound(false).build();
                case 5:
                    return SearchResult.builder().withPosition(1).withFound(false).build();
                case 3:
                    return SearchResult.builder().withPosition(2).withFound(false).build();
                case 2:
                    return SearchResult.builder().withPosition(3).withFound(true).build();
                default:
                    return null;
            }
        }));
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(expected, result);
    }

    @Test
    void IdentityElements() {
        int[] seq1 = {6,8,4,2};
        int[] seq2 = {6,8,4,2};
        double expected = IDENTITY;
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            switch (elem) {
                case 6:
                    return SearchResult.builder().withPosition(0).withFound(true).build();
                case 8:
                    return SearchResult.builder().withPosition(1).withFound(true).build();
                case 4:
                    return SearchResult.builder().withPosition(2).withFound(true).build();
                case 2:
                    return SearchResult.builder().withPosition(3).withFound(true).build();
                default:
                    return null;
            }
        }));
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(expected, result);
    }

    @Test
    void Invoke3Times() {
        int[] seq1 = {1,5,3};
        int[] seq2 = {6,8,4,2,9,7};
        double expected = 3;
        AtomicInteger counter = new AtomicInteger();
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            counter.getAndIncrement();
            switch (elem) {
                case 1:
                    return SearchResult.builder().withPosition(0).withFound(false).build();
                case 5:
                    return SearchResult.builder().withPosition(1).withFound(false).build();
                case 3:
                    return SearchResult.builder().withPosition(2).withFound(false).build();
                default:
                    return null;
            }
        }));
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(expected,counter.get());
    }

    @Test
    void NoInvoke() {
        int[] seq1 = {};
        int[] seq2 = {6,8,4,2,9,7};
        double expected = 0;
        AtomicInteger counter = new AtomicInteger();
        SimilarityFinder similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            counter.getAndIncrement();
            return null;
        }));
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(expected,counter.get());
    }
}
