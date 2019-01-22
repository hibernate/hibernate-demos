/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jpacditesting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.TransactionalException;
import javax.transaction.UserTransaction;

import org.junit.Test;

public class CdiJpaTest extends CdiJpaTestBase {

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction ut;

    @Inject
    private ObserverTestBean observerTestBean;

    @Inject
    private TestService testService;

    @Inject
    private TransactionalTestService transactionalTestService;

    @Test
    public void canInjectEntityManager() {
        assertThat(entityManager).isNotNull();

        entityManager.getTransaction().begin();

        TestEntity te = new TestEntity();
        te.id = UUID.randomUUID();
        te.name = "Test 1";
        entityManager.persist(te);

        te = new TestEntity();
        te.id = UUID.randomUUID();
        te.name = "Test 2";
        entityManager.persist(te);

        entityManager.getTransaction().commit();
        entityManager.clear();

        entityManager.getTransaction().begin();
        List<TestEntity> loaded = entityManager.createQuery("FROM TestEntity te", TestEntity.class).getResultList();
        assertThat(loaded).hasSize(2);
        entityManager.getTransaction().commit();
    }

    @Test
    public void canInjectUserTransaction() throws Exception {
        assertThat(ut).isNotNull();

        ut.begin();

        TestEntity te = new TestEntity();
        te.id = UUID.randomUUID();
        te.name = "Test 1";
        entityManager.persist(te);

        te = new TestEntity();
        te.id = UUID.randomUUID();
        te.name = "Test 2";
        entityManager.persist(te);

        ut.commit();
        entityManager.clear();

        ut.begin();
        List<TestEntity> loaded = entityManager.createQuery("FROM TestEntity te", TestEntity.class).getResultList();
        assertThat(loaded).hasSize(2);
        ut.commit();
    }

    @Test
    public void shouldProcessTransactionalObservers() {
        observerTestBean.work();
        assertThat(observerTestBean.getResult()).isEqualTo("321");
    }

    @Test
    public void canUseDiInEntityListener() {
        entityManager.getTransaction().begin();

        TestEntity te = new TestEntity();
        te.id = UUID.randomUUID();
        te.name = "Test 1";
        entityManager.persist(te);

        te = new TestEntity();
        te.id = UUID.randomUUID();
        te.name = "Test 2";
        entityManager.persist(te);

        entityManager.getTransaction().commit();

        assertThat(testService.getTestEntityNames()).contains("Test 1", "Test 2");
    }

    @Test
    public void canUseDeclarativeTxControl() throws Exception {
        try {
            transactionalTestService.doSomething();
            fail("Exception raised due to missing yet required transaction wasn't raised");
        }
        catch(TransactionalException e) {
            assertThat(e.getMessage().contains("ARJUNA016110"));
        }

        ut.begin();
        assertThat(transactionalTestService.doSomething()).isEqualTo("Success");
        ut.rollback();
    }
}
