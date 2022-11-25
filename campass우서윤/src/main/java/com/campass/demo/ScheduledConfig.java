package com.campass.demo;

import org.springframework.context.annotation.*;
import org.springframework.scheduling.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.*;

@Configuration
@EnableScheduling
public class ScheduledConfig {
	@Bean
	public TaskScheduler getTaskScheduler() {
		// @Scheduled 어노테이션을 위한 스레드 설정
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(10);
		return scheduler;
	}
}
