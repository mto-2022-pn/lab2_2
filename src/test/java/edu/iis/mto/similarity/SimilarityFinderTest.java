package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    @Test
    void bothSequencesAreEmpty() {
        int[] sequence = {};
        int[] secondSequence = {};
        SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence1) -> SearchResult.builder().build());

        double result = similarityFinder.calculateJackardSimilarity(sequence,secondSequence);
        assertEquals(1,result);
    }

    @Test
    void oneSequenceIsEmpty(){
        int[] sequence = {1, 21, 32, 46, 58};
        int[] secondSequence = {};
        SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence1) ->
        {
            SearchResult searchResult = null;
            if(elem==1)
            {
                searchResult = SearchResult.builder().withPosition(0).withFound(false).build();
            }
            if (elem==21)
            {
                searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
            }
            if (elem==32)
            {
                searchResult = SearchResult.builder().withPosition(2).withFound(false).build();
            }
            if (elem==46)
            {
                searchResult = SearchResult.builder().withPosition(3).withFound(false).build();
            }
            if (elem==58)
            {
                searchResult = SearchResult.builder().withPosition(4).withFound(false).build();
            }
            return searchResult;
        });
        double result = similarityFinder.calculateJackardSimilarity(sequence, secondSequence);
        assertEquals(0,result);
    }

}
