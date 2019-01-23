/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jpacditesting.support;

import org.jnp.server.NamingBeanImpl;
import org.junit.rules.ExternalResource;

import com.arjuna.ats.jta.utils.JNDIManager;

public class JtaEnvironment extends ExternalResource {

    private NamingBeanImpl NAMING_BEAN;

    @Override
    protected void before() throws Throwable {
        NAMING_BEAN = new NamingBeanImpl();
        NAMING_BEAN.start();

        JNDIManager.bindJTAImplementation();
        TransactionalConnectionProvider.bindDataSource();
    }

    @Override
    protected void after() {
        NAMING_BEAN.stop();
    }
}
