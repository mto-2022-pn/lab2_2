package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    SimilarityFinder similarityFinder;
    @BeforeEach
     void setUp(){
        similarityFinder = new SimilarityFinder((elem, sequence) -> SearchResult.builder().build());
    }

    @Test
    void SentencesAreEmpty() {
        int seq [] = new int[0];
        int seq1 [] = new int[0];
        double result = similarityFinder.calculateJackardSimilarity(seq,seq1);
        assertEquals(1,result);
    }

    @Test
    void OneSentenceIsEmpty() {
        int seq [] = new int[]{1,2,3};
        int seq1 [] = new int[0];
        double result = similarityFinder.calculateJackardSimilarity(seq,seq1);
        assertEquals(0,result);
        result = similarityFinder.calculateJackardSimilarity(seq1,seq);
        assertEquals(0,result);
    }

    @Test
    void sequencesAreTheSame()
    {
        int[] seq = {1, 2, 3, 4};
        int[] seq1 = {1, 2, 3, 4};
        SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence) ->
        {
            SearchResult searchResult = null;
            if (elem==1)
                searchResult = SearchResult.builder().withPosition(0).withFound(true).build();
            if (elem==2)
                searchResult = SearchResult.builder().withPosition(1).withFound(true).build();
            if (elem==3)
                searchResult = SearchResult.builder().withPosition(2).withFound(true).build();
            if (elem==4)
                searchResult = SearchResult.builder().withPosition(3).withFound(true).build();
            return searchResult;
        });
        double result = similarityFinder.calculateJackardSimilarity(seq, seq1);
        assertEquals(1,result);
    }

    @Test
    void sequencesHaveCommonValues()
    {
        int[] seq = {1, 2, 3, 4};
        int[] seq1 = {3, 4, 5, 6,7,8,9,10};
        SimilarityFinder similarityFinder = new SimilarityFinder((elem, sequence) ->
        {
            SearchResult searchResult = null;
            if (elem==1)
                searchResult = SearchResult.builder().withPosition(0).withFound(false).build();
            if (elem==2)
                searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
            if (elem==3)
                searchResult = SearchResult.builder().withPosition(2).withFound(true).build();
            if (elem==4)
                searchResult = SearchResult.builder().withPosition(3).withFound(true).build();
            return searchResult;
        });
        double result = similarityFinder.calculateJackardSimilarity(seq, seq1);
        assertEquals(2.0/(seq.length+seq1.length-2),result);
    }

}
