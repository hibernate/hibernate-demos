package org.hibernate.demos.quarkus;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeClientResourceIT extends ClientResourceTest {

    // Execute the same tests but in native mode.
}