/*
 * License: Apache License, Version 2.0
 * See the LICENSE file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package com.example.b;

import java.io.InputStream;
import java.util.List;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.IndexReader;

/**
 * A consumer of the Jandex index. We simply print out all types annotated with {@code @Entity}.
 *
 * @author Gunnar Morling
 */
public class Main {

    public static void main(String[] args) throws Exception {
        try (InputStream input = Main.class.getResourceAsStream( "/META-INF/jandex.idx" ) ) {
            IndexReader reader = new IndexReader( input );
            Index index = reader.read();

            List<AnnotationInstance> entityInstances = index.getAnnotations(
                    DotName.createSimple( "com.example.a.Entity" )
            );

            for (AnnotationInstance annotationInstance : entityInstances) {
                System.out.println( annotationInstance.target().asClass().name() );
            }
        }
    }
}
