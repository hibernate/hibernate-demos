package org.hibernate.search.demos.wikipedia.data.dao.hibernate;

import org.hibernate.search.demos.wikipedia.data.Page;
import org.hibernate.search.demos.wikipedia.data.dao.PageDao;
import org.hibernate.search.demos.wikipedia.util.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.jpa.FullTextEntityManager;
import org.hibernate.search.mapper.orm.jpa.FullTextQuery;

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

		FullTextQuery query = fullTextEm.search( Page.class ).query()
				.asEntity()
				.predicate( f -> {
					if ( term == null || term.isEmpty() ) {
						return f.matchAll().toPredicate();
					}
					else {
						return f.match()
								.onField( "title" ).boostedTo( 2.0f )
								.orField( "content" )
								.matching( term )
								.toPredicate();
					}
				} )
				.sort( f -> f.byScore() )
				.build();

		query.setFirstResult( offset )
				.setMaxResults( limit );

		return new SearchResult<>( query.getResultSize(), query.getResultList() );
	}

}
