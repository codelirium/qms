package io.codelirium.unifiedpost.qms.configuration;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.inject.Inject;
import javax.sql.DataSource;


@Configuration
public class DBUnitConfiguration {

	@Inject
	private DataSource dataSource;


	@Bean
	public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection() {

		final DatabaseConfigBean bean = new DatabaseConfigBean();

		bean.setDatatypeFactory(new H2DataTypeFactory());


		final DatabaseDataSourceConnectionFactoryBean dbConnectionFactory = new DatabaseDataSourceConnectionFactoryBean(dataSource);

		dbConnectionFactory.setDatabaseConfig(bean);


		return dbConnectionFactory;
	}
}
