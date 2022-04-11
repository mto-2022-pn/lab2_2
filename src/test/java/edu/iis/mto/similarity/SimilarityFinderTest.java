package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testMethodShouldBeInvoke0Times() {
        class SequenceSearcherMock implements SequenceSearcher{
            private int invokeCounter = 0;

            @Override
            public SearchResult search(int elem, int[] sequence) {
                invokeCounter++;
                return SearchResult.builder().build();
            }
        }

        SequenceSearcherMock searcher = new SequenceSearcherMock();

        finder = new SimilarityFinder(searcher);
        int[] seq1 = {};
        int[] seq2 = {};
        double result = finder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(0, searcher.invokeCounter);
        assertEquals(1.0d, result);
    }

    @Test
    void testMethodShouldBeInvoke5Times() {
        class SequenceSearcherMock implements SequenceSearcher{
            private int invokeCounter = 0;

            @Override
            public SearchResult search(int elem, int[] sequence) {
                invokeCounter++;
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
            }
        }

        SequenceSearcherMock searcher = new SequenceSearcherMock();

        finder = new SimilarityFinder(searcher);
        int[] seq1 = {1,2,3,5,7};
        int[] seq2 = {1,5,7,9,11};
        double result = finder.calculateJackardSimilarity(seq1, seq2);

        assertEquals(5, searcher.invokeCounter);
        assertEquals(3.0d / (seq1.length + seq2.length - 3), result);
    }

    @Test
    void testMethodCheckingAllCommonElements() {
        class SequenceSearcherMock implements SequenceSearcher{
            private final List<Integer> elemList = new ArrayList<>();
//            private final List<int[]> sequenceList = new ArrayList<>();

            @Override
            public SearchResult search(int elem, int[] sequence) {
                elemList.add(elem);
//                sequenceList.add(sequence);
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
            }
        }

        SequenceSearcherMock searcher = new SequenceSearcherMock();

        finder = new SimilarityFinder(searcher);
        int[] seq1 = {1,2,3,5,7};
        int[] seq2 = {1,5,7};
        double result = finder.calculateJackardSimilarity(seq1, seq2);

        for (int e:seq2) {
            assertTrue(searcher.elemList.contains(e));
        }

        assertEquals(3.0d / (seq1.length + seq2.length - 3), result);
    }
}
