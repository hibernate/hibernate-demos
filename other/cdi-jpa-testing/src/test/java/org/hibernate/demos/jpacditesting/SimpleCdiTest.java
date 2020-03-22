/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jpacditesting;

import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoWeld
public class SimpleCdiTest {

    @Inject
    private GreetingService greeter;

    @Test
    public void helloWorld() {
        assertThat(greeter.greet("Java")).isEqualTo("Hello, Java");
    }
}
