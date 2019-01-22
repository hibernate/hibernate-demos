/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jpacditesting;

import static org.assertj.core.api.Assertions.assertThat;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Rule;
import org.junit.Test;

public class SimpleCdiTest {

    @Rule
    public WeldInitiator weld = WeldInitiator.from(GreetingService.class)
        .activate(RequestScoped.class)
        .inject(this)
        .build();

    @Inject
    private GreetingService greeter;

    @Test
    public void helloWorld() {
        assertThat(greeter.greet("Java")).isEqualTo("Hello, Java");
    }
}
