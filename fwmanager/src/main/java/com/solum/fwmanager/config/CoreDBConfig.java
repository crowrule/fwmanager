package com.solum.fwmanager.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef="aimsCoreEntityManagerFactory",
		transactionManagerRef="aimsCoreTransactionManager",
		basePackages= {
				"com.solum.fwmanager.externel.repository"
		}
)
public class CoreDBConfig {

	@Bean(name="aimsCoreDataSource")
	@ConfigurationProperties(prefix="aimscore.datasource")
	public DataSource datasource(DataSourceProperties properties) {
        HikariDataSource hikariDataSource = new HikariDataSource();

        return hikariDataSource;
	}
	

	@Bean(name="aimsCoreEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean aimscoreEntityManager(
				EntityManagerFactoryBuilder	builder,
				@Qualifier("aimsCoreDataSource") DataSource	dataSource
			) {
		return	builder.dataSource(dataSource)
					.packages("com.solution.fwmanager.externel.entity")
					.build();
	}
	
	
	@Bean(name="aimsCoreTransactionManager")
	public PlatformTransactionManager aimsTransactionManager(
				@Qualifier("aimsCoreEntityManagerFactory") EntityManagerFactory aimsCoreManagerFactory
			) {
		return new JpaTransactionManager(aimsCoreManagerFactory);
	}
}
