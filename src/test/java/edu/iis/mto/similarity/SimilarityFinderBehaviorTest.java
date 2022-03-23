package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SimilarityFinderBehaviorTest {
    @Test
    void methodShouldInvokeSixTimes() throws IllegalAccessException, NoSuchFieldException {
        int[] sequence = {13, 12, 11, 99, 201, -12};
        int[] secondSequence = {1, 2, 3, 4, 5, 6, 7};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            int counter =   0;
            @Override
            public SearchResult search(int elem, int[] sequence) {
                SearchResult searchResult = null;
                counter++;
                if (elem == 13)
                    searchResult = SearchResult.builder().withPosition(0).withFound(false).build();
                if (elem == 12)
                    searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
                if (elem == 11)
                    searchResult = SearchResult.builder().withPosition(2).withFound(false).build();
                if (elem == 99)
                    searchResult = SearchResult.builder().withPosition(3).withFound(false).build();
                if (elem == 201)
                    searchResult = SearchResult.builder().withPosition(4).withFound(false).build();
                if (elem == -12)
                    searchResult = SearchResult.builder().withPosition(5).withFound(false).build();
                return searchResult;
            }
        };
        SimilarityFinder similarityFinder = new SimilarityFinder(sequenceSearcher);
        similarityFinder.calculateJackardSimilarity(sequence,secondSequence);

        Field field = sequenceSearcher.getClass().getDeclaredField("counter");
        int amountOfSearches = field.getInt(sequenceSearcher);
        assertEquals(6,amountOfSearches);
    }

}
