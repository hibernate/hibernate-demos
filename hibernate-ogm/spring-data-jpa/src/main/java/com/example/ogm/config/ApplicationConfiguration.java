package com.example.ogm.config;

import java.io.File;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.hibernate.cfg.Environment;
import org.hibernate.ogm.cfg.OgmProperties;
import org.hibernate.ogm.datastore.impl.DatastoreProviderType;
import org.hibernate.ogm.datastore.neo4j.Neo4jProperties;
import org.hibernate.ogm.jpa.HibernateOgmPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@ComponentScan("com.example.ogm")
@EnableJpaRepositories("com.example.ogm.data.jpa")
public class ApplicationConfiguration {

	private Properties hibProperties() {
		String dbPath = System.getProperty("java.io.tmpdir") + File.separator + "NEO4J_DB";
		Properties properties = new Properties();
		properties.put( Environment.HBM2DDL_AUTO, "none" );
		properties.put( OgmProperties.DATASTORE_PROVIDER, DatastoreProviderType.NEO4J_EMBEDDED.name() );
		properties.put( Neo4jProperties.DATABASE_PATH, dbPath );
		return properties;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
		bean.setGenerateDdl( true );
		return bean;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager( emf );
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		String persistenceUnitName = "neo4j-PU";

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter( jpaVendorAdapter() );
		factory.setPackagesToScan( "com.example.ogm" );
		factory.setPersistenceUnitName( persistenceUnitName );
		factory.setJpaProperties( hibProperties() );
		factory.setPersistenceProviderClass( HibernateOgmPersistence.class );
		return factory;
	}
}
