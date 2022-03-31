package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.iis.mto.searcher.SearchResult;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

class SimilarityFinderTest {

    @Test
    void emptySequences() {
        int[] seq1 = new int[0];
        int[] seq2 = new int[0];

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        double res = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1, res);
    }

    @Test
    void firstSeqEmpty() {
        int[] seq1 = new int[0];
        int[] seq2 = new int[]{1, 4};

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        double res = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, res);
    }

    @Test
    void secondSeqEmpty() {
        int[] seq1 = new int[]{6, 7};
        int[] seq2 = new int[0];

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> SearchResult.builder().build());
        double res = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, res);
    }

    @Test
    void indenticalSeq() {
        int[] seq1 = new int[]{1, 5, 9, 3};
        int[] seq2 = new int[]{1, 5, 9, 3};

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> {
            SearchResult searchResult = SearchResult.builder().build();
            if (elem == 1) {
                searchResult = SearchResult.builder().withPosition(0).withFound(true).build();
            } else if (elem == 5) {
                searchResult = SearchResult.builder().withPosition(1).withFound(true).build();
            } else if (elem == 9) {
                searchResult = SearchResult.builder().withPosition(2).withFound(true).build();
            } else if (elem == 3) {
                searchResult = SearchResult.builder().withPosition(4).withFound(true).build();
            }
            return searchResult;
        });
        double res = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(1, res);
    }

    @Test
    void differentSeq() {
        int[] seq1 = new int[]{1, 5, 9, 3};
        int[] seq2 = new int[]{8, 4, 8, 7};

        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> {
            SearchResult searchResult = SearchResult.builder().build();
            if (elem == 1) {
                searchResult = SearchResult.builder().withPosition(0).withFound(false).build();
            } else if (elem == 5) {
                searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
            } else if (elem == 9) {
                searchResult = SearchResult.builder().withPosition(2).withFound(false).build();
            } else if (elem == 3) {
                searchResult = SearchResult.builder().withPosition(4).withFound(false).build();
            }
            return searchResult;
        });
        double res = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(0, res);
    }

    @Test
    void halfSeqDifferent(){
        int[] seq1 = new int[]{1, 5, 9, 3};
        int[] seq2 = new int[]{8, 4, 9, 3};
        double numberOfSimilar = 2;
        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> {
            SearchResult searchResult = SearchResult.builder().build();
            if (elem == 1) {
                searchResult = SearchResult.builder().withPosition(0).withFound(false).build();
            } else if (elem == 5) {
                searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
            } else if (elem == 9) {
                searchResult = SearchResult.builder().withPosition(2).withFound(true).build();
            } else if (elem == 3) {
                searchResult = SearchResult.builder().withPosition(4).withFound(true).build();
            }
            return searchResult;
        });
        double res = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        double expected = numberOfSimilar/(seq1.length+seq2.length-numberOfSimilar);
        assertEquals(expected, res);
    }

    @Test
    void numberOfInvokes(){
        int[] seq1 = new int[]{1, 5, 9, 3};
        int[] seq2 = new int[]{8, 4, 9, 3};
        AtomicInteger l= new AtomicInteger();
        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> {
            l.incrementAndGet();
            SearchResult searchResult = SearchResult.builder().build();
            if (elem == 1) {
                searchResult = SearchResult.builder().withPosition(0).withFound(false).build();
            } else if (elem == 5) {
                searchResult = SearchResult.builder().withPosition(1).withFound(false).build();
            } else if (elem == 9) {
                searchResult = SearchResult.builder().withPosition(2).withFound(true).build();
            } else if (elem == 3) {
                searchResult = SearchResult.builder().withPosition(4).withFound(true).build();
            }
            return searchResult;
        });
        similarityFinder.calculateJackardSimilarity(seq1, seq2);
        int res = l.get();
        assertEquals(res, seq1.length);
    }

    @Test
    void zeroInvokes(){
        int[] seq1 = new int[]{};
        int[] seq2 = new int[]{1, 5, 9, 3};
        AtomicInteger l = new AtomicInteger();
        SimilarityFinder similarityFinder = new SimilarityFinder((elem, seq) -> {
            l.incrementAndGet();
            SearchResult searchResult = SearchResult.builder().build();
            return searchResult;
        });
        similarityFinder.calculateJackardSimilarity(seq1, seq2);
        int res = l.get();
        assertEquals(res, seq1.length);
    }
}
