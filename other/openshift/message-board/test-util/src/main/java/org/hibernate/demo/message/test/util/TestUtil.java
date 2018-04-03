/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demo.message.test.util;

import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * @author Fabio Massimo Ercoli
 */
public class TestUtil {

	public static void inTransaction(EntityManager em, Consumer<EntityTransaction> consumer) {
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
			if ( RuntimeException.class.isInstance( t ) ) {
				throw (RuntimeException) t;
			}
			throw new RuntimeException( t );
		}
	}

	public static void inTransaction(UserTransaction utx, Consumer<UserTransaction> consumer) {

		try {
			utx.begin();
			consumer.accept( utx );
			utx.commit();
		}
		catch (Throwable t) {
			try {
				if ( utx.getStatus() == Status.STATUS_ACTIVE ) {
					utx.rollback();
				}
			}
			catch (SystemException ex) {
				throw new RuntimeException( ex );
			}
			if ( RuntimeException.class.isInstance( t ) ) {
				throw (RuntimeException) t;
			}
			throw new RuntimeException( t );
		}
	}

}
