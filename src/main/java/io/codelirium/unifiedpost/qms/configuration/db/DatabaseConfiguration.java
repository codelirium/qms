package io.codelirium.unifiedpost.qms.configuration.db;

import com.zaxxer.hikari.HikariDataSource;
import io.codelirium.unifiedpost.qms.QuoteManagementServiceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static io.codelirium.unifiedpost.qms.QuoteManagementServiceApplication.CORE_PACKAGE;
import static io.codelirium.unifiedpost.qms.configuration.db.DatabaseConfiguration.CORE_PACKAGE_JPA_REPOSITORIES;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { CORE_PACKAGE_JPA_REPOSITORIES })
public class DatabaseConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfiguration.class);


	static final String CORE_PACKAGE_JPA_REPOSITORIES = CORE_PACKAGE + ".repository";

	private static final String PROPERTY_NAME_DATABASE_JPA_VENDOR = "db.database";
	private static final String PROPERTY_NAME_DATABASE_DRIVER     = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_URL        = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME   = "db.username";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD   = "db.password";

	private static final String PROPERTY_NAME_DB_HIKARI_POOL_NAME                = "db.hikari.poolName";
	private static final String PROPERTY_NAME_DB_HIKARI_MAX_POOL_SIZE            = "db.hikari.maxPoolSize";
	private static final String PROPERTY_NAME_DB_HIKARI_IDLE_TIMEOUT             = "db.hikari.idleTimeout";
	private static final String PROPERTY_NAME_DB_HIKARI_CONNECTION_TIMEOUT       = "db.hikari.connectionTimeout";
	private static final String PROPERTY_NAME_DB_HIKARI_PREFERRED_TEST_QUERY     = "db.hikari.preferredTestQuery";
	private static final String PROPERTY_NAME_DB_HIKARI_AUTO_COMMIT              = "db.hikari.autoCommit";
	private static final String PROPERTY_NAME_DB_HIKARY_LEAK_DETECTION_THRESHOLD = "db.hikari.leakDetectionThreshold";

	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL                      = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL                    = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_GENERATE_DDL                  = "hibernate.generate_ddl";
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT                       = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO                  = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_HIBERNATE_AUTO_CLOSE_SESSION            = "hibernate.auto_close_session";
	private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_USE_UNICODE        = "hibernate.connection.useUnicode";
	private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_CHARACTER_ENCODING = "hibernate.connection.characterEncoding";
	private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_CHAR_SET           = "hibernate.connection.charSet";
	private static final String PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS              = "hibernate.use_sql_comments";
	private static final String PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS           = "hibernate.generate_statistics";
	private static final String PROPERTY_NAME_HIBERNATE_ID_NEW_GEN_MAPPINGS           = "hibernate.id.new_generator_mappings";


	@Resource
	private Environment env;


	@Bean
	public DataSource dataSource() {

		final HikariDataSource dataSource = new HikariDataSource();

		try {

			dataSource.setDriverClassName(env.getProperty(PROPERTY_NAME_DATABASE_DRIVER));

		} catch (final RuntimeException e) {

			LOGGER.error("Failed to create the dataSource: ", e);

		}


		dataSource.setJdbcUrl(env.getProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		dataSource.setPoolName(env.getProperty(PROPERTY_NAME_DB_HIKARI_POOL_NAME));


		try {

			dataSource.setMaximumPoolSize(Integer.parseInt(env.getProperty(PROPERTY_NAME_DB_HIKARI_MAX_POOL_SIZE)));
			dataSource.setIdleTimeout(TimeUnit.SECONDS.toMillis(Long.parseLong(env.getProperty(PROPERTY_NAME_DB_HIKARI_IDLE_TIMEOUT))));
			dataSource.setConnectionTimeout(TimeUnit.SECONDS.toMillis(Long.parseLong(env.getProperty(PROPERTY_NAME_DB_HIKARI_CONNECTION_TIMEOUT))));
			dataSource.setLeakDetectionThreshold(Long.parseLong(env.getProperty(PROPERTY_NAME_DB_HIKARY_LEAK_DETECTION_THRESHOLD)));

		} catch (final NumberFormatException e) {

			LOGGER.error("Failed to create the dataSource: ", e);

		}


		dataSource.setConnectionTestQuery(env.getProperty(PROPERTY_NAME_DB_HIKARI_PREFERRED_TEST_QUERY));
		dataSource.setAutoCommit(Boolean.valueOf(PROPERTY_NAME_DB_HIKARI_AUTO_COMMIT));


		return dataSource;
	}


	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		final LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan(QuoteManagementServiceApplication.class.getPackageName());
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactory.setJpaProperties(loadHibernateProperties());


		return entityManagerFactory;
	}


	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {

		final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

		hibernateJpaVendorAdapter.setShowSql(Boolean.valueOf(env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL)));
		hibernateJpaVendorAdapter.setGenerateDdl(Boolean.valueOf(env.getProperty(PROPERTY_NAME_HIBERNATE_GENERATE_DDL)));
		hibernateJpaVendorAdapter.setDatabase(Database.valueOf(env.getProperty(PROPERTY_NAME_DATABASE_JPA_VENDOR)));


		return hibernateJpaVendorAdapter;
	}


	@Bean
	public JpaTransactionManager transactionManager() {

		final JpaTransactionManager transactionManager = new JpaTransactionManager();

		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());


		return transactionManager;
	}


	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {

		return new PersistenceExceptionTranslationPostProcessor();

	}


	private Properties loadHibernateProperties() {

		final Properties hibernateProperties = new Properties();

		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT,                       env.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL,                      env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL,                    env.getProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO,                  env.getProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_GENERATE_DDL,                  env.getProperty(PROPERTY_NAME_HIBERNATE_GENERATE_DDL));
		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_AUTO_CLOSE_SESSION,            env.getProperty(PROPERTY_NAME_HIBERNATE_AUTO_CLOSE_SESSION));
		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_USE_UNICODE,        env.getProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_USE_UNICODE));
		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_CHARACTER_ENCODING, env.getProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_CHARACTER_ENCODING));
		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_CHAR_SET,           env.getProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_CHAR_SET));
		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS,              env.getProperty(PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS));
		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS,           env.getProperty(PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS));
		hibernateProperties.put(PROPERTY_NAME_HIBERNATE_ID_NEW_GEN_MAPPINGS,           env.getProperty(PROPERTY_NAME_HIBERNATE_ID_NEW_GEN_MAPPINGS));


		return hibernateProperties;
	}
}
