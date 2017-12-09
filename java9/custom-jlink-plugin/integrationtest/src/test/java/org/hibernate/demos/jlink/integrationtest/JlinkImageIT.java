/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jlink.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

public class JlinkImageIT {

    private static String agentPath;
    private static String modulesDir;
    private static String jlinkImageDir;

    @BeforeClass
    public static void setUp() {
        agentPath = System.getProperty( "agentPath" );
        modulesDir = System.getProperty( "modulesDir" );
        jlinkImageDir = System.getProperty( "jlinkImageDir" );
    }

    @Test
    public void testRuntimeImage() throws Exception {
        runJLinkWithAddIndexPlugin();

        Process process = new ProcessBuilder()
                .command( jlinkImageDir + "/bin/java", "--module", "com.example.b/com.example.b.Main" )
                .start();

        String output = readToEnd( process.getInputStream() );
        String errorOutput = readToEnd( process.getErrorStream() );

        assertEquals(
                "No error output expected: " + errorOutput,
                "",
                errorOutput
        );

        assertTrue(
                "Output doesn't match expectation: " + output,
                output.contains( "com.example.a.AnEntity" )
        );

        assertFalse(
                "Output doesn't match expectation: " + output,
                output.contains( "com.example.a.NotAnEntity" )
        );
    }

    private void runJLinkWithAddIndexPlugin() throws Exception {
        Process process = new ProcessBuilder()
                .command( System.getProperty( "java.home" ) + "/bin/java",
                        "-Xdebug",
                        "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000",
                        "-javaagent:" + agentPath,
                        "--module-path", modulesDir,
                        "--module", "org.hibernate.demos.jlink",

                        // arguments passed on to JLink
                        "--module-path", System.getProperty( "java.home" ) + "/jmods/:" + modulesDir,
                        "--add-modules", "java.base,com.example.b",
                        "--output", jlinkImageDir,
                        "--add-index=com.example.b:for-modules=com.example.a"
//                        "--list-plugins"
                )
                .start();

        String output = readToEnd( process.getInputStream() );
        String errorOutput = readToEnd( process.getErrorStream() );

        System.out.println( output );

        assertEquals(
                "No error output expected: " + errorOutput,
                "",
                errorOutput
        );
    }

    @SuppressWarnings("resource")
    private String readToEnd(InputStream is) {
        try( Scanner s = new Scanner( is ).useDelimiter("\\A") ) {
            return s.hasNext() ? s.next() : "";
        }
    }
}
