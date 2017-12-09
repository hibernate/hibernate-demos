/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jlink.agent;

import java.lang.instrument.Instrumentation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A Java agent which enables the custom JLink plug-in by altering the jdk.jlink module
 * so it exports the {@code Plugin} API and by altering the module with the plug-in
 * implementation so it provides this implementation as a service.
 * <p>
 * The normal {@code --add-exports} option can't be used for this, as the service binding
 * of services happens before this option is processed.
 *
 * @author Gunnar Morling
 */
public class JLinkPluginRegistrationAgent {

    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        Module jlinkModule = ModuleLayer.boot().findModule( "jdk.jlink" ).get();
        Module addIndexModule = ModuleLayer.boot().findModule( "org.hibernate.demos.jlink" ).get();

        Map<String, Set<Module>> extraExports = new HashMap<>();
        extraExports.put( "jdk.tools.jlink.plugin", Collections.singleton( addIndexModule ) );

        // alter jdk.jlink to export its API to the module with our indexing plug-in
        inst.redefineModule( jlinkModule,
                Collections.emptySet(),
                extraExports,
                Collections.emptyMap(),
                Collections.emptySet(),
                Collections.emptyMap()
        );

        Class<?> pluginClass = jlinkModule.getClassLoader().loadClass( "jdk.tools.jlink.plugin.Plugin" );
        Class<?> addIndexPluginClass = addIndexModule.getClassLoader().loadClass( "org.hibernate.demos.jlink.plugins.AddIndexPlugin" );

        Map<Class<?>, List<Class<?>>> extraProvides = new HashMap<>();
        extraProvides.put( pluginClass, Collections.singletonList( addIndexPluginClass ) );

        // alter the module with the indexing plug-in so it provides the plug-in as a service
        inst.redefineModule( addIndexModule,
                Collections.emptySet(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                Collections.emptySet(),
                extraProvides
        );
    }
}
