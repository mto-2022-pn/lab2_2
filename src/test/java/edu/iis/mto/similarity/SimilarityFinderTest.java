package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.*;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    SimilarityFinder similarityFinder;
    @BeforeEach
    void initSimilarityFinder(){
        similarityFinder = new SimilarityFinder(((elem, sequence) -> SearchResult.builder().build()));
    }
    @Test
    void twoSequencesAreEmpty() {
        int[] seq1={};
        int[] seq2={};
        double expected=1;
        double result=similarityFinder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(expected,result);
    }
    @Test
    void oneSequencesIsEmpty() {
        int[] seq1={1,3,5,7,9};
        int[] seq2={};
        double expected=0;
        double result=similarityFinder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(expected,result);
        result=similarityFinder.calculateJackardSimilarity(seq2,seq1);
        assertEquals(expected,result);
    }
    @Test
    void twoSequencesAreTheSame() {
        int[] seq1={1,3,5};
        int[] seq2={1,3,5};
        double expected=1;
        similarityFinder = new SimilarityFinder((elem, sequence) -> {
            SearchResult searchResult=null;
            if(elem == 1){
                searchResult = SearchResult.builder().withPosition(0).withFound(true).build();
            }
            else if(elem == 3){
                searchResult = SearchResult.builder().withPosition(1).withFound(true).build();
            }
            else if(elem == 5){
                searchResult = SearchResult.builder().withPosition(2).withFound(true).build();
            }
            return searchResult;
        });
        double result=similarityFinder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(expected,result);
    }
    @Test
    void twoSequencesHaveCommonElements() {
        int[] seq1={0,1,2};
        int[] seq2={1,2,3,4,5,6};
        double expected=2.0/(seq1.length+seq2.length-2.0);
        similarityFinder = new SimilarityFinder((elem, sequence) -> {
            SearchResult searchResult=null;
            if(elem == 0){
                searchResult = SearchResult.builder().withPosition(0).withFound(false).build();
            }
            if(elem == 1){
                searchResult = SearchResult.builder().withPosition(1).withFound(true).build();
            }
            else if(elem == 2){
                searchResult = SearchResult.builder().withPosition(2).withFound(true).build();
            }
            return searchResult;
        });
        double result=similarityFinder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(expected,result);
    }
    @Test
    void twoSequencesHaveNoCommonElements() {
        int[] seq1={7,8,9};
        int[] seq2={1,2,3,4,5,6};
        double expected=0;
        similarityFinder = new SimilarityFinder((elem, sequence) -> {
            SearchResult searchResult=null;
            if(elem == 7){
                searchResult = SearchResult.builder().withPosition(0).withFound(false).build();
            }
            if(elem == 8){
                searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
            }
            else if(elem == 9){
                searchResult = SearchResult.builder().withPosition(2).withFound(false).build();
            }
            return searchResult;
        });
        double result=similarityFinder.calculateJackardSimilarity(seq1,seq2);
        assertEquals(expected,result);
    }
    @Test
    void searchMethodShouldInvokeThreeTimes() throws NoSuchFieldException, IllegalAccessException {
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
    void searchMethodShouldInvokeZeroTimes() throws NoSuchFieldException, IllegalAccessException {
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
