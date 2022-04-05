package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import java.util.Arrays;

class SimilarityFinderTest {

    SimilarityFinder similarityFinder;

    @BeforeEach
    void setUp()
    {
        similarityFinder = new SimilarityFinder((elem, sequence) -> 
        {
            boolean check = Arrays.binarySearch(sequence, elem) >= 0;
            return SearchResult.builder().withPosition(elem).withFound(check).build();
        }
        );
    }

    @Test
    void test01()
    {
        int seq1[] = {};
        int seq2[] = {};
        assertEquals(1, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }    

    @Test
    void test02()
    {
        int seq1[] = {1, 2, 3, 4, 5};
        int seq2[] = {};
        assertEquals(0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }    

    @Test
    void test03()
    {
        int seq1[] = {};
        int seq2[] = {2, 3, 4, 5, 6};
        assertEquals(0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void test04()
    {
        int seq1[] = {1};
        int seq2[] = {1};
        assertEquals(1, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void test05()
    {
        int seq1[] = {1};
        int seq2[] = {2};
        assertEquals(0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void test06()
    {
        int seq1[] = {1, 2, 3, 4, 5};
        int seq2[] = {1};
        assertEquals((double)1/5, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void test07()
    {
        int seq1[] = {1, 2, 3, 4, 5};
        int seq2[] = {2, 3, 4, 5, 6};
        assertEquals((double)4/6, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }
    
    @Test
    void test08()
    {
        int seq1[] = {1, 2, 3, 4, 5};
        int seq2[] = {3};
        assertEquals((double)1/5, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }

    @Test
    void test09()
    {
        int seq1[] = {1, 2, 3, 4, 5};
        int seq2[] = {6};
        assertEquals(0, similarityFinder.calculateJackardSimilarity(seq1, seq2));
    }
    // TODO ej jakie wartości powinny być zwracane?
    
    // @Test
    // void test69()
    // {

    //     // SimilarityFinder similarityFinder = new SimilarityFinder(new SequenceSearcher() {

    //     //     @Override
    //     //     public SearchResult search(int elem, int[] sequence) {
    //     //         // SearchResult sr = new SearchResult(null);
    //     //         SearchResult sr = SearchResult.builder().build();
    //     //         int i = 0;
    //     //         for (int s: sequence)
    //     //         {
    //     //             if (elem == s):
    //     //                 sr.found = true;
    //     //                 sr.position = i;
    //     //                 break;
    //     //             i++;
    //     //         }
    //     //         return sr;
                
    //     //         return null;
    //     //     }
            
    //     // });
    //     int seq1[] = {1, 2, 3, 4};
    //     int seq2[] = {2, 3, 4, 5};
    //     // int[] seq1 = {};
    //     // int[] seq2 = {1, 2, 3};
    //     // assertEquals(1, similarityFinder.calculateJackardSimilarity(seq1, seq2));
        
    // }

}
