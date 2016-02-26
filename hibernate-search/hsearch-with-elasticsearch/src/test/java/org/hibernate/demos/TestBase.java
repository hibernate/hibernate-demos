/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demos;

import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TestBase {

	protected static void inTransaction(EntityManager em, Consumer<EntityTransaction> consumer) {
		EntityTransaction transaction = em.getTransaction();

		transaction.begin();
		try {
			consumer.accept( transaction );
			transaction.commit();
		}
		catch (Throwable t) {
			if ( transaction.isActive() ) {
				transaction.rollback();
			}
			throw t;
		}
	}
}
