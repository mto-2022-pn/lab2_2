package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

class SimilarityFinderTest {

    @Test
    void bothSequencesAreEmpty() {
        int [] seq = new int[0];
        int [] seq2 = new int[0];
        SimilarityFinder sf = new SimilarityFinder((elem, sequence) -> SearchResult.builder().build());
        assertEquals(1.0,sf.calculateJackardSimilarity(seq,seq2));
    }

    @Test
    void firstSequenceIsEmptyCausingResultEqualsZero() {
        int [] seq = new int[0];
        int [] seq2 = new int[]{1,2,3};
        SimilarityFinder sf = new SimilarityFinder((elem, sequence) -> SearchResult.builder().build());
        assertEquals(0.0,sf.calculateJackardSimilarity(seq,seq2));
    }

    @Test
    void secondSequenceIsEmptyCausingResultEqualsZero() {
        int [] seq = new int[]{1,2,3};
        int [] seq2 = new int[0];
        SimilarityFinder sf = new SimilarityFinder((elem, sequence) -> SearchResult.builder().build());
        assertEquals(0.0,sf.calculateJackardSimilarity(seq,seq2));
    }

    @Test
    void sequencesAreTheSame() {
        int [] seq = new int[]{1,2,3};
        int [] seq2 = new int[]{1,2,3};
        SimilarityFinder sf = new SimilarityFinder((elem, sequence) ->
        {
            switch (elem){
                case 1: return SearchResult.builder().withPosition(0).withFound(true).build();
                case 2: return SearchResult.builder().withPosition(1).withFound(true).build();
                case 3: return SearchResult.builder().withPosition(2).withFound(true).build();
                default: return null;
            }
        });
        assertEquals(1.0,sf.calculateJackardSimilarity(seq,seq2));
    }
    @Test
    void sequencesAreDifferent() {
        int [] seq = new int[]{1,2,3};
        int [] seq2 = new int[]{4,5,6,9,7};
        SimilarityFinder sf = new SimilarityFinder((elem, sequence) ->
        {
            switch (elem){
                case 1: return SearchResult.builder().withPosition(0).withFound(false).build();
                case 2: return SearchResult.builder().withPosition(1).withFound(false).build();
                case 3: return SearchResult.builder().withPosition(2).withFound(false).build();
                default: return null;
            }
        });
        assertEquals(0.0,sf.calculateJackardSimilarity(seq,seq2));
    }

    @Test
    void sequencesHaveSomeCommonElements() {
        int [] seq = new int[]{1,2,3};
        int [] seq2 = new int[]{1,2,6,9,7};
        int common = 2;
        SimilarityFinder sf = new SimilarityFinder((elem, sequence) ->
        {
            switch (elem){
                case 1: return SearchResult.builder().withPosition(0).withFound(true).build();
                case 2: return SearchResult.builder().withPosition(1).withFound(true).build();
                case 3: return SearchResult.builder().withPosition(2).withFound(false).build();
                default: return null;
            }
        });
        double expected = (double) common/ (seq.length + seq2.length - common);
        sf.calculateJackardSimilarity(seq,seq2);
        assertEquals(expected,sf.calculateJackardSimilarity(seq,seq2));
    }
    @Test
    void invokesSearchingZeroTimes(){
        int [] seq = new int[0];
        int [] seq2 = new int[]{1,2,6,9,7};
        int[] invokeCounter = new int[]{0};
        SimilarityFinder sf = new SimilarityFinder((elem, sequence) ->{
            invokeCounter[0]++;
            return SearchResult.builder().build();
        });
        assertEquals(seq.length,invokeCounter[0]);
    }

    @Test
    void invokesSearchingTwoTimes(){
        int [] seq = new int[]{1,2};
        int [] seq2 = new int[]{1,2,6,9,7};
        int[] invokeCounter = new int[]{0};
        SimilarityFinder sf = new SimilarityFinder((elem, sequence) ->{
            invokeCounter[0]++;
            return SearchResult.builder().build();
        });
        sf.calculateJackardSimilarity(seq,seq2);
        assertEquals(seq.length,invokeCounter[0]);
    }
}
