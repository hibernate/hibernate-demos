package org.hibernate.brmeyer.demo;

import java.util.Properties;

import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;



/**
 * The Class SimpleMultiTenantConnectionProvider.
 */
public class SimpleMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

	/** The Constant TENANT_ID_1. */
	public static final String TENANT_ID_1 = "foo1";
	
	/** The Constant TENANT_ID_2. */
	public static final String TENANT_ID_2 = "foo2";

	/** The Constant DB_NAME_1. */
	public static final String DB_NAME_1 = "foo1_tbl";
	
	/** The Constant DB_NAME_2. */
	public static final String DB_NAME_2 = "foo2_tbl";
	
	/** The Constant DRIVER. */
	private static final String DRIVER = "org.h2.Driver";
	
	/** The Constant URL. */
	private static final String URL = "jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;MVCC=TRUE";
	
	/** The Constant USER. */
	private static final String USER = "sa";
	
	/** The Constant PASS. */
	private static final String PASS = "";
	
	/** The foo 1 connection provider. */
	private final ConnectionProvider foo1ConnectionProvider = buildConnectionProvider( DB_NAME_1 );
	
	/** The foo 2 connection provider. */
	private final ConnectionProvider foo2ConnectionProvider = buildConnectionProvider( DB_NAME_2 );

	/* (non-Javadoc)
	 * @see org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider#getAnyConnectionProvider()
	 */
	@Override
	protected ConnectionProvider getAnyConnectionProvider() {
		return foo1ConnectionProvider;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider#selectConnectionProvider(java.lang.String)
	 */
	@Override
	protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
		if ( TENANT_ID_1.equals( tenantIdentifier ) ) {
			return foo1ConnectionProvider;
		}
		else if ( TENANT_ID_2.equals( tenantIdentifier ) ) {
			return foo2ConnectionProvider;
		}
		throw new IllegalArgumentException( "Unknown tenant identifier" );
	}
	
	/**
	 * Builds the connection provider.
	 *
	 * @param dbName the db name
	 * @return the connection provider
	 */
	private ConnectionProvider buildConnectionProvider(String dbName) {
		Properties props = new Properties( null );
		props.put( "hibernate.connection.driver_class", DRIVER );
		// Inject dbName into connection url string.
		props.put( "hibernate.connection.url", String.format( URL, dbName ) );
		props.put( "hibernate.connection.username", USER );
		props.put( "hibernate.connection.password", PASS );
		
		// Note that DriverManagerConnectionProviderImpl is an internal class.  However, rather than creating
		// a ConnectionProvider, I'm using it for simplicity's sake.
		// DriverManagerConnectionProviderImpl obtains a Connection through the JDBC Driver#connect
		DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();
		connectionProvider.configure( props );
		return connectionProvider;
	}

}
