/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.mrjar;

import java.lang.management.ManagementFactory;

/**
 * @author Gunnar Morling
 */
public class ProcessIdProvider {

	public ProcessIdDescriptor getPid() {
		String vmName = ManagementFactory.getRuntimeMXBean().getName();
		long pid = Long.parseLong( vmName.split( "@" )[0] );
		return new ProcessIdDescriptor( pid, "RuntimeMXBean" );
	}
}
