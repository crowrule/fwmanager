package com.solum.fwmanager.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncEventConfig implements AsyncConfigurer {
    
	@Override
	public Executor getAsyncExecutor() {
		
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    //executor.setMaxPoolSize(10);
	    //executor.setCorePoolSize(4);
	    executor.setThreadNamePrefix("AsyncThreadInPool-");

	    // Initialize the executor
	    executor.initialize();

	    return executor;
	}

}
