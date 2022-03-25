package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimilarityFinderBehaviourTest {

    @Test
    void methodInvokesCountTest() throws NoSuchFieldException, IllegalAccessException {

        int[] seq = {1, 2};
        int[] seq1 = {1, 2, 3, 4};

        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            int count = 0;
            @Override
            public SearchResult search(int elem, int[] sequence) {
                SearchResult searchResult = null;
                count++;
                if (elem == 1)
                    searchResult = SearchResult.builder().withPosition(0).withFound(false).build();
                if (elem == 2)
                    searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
                return searchResult;
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        similarityFinder.calculateJackardSimilarity(seq,seq1);
        Field check = sequenceSearcher.getClass().getDeclaredField("count");
        int checkInt = check.getInt(sequenceSearcher);
        assertEquals(2,checkInt);
    }

    @Test
    void methodInvokesCountTestWithEmptyArray() throws NoSuchFieldException, IllegalAccessException {

        int[] seq = {};
        int[] seq1 = {1, 2, 3, 4};

        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            int count = 0;
            @Override
            public SearchResult search(int elem, int[] sequence) {
                SearchResult searchResult = null;
                count++;
                searchResult = SearchResult.builder().build();
                return searchResult;
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        similarityFinder.calculateJackardSimilarity(seq,seq1);
        Field check = sequenceSearcher.getClass().getDeclaredField("count");
        int checkInt = check.getInt(sequenceSearcher);
        assertEquals(0,checkInt);
    }
}
