/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.mrjar;

import java.lang.ProcessHandle;

/**
 * @author Gunnar Morling
 */
public class ProcessIdProvider {

	public ProcessIdDescriptor getPid() {
		long pid = ProcessHandle.current().pid();
		return new ProcessIdDescriptor( pid, "ProcessHandle" );
	}
}
