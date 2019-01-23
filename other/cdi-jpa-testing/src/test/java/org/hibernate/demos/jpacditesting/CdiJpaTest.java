/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jpacditesting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.TransactionalException;
import javax.transaction.UserTransaction;

import org.hibernate.demos.jpacditesting.support.JtaEnvironment;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class CdiJpaTest {

    @ClassRule
    public static JtaEnvironment jtaEnvironment = new JtaEnvironment();

    @Rule
    public WeldInitiator weld = WeldInitiator.from(new Weld())
            .activate(RequestScoped.class)
            .inject(this)
            .build();

    // new Weld() above enables scanning of the classpath; alternatively, only the required beans can be listed explicitly:

//    @Rule
//    public WeldInitiator weld = WeldInitiator.from(
//            ObserverTestBean.class,
//            TransactionalTestService.class,
//            TestService.class,
//            EntityManagerProducer.class,
//            EntityManagerFactoryProducer.class,
//            TransactionExtension.class
//        )
//        .activate(RequestScoped.class)
//        .inject(this)
//        .build();

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
