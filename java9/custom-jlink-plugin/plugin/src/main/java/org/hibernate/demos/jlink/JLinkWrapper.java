/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jlink;

import java.util.Optional;
import java.util.spi.ToolProvider;

/**
 * A simple wrapper around jlink with the only purpose of being able to specify options
 * such as {@code javagent} or {@code Xdebug} which would be rejected by the jlink binary
 * itself.
 *
 * @author Gunnar Morling
 */
public class JLinkWrapper {

    public static void main(String[] args) {
        Optional<ToolProvider> jlink = ToolProvider.findFirst( "jlink" );

        jlink.get().run(
                System.out,
                System.err,
                args
        );
    }
}
