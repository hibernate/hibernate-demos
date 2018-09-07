
package org.hibernate.demo.message.post.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MavenUtils {

	private static final String MAVEN_PROPERTIES_FILENAME = "maven.properties";

	private static Properties getProperties() {
		try ( InputStream inputStream = MavenUtils.class.getClassLoader().getResourceAsStream( MAVEN_PROPERTIES_FILENAME ) ) {
			Properties mavenProperties = new Properties();
			mavenProperties.load( inputStream );
			return mavenProperties;
		}
		catch (IOException e) {
			// not expected
			throw new RuntimeException( e );
		}
	}

	public static String getProperty(String propertyName) {
		return getProperties().getProperty( propertyName );
	}
}
