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
	public SearchResult<Page> search(String term, PageSort sort, int offset, int limit) {
		SearchSession searchSession = Search.session( getEm() );

		return new SearchResult<>( searchSession.search( Page.class )
				.where( f -> {
					if ( term == null || term.isEmpty() ) {
						return f.matchAll();
					}
					else {
						return f.match()
								.field( "title" ).boost( 2.0f )
								.field( "content" )
								.matching( term );
					}
				} )
				.sort( f -> {
					switch ( sort ) {
						case TITLE:
							return f.field( "title_sort" );
						case RELEVANCE:
						default:
							return f.score();
					}
				} )
				.fetch( offset, limit )
		);
	}

}
