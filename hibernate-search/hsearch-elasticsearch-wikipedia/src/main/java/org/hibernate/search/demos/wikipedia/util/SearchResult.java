package org.hibernate.search.demos.wikipedia.util;

import java.util.List;

public class SearchResult<T> {

	private long totalCount;

	private List<T> hits;

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
