package org.hibernate.search.demos.wikipedia.data.dao.hibernate;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.search.demos.wikipedia.data.Page;
import org.hibernate.search.demos.wikipedia.data.dao.PageDao;
import org.hibernate.search.demos.wikipedia.util.SearchResult;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;


@Repository
public class HibernatePageDaoImpl extends AbstractHibernateDao implements PageDao {

	@Override
	public void create(Page page) {
		getEm().persist( page );
	}

	@Override
	public void update(Page page) {
		getEm().merge( page );
	}

	@Override
	public void delete(Page page) {
		getEm().remove( page );
	}

	@Override
	public Page getById(Long id) {
		return getEm().find( Page.class, id );
	}

	@Override
	@SuppressWarnings("unchecked")
	public SearchResult<Page> search(String term, int offset, int limit) {
		FullTextEntityManager fullTextEm = Search.getFullTextEntityManager( getEm() );
		QueryBuilder queryBuilder = fullTextEm.getSearchFactory().buildQueryBuilder()
				.forEntity( Page.class ).get();

		Query luceneQuery;
		if ( term == null || term.isEmpty() ) {
			luceneQuery = queryBuilder.all().createQuery();
		}
		else {
			luceneQuery = queryBuilder.keyword()
					.onField( "title" ).boostedTo( 2.0f )
					.andField( "content" )
					.matching( term )
					.createQuery();
		}
		
		Sort scoreSort = queryBuilder.sort().byScore().createSort();

		FullTextQuery query = fullTextEm.createFullTextQuery( luceneQuery, Page.class )
				.setFirstResult( offset )
				.setMaxResults( limit )
				.setSort( scoreSort );
		
		return new SearchResult<>( query.getResultSize(), query.getResultList() );
	}

}
