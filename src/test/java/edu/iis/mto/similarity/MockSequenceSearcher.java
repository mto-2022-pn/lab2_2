package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;

public class MockSequenceSearcher extends FakeSequenceSearcher {
    private int searchInvokeCounter;

    public MockSequenceSearcher() {
        searchInvokeCounter = 0;
    }

    public int getSearchInvokeCounter() {
        return searchInvokeCounter;
    }

    @Override
    public SearchResult search(int elem, int[] sequence) {
        searchInvokeCounter++;
        return super.search(elem, sequence);
    }
}
