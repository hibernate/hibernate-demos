package org.hibernate.search.demos.wikipedia.util;

import java.util.List;

public class SearchResult<T> {

	private long totalCount;

	private List<T> hits;

	/**
	 * Wrap a Hibernate Search result to avoid exposing Hibernate Search to the rest of the application.
	 * @param hibernateSearchSearchResult The Hibernate Search result.
	 */
	public SearchResult(org.hibernate.search.engine.search.query.SearchResult <T> hibernateSearchSearchResult) {
		this( hibernateSearchSearchResult.total().hitCount(), hibernateSearchSearchResult.hits() );
	}

	public SearchResult(long totalCount, List<T> hits) {
		super();
		this.totalCount = totalCount;
		this.hits = hits;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getHits() {
		return hits;
	}

	public void setHits(List<T> hits) {
		this.hits = hits;
	}

}
