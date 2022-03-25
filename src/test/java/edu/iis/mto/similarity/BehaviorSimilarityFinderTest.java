package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class BehaviorSimilarityFinderTest {

    @Test
    void methodInvokeThreeTimes() throws NoSuchFieldException, IllegalAccessException {
        int[] seq1={0,1,2};
        int[] seq2={1,2,3,4,5,6};
        int expected = 3;
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            int invokeCounter = 0;
            @Override
            public SearchResult search(int elem, int[] sequence) {
                invokeCounter++;
                SearchResult searchResult = null;
                if (elem == 0){
                    searchResult = SearchResult.builder().withPosition(0).withFound(true).build();
                }
                else if (elem == 1){
                    searchResult = SearchResult.builder().withPosition(1).withFound(true).build();
                }
                else if (elem == 2){
                    searchResult = SearchResult.builder().withPosition(2).withFound(true).build();
                }
                return searchResult;
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        similarityFinder.calculateJackardSimilarity(seq1,seq2);
        int invokeCounter = sequenceSearcher.getClass().getDeclaredField("invokeCounter").getInt(sequenceSearcher);
        assertEquals(expected,invokeCounter);
    }
    @Test
    void methodInvokeZeroTimes() throws NoSuchFieldException, IllegalAccessException {
        int[] seq1={};
        int[] seq2={1,2,3,4,5,6};
        int expected = 0;
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            int invokeCounter = 0;
            @Override
            public SearchResult search(int elem, int[] sequence) {
                invokeCounter++;
                return null;
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        similarityFinder.calculateJackardSimilarity(seq1,seq2);
        int invokeCounter = sequenceSearcher.getClass().getDeclaredField("invokeCounter").getInt(sequenceSearcher);
        assertEquals(expected,invokeCounter);
    }
}
