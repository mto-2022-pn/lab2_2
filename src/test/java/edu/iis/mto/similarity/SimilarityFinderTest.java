package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {
// zbiór sklada sie z niepowtarzajacych elementow
    private SimilarityFinder finder;

    @Test
    void testBothSequencesEmpty() {
        finder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        int[] seq1 = {};
        int[] seq2 = {};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, result);
    }

    @Test
    void testFirstSequenceEmpty() {
        finder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        int[] seq1 = {};
        int[] seq2 = {1,2,3};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.0d, result);
    }

    @Test
    void testSecondSequenceEmpty() {
        finder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        int[] seq1 = {};
        int[] seq2 = {1,2,3};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.0d, result);
    }

    @Test
    void testSequencesEquals() {
        finder = new SimilarityFinder((elem, seq) -> {
            SearchResult result;
            switch(elem){
                case 1:
                case 2:
                case 3:
                    result = SearchResult.builder().withFound(true).build();
                    break;
                default:
                    result = SearchResult.builder().withFound(false).build();
            }
            return result;
        });
        int[] seq1 = {1,2,3};
        int[] seq2 = {1,2,3};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1.0d, result);
    }

    @Test
    void testSequencesTotallyDifferent() {
        finder = new SimilarityFinder((elem, seq) -> {
            SearchResult result;
            switch(elem){
                case 1:
                case 3:
                case 5:
                    result = SearchResult.builder().withFound(false).build();
                    break;
                default:
                    result = SearchResult.builder().withFound(true).build();
            }
            return result;
        });
        int[] seq1 = {1,3,5};
        int[] seq2 = {2,4,6,8};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0.0d, result);
    }

    @Test
    void testSequencesWithSomeCommonElements() {
        finder = new SimilarityFinder((elem, seq) -> {
            SearchResult result;
            switch(elem){
                case 1:
                case 5:
                case 7:
                    result = SearchResult.builder().withFound(true).build();
                    break;
                default:
                    result = SearchResult.builder().withFound(false).build();
            }
            return result;
        });
        int[] seq1 = {1,2,3,5,7};
        int[] seq2 = {1,4,5,6,7,8};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(3.0d / (seq1.length + seq2.length - 3), result);
    }

    @Test
    void testFirstSequenceInSecondSequence() {
        finder = new SimilarityFinder((elem, seq) -> {
            SearchResult result;
            switch(elem){
                case 1:
                case 5:
                case 7:
                    result = SearchResult.builder().withFound(true).build();
                    break;
                default:
                    result = SearchResult.builder().withFound(false).build();
            }
            return result;
        });
        int[] seq1 = {1,5,7};
        int[] seq2 = {1,4,5,6,7,8};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(3.0d / (seq1.length + seq2.length - 3), result);
    }

    @Test
    void testSecondSequenceInFirstSequence() {
        finder = new SimilarityFinder((elem, seq) -> {
            SearchResult result;
            switch(elem){
                case 1:
                case 5:
                case 7:
                    result = SearchResult.builder().withFound(true).build();
                    break;
                default:
                    result = SearchResult.builder().withFound(false).build();
            }
            return result;
        });
        int[] seq1 = {1,2,3,5,7};
        int[] seq2 = {1,5,7};
        double result = finder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(3.0d / (seq1.length + seq2.length - 3), result);
    }
}
