package org.hibernate.search.demos.wikipedia.data.dao.hibernate;

import org.hibernate.search.demos.wikipedia.data.Page;
import org.hibernate.search.demos.wikipedia.data.QPage;
import org.hibernate.search.demos.wikipedia.data.dao.PageDao;
import org.hibernate.search.demos.wikipedia.util.SearchResult;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;


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
	public SearchResult<Page> search(String term, int offset, int limit) {
		JPAQuery<Page> query = query()
				.select( QPage.page )
				.from( QPage.page )
				.where( QPage.page.title.likeIgnoreCase( "%" + term + "%" )
						.or( QPage.page.content.likeIgnoreCase( "%" + term + "%" ) ) )
				.offset( offset )
				.limit( limit );
		return new SearchResult<>( query.fetchCount(), query.fetch() );
	}

}
