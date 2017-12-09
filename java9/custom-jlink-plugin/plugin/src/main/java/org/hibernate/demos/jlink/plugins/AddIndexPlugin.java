/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.demos.jlink.plugins;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jboss.jandex.Index;
import org.jboss.jandex.IndexWriter;
import org.jboss.jandex.Indexer;

import jdk.tools.jlink.plugin.Plugin;
import jdk.tools.jlink.plugin.ResourcePool;
import jdk.tools.jlink.plugin.ResourcePoolBuilder;
import jdk.tools.jlink.plugin.ResourcePoolEntry;

/**
 * A plug-in for jlink which adds a Jandex index to the image.
 *
 * @author Gunnar Morling
 *
 */
public class AddIndexPlugin implements Plugin {

    private static final String NAME = "add-index";
    private String targetModule;
    private List<String> modules;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public void configure(Map<String, String> config) {
        targetModule = config.get( NAME );
        String modulesToIndex = config.get( "for-modules" );
        this.modules = Arrays.asList( modulesToIndex.split( "," ) );
    }

    @Override
    public String getDescription() {
        return "Adds an annotation index for one or more modules." + System.lineSeparator() +
                "<target-module>: name of the module which will host the index" + System.lineSeparator() +
                "<source-module-list>: comma-separated list of modules to include within the index";
    }

    @Override
    public String getArgumentsDescription() {
        return "<target-module>:for-modules=<source-module-list>";
    }

    @Override
    public ResourcePool transform(ResourcePool in, ResourcePoolBuilder out) {
        Indexer indexer = new Indexer();

        in.transformAndCopy(
                e -> {
                    if ( addToIndex( e ) ) {
                        try {
                            indexer.index( e.content() );
                        }
                        catch(Exception ex) {
                            throw new RuntimeException( ex );
                        }
                    }

                    return e;
                },
                out
        );

        ByteArrayOutputStream index = writeToOutputStream( indexer );

        out.add( ResourcePoolEntry.create( "/" + targetModule + "/META-INF/jandex.idx", index.toByteArray() ) );
        return out.build();
    }

    private ByteArrayOutputStream writeToOutputStream(Indexer indexer) {
        ByteArrayOutputStream outStream = null;

        Index index = indexer.complete();

        outStream = new ByteArrayOutputStream();
        IndexWriter writer = new IndexWriter( outStream );

        try {
            writer.write(index);
        }
        catch( Exception e ) {
            throw new RuntimeException( e );
        }
        finally {
            try {
                outStream.close();
            }
            catch( Exception e ) {
                throw new RuntimeException( e );
            }
        }

        return outStream;
    }

    private boolean addToIndex(ResourcePoolEntry entry) {
        if ( !entry.path().endsWith("class") ) {
            return false;
        }

        for ( String moduleToIndex : modules ) {
            if ( entry.path().startsWith( "/" + moduleToIndex ) ) {
                return true;
            }
        }

        return false;
    }
}
