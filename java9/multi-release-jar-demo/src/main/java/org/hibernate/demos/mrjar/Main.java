/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.mrjar;

public class Main {

	public static void main(String[] args) {
		ProcessIdDescriptor pid = new ProcessIdProvider().getPid();

		System.out.println( "PID: " + pid.getPid() );
		System.out.println( "Provider: " + pid.getProviderName() );
	}
}
