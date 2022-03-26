package edu.iis.mto.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.searcher.SearchResult;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class SimilarityFinderTest {

    private static final double DIFFERENT = 0.0d;
    private static final double IDENTITY = 1.0d;
    private final Random random = new Random();

    private SimilarityFinder similarityFinder;

    //Each of the three arrays contains different values.
    private int[] a;
    private int[] b;
    private int[] c;

    @BeforeEach
    void SetUpTest() {
        similarityFinder = new SimilarityFinder(
                (element, sequence) -> SearchResult.builder().build());

        HashSet<Integer> hashSet = new HashSet<>();
        int size = random.nextInt(9999) + 9;

        while (hashSet.size() < size)
            hashSet.add(random.nextInt());

        int aLimit = random.nextInt(size * 2 / 3);
        int bLimit = random.nextInt( (size - aLimit) * 2 / 3) + aLimit;

        a = new int[aLimit];
        b = new int[bLimit - aLimit];
        c = new int[size - bLimit];

        int x = 0;
        for (var i : hashSet)
        {
            if(x < aLimit)
                a[x] = i;
            else if(x < bLimit)
                b[x - aLimit] = i;
            else
                c[x - bLimit] = i;
            ++x;
        }
    }

    @Test
    void BothSeqEmpty() {
        int[] seq1 = {};
        int[] seq2 = {};
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(IDENTITY, result);
    }

    @Test
    void FirstSeqEmpty() {
        int[] seq1 = {};
        int[] seq2 = b;
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(DIFFERENT, result);
    }

    @Test
    void SecondSeqEmpty() {
        int[] seq1 = a;
        int[] seq2 = {};
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(DIFFERENT, result);
    }

    @Test
    void NotHaveCommonElements() {
        int[] seq1 = a;
        int[] seq2 = b;
        var similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            int idx = FirstIndex(elem, seq1);
            return SearchResult.builder().withPosition(idx).withFound(false).build();
        }));
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(DIFFERENT, result);
    }

    @Test
    void HaveCommonElements() {
        int[] seq1 = ArrayUtils.addAll(a, c);
        int[] seq2 = ArrayUtils.addAll(b, c);
        double expected = (double) c.length / (seq1.length + seq2.length - c.length);
        var similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            int idx = FirstIndex(elem, seq1);
            boolean common = Contains(elem, c);
            return SearchResult.builder().withPosition(idx).withFound(common).build();
        }));
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(expected, result);
    }

    @Test
    void IdentityElements() {
        int[] seq1 = a;
        int[] seq2 = a;
        var similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            int idx = FirstIndex(elem, seq1);
            return SearchResult.builder().withPosition(idx).withFound(true).build();
        }));
        double result = similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(IDENTITY, result);
    }

    @Test
    void InvokeALengthTimes() {
        int[] seq1 = a;
        int[] seq2 = ArrayUtils.addAll(b, c);
        AtomicInteger counter = new AtomicInteger();
        var similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            counter.getAndIncrement();
            int idx = FirstIndex(elem, seq1);
            return SearchResult.builder().withPosition(idx).withFound(false).build();
        }));
        similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(a.length,counter.get());
    }

    @Test
    void NoInvoke() {
        int[] seq1 = {};
        int[] seq2 = a;
        double expected = 0;
        AtomicInteger counter = new AtomicInteger();
        var similarityFinder = new SimilarityFinder(((elem, sequence) -> {
            counter.getAndIncrement();
            return null;
        }));
        similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertEquals(expected,counter.get());
    }

    private int FirstIndex(int elem, int[] seq) {
        for(int idx = 0; idx < seq.length; ++idx)
            if(seq[idx] == elem)
                return idx;
        return -1;
    }

    private boolean Contains(int elem, int[] seq) {
        for(var e : seq)
            if(e == elem)
                return true;
        return false;
    }
}
