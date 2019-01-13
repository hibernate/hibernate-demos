/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jpacditesting;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.se.SeContainerInitializer;

import org.hibernate.demos.jpacditesting.support.TransactionalConnectionProvider;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.junit4.WeldInitiator;
import org.jnp.server.NamingBeanImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;

import com.arjuna.ats.jta.utils.JNDIManager;

public abstract class AbstractCdiTest {

    private static NamingBeanImpl NAMING_BEAN;

    @Rule
    public WeldInitiator weld = WeldInitiator.from(((Weld) SeContainerInitializer.newInstance()))
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
