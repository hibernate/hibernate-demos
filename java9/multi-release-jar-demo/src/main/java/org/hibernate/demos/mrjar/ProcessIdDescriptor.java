/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.mrjar;

/**
 * @author Gunnar Morling
 */
public class ProcessIdDescriptor {

	private final long pid;
	private final String providerName;

	public ProcessIdDescriptor(long pid, String providerName) {
		this.pid = pid;
		this.providerName = providerName;
	}

	public long getPid() {
		return pid;
	}

	public String getProviderName() {
		return providerName;
	}
}
