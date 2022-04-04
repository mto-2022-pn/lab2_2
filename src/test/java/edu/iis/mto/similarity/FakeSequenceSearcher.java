package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;

public class FakeSequenceSearcher implements SequenceSearcher {
    @Override
    public SearchResult search(int elem, int[] sequence) {
        if(sequence.length == 0) {
            throw new IllegalArgumentException("Sequence length = 0");
        }

        for(int i = 0; i < sequence.length; i++) {
            if(sequence[i] == elem) {
                return SearchResult.builder().withFound(true).withPosition(i).build();
            }
        }
        return SearchResult.builder().withFound(false).withPosition(-1).build();
    }
}
