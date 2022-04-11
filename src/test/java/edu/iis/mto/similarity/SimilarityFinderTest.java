package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    private static double SAME_SEQUENCES = 1.0d;
    private static double DIFFERENT_SEQUENCES = 0d;

    @Test void testSimilarityWithBothEmptySequences() {
        SimilarityFinder finder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
        int[] firstSeq = {};
        int[] secondSeq = {};
        double similarity = finder.calculateJackardSimilarity(firstSeq, secondSeq);
        assertEquals(SAME_SEQUENCES, similarity);
    }

    @Test void testSimilarityWithFirstEmptySequence() {
        SimilarityFinder finder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
        int[] firstSeq = {};
        int[] secondSeq = {2, 6, 7, 23};
        double similarity = finder.calculateJackardSimilarity(firstSeq, secondSeq);
        assertEquals(DIFFERENT_SEQUENCES, similarity);
    }

    @Test void testSimilarityWithSecondEmptySequence() {
        SimilarityFinder finder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
        int[] firstSeq = {2, 6, 7, 23};
        int[] secondSeq = {};
        double similarity = finder.calculateJackardSimilarity(firstSeq, secondSeq);
        assertEquals(DIFFERENT_SEQUENCES, similarity);
    }

    @Test void testSimilarityWithAllSameElementsInSequences() {
        SimilarityFinder finder = new SimilarityFinder(((elem, sequence) -> {
            SearchResult result;
            switch (elem) {
                case 2:
                    result = SearchResult.builder().withPosition(0).withFound(true).build();
                    break;
                case 6:
                    result = SearchResult.builder().withPosition(1).withFound(true).build();
                    break;
                case 7:
                    result = SearchResult.builder().withPosition(2).withFound(true).build();
                    break;
                case 23:
                    result = SearchResult.builder().withPosition(3).withFound(true).build();
                    break;
                default:
                    result = SearchResult.builder().build();
            }
            return result;
        }));
        int[] firstSeq = {2, 6, 7, 23};
        int[] secondSeq = {2, 6, 7, 23};
        double similarity = finder.calculateJackardSimilarity(firstSeq, secondSeq);
        assertEquals(SAME_SEQUENCES, similarity);
    }

    @Test void testSimilarityWithFewSameElementsInSequences() {
        SimilarityFinder finder = new SimilarityFinder(((elem, sequence) -> {
            SearchResult result;
            switch (elem) {
                case 2:
                    result = SearchResult.builder().withPosition(0).withFound(true).build();
                    break;
                case 6:
                    result = SearchResult.builder().withPosition(1).withFound(true).build();
                    break;
                case 7:
                    result = SearchResult.builder().withPosition(2).withFound(true).build();
                    break;
                case 23:
                    result = SearchResult.builder().withPosition(3).withFound(true).build();
                    break;
                default:
                    result = SearchResult.builder().build();
            }
            return result;
        }));
        int[] firstSeq = {2, 6, 7, 23};
        int[] secondSeq = {2, 6, 7, 23, 29, 33};
        double numberOfSameElements = 4d;
        double similarity = finder.calculateJackardSimilarity(firstSeq, secondSeq);
        assertEquals(numberOfSameElements / (firstSeq.length + secondSeq.length - numberOfSameElements), similarity);
    }

    @Test void testSimilarityWithNoSameElementsInSequences() {
        SimilarityFinder finder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
        int[] first_seq = {3, 5, 8, 25};
        int[] second_seq = {2, 6, 7, 23, 29, 33};
        double similarity = finder.calculateJackardSimilarity(first_seq, second_seq);
        assertEquals(DIFFERENT_SEQUENCES, similarity);
    }

}
