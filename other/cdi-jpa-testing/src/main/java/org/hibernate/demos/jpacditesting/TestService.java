/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jpacditesting;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TestService {

    private final List<String> names = new ArrayList<>();

    public List<String> getTestEntityNames() {
        return names;
    }

    public void addTestEntityName(String name) {
        names.add(name);
    }
}
