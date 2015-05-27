/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part3.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * A REST application for managing hikes and organizers.
 *
 * @author Gunnar Morling
 */
@ApplicationPath("/hike-manager")
public class HikeManagerApplication extends Application {
}
