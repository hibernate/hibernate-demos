/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jpacditesting;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class ObserverTestBean {

    @Inject
    private Event<Long> event1;

    @Inject
    private Event<Integer> event2;

    @Inject
    private Event<Short> event3;

    private final StringBuilder sb = new StringBuilder();

    @Transactional
    public void work() {
        event1.fire(1L);
        event2.fire(2);
        event3.fire((short) 3);
    }

    public void observesLong(@Observes(during=TransactionPhase.AFTER_COMPLETION) Long event) {
        sb.append(event);
    }

    public void observeInteger(@Observes(during=TransactionPhase.BEFORE_COMPLETION) Integer event) {
        sb.append(event);
    }

    public void observesShort(@Observes(during=TransactionPhase.IN_PROGRESS) Short event) {
        sb.append(event);
    }

    public String getResult() {
        return sb.toString();
    }
}
