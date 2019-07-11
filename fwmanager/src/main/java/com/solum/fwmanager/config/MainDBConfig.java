package com.solum.fwmanager.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef="primaryEntityManagerFactory",
		transactionManagerRef="primaryTransactionManager",
		basePackages= "com.solum.fwmanager.repository"
)
public class MainDBConfig {

	@Primary
	@Bean(name="primaryDataSource")
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariDataSource datasource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
	}
	
	@Primary
	@Bean(name="primaryEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
				EntityManagerFactoryBuilder builder,
				@Qualifier("primaryDataSource") DataSource dataSource
			) {
		return builder
				.dataSource(dataSource)
				.packages("com.solum.fwmanager.entity")
				.build();
	}
	
	@Primary
	@Bean(name="primaryTransactionManager")
	public PlatformTransactionManager primaryTransactionManager(
				@Qualifier("primaryEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory
			) {
		return new JpaTransactionManager(primaryEntityManagerFactory);
	}

}
