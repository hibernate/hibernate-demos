/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jpacditesting;

import javax.enterprise.context.RequestScoped;

import org.hibernate.demos.jpacditesting.support.EntityManagerFactoryProducer;
import org.hibernate.demos.jpacditesting.support.EntityManagerProducer;
import org.hibernate.demos.jpacditesting.support.TransactionalConnectionProvider;
import org.jboss.weld.junit4.WeldInitiator;
import org.jnp.server.NamingBeanImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;

import com.arjuna.ats.jta.cdi.TransactionExtension;
import com.arjuna.ats.jta.utils.JNDIManager;

public abstract class CdiJpaTestBase {

    private static NamingBeanImpl NAMING_BEAN;

//    @Rule
//    public WeldInitiator weld = WeldInitiator.from(new Weld())
//        .activate(RequestScoped.class)
//        .inject(this)
//        .build();

    // new Weld() above enables scanning of the classpath; alternatively, only the required beans can be listed explicitly:

    @Rule
    public WeldInitiator weld = WeldInitiator.from(
            ObserverTestBean.class,
            TransactionalTestService.class,
            TestService.class,
            EntityManagerProducer.class,
            EntityManagerFactoryProducer.class,
            TransactionExtension.class
        )
        .activate(RequestScoped.class)
        .inject(this)
        .build();

    @BeforeClass
    public static void startJndiAndBindJtaAndDataSource() throws Exception {
        NAMING_BEAN = new NamingBeanImpl();
        NAMING_BEAN.start();

        JNDIManager.bindJTAImplementation();
        TransactionalConnectionProvider.bindDataSource();
    }

    @AfterClass
    public static void stopJndi() {
        NAMING_BEAN.stop();
    }
}
