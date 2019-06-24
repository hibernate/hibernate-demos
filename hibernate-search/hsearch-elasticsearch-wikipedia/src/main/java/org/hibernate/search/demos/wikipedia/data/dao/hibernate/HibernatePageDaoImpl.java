package org.hibernate.search.demos.wikipedia.data.dao.hibernate;

import org.hibernate.search.demos.wikipedia.data.Page;
import org.hibernate.search.demos.wikipedia.data.dao.PageDao;
import org.hibernate.search.demos.wikipedia.data.dao.PageSort;
import org.hibernate.search.demos.wikipedia.util.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;

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
	public SearchResult<Page> search(String term, PageSort sort, int limit, int offset) {
		SearchSession searchSession = Search.session( getEm() );

		return new SearchResult<>( searchSession.search( Page.class )
				.predicate( f -> {
					if ( term == null || term.isEmpty() ) {
						return f.matchAll();
					}
					else {
						return f.match()
								.onField( "title" ).boostedTo( 2.0f )
								.orField( "content" )
								.matching( term );
					}
				} )
				.sort( f -> {
					switch ( sort ) {
						case TITLE:
							return f.byField( "title_sort" );
						case RELEVANCE:
						default:
							return f.byScore();
					}
				} )
				.fetch( limit, offset )
		);
	}

}
