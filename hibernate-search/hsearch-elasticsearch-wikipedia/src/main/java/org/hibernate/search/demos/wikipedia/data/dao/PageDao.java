package org.hibernate.search.demos.wikipedia.data.dao;

import org.hibernate.search.demos.wikipedia.data.Page;
import org.hibernate.search.demos.wikipedia.util.SearchResult;

public interface PageDao {
	
	void create(Page page);
	
	void update(Page page);
	
	void delete(Page page);

	Page getById(Long id);

	SearchResult<Page> search(String term, PageSort sort, int limit, int offset);

}
