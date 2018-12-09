package org.eop.ssei.web.config.schedule;

import org.eop.ssei.web.config.security.metadatasource.DatabaseFilterInvocationSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author lixinjie
 * @since 2018-08-08
 */
@Configuration
@EnableScheduling
@PropertySource(value = "classpath:schedule/schedule.properties", ignoreResourceNotFound = true)
public class ScheduleConfig {
	
	@Autowired
	private DatabaseFilterInvocationSecurityMetadataSource metadataSource;
	
	@Scheduled(cron = "${schedule.cron}")
	public void execute() {
		//如果该方法里开启了新线程，即使新线程未执行完，该方法也会正常退出本次调度
		//下次调度还会照常执行
		//如果该方法是同步执行的，只有执行完才会退出本次调度，如果未执行完时下次调度已经到来
		//下次调度不会执行，只有等本次调度执行完后才会执行
		metadataSource.reload();
	}
	
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		return new ThreadPoolTaskScheduler();
	}
	
	@Profile("dev")
	@Configuration
	@PropertySource(value = "classpath:schedule/dev/schedule.properties", ignoreResourceNotFound = true)
	static class ScheduleConfigDev {
		
	}
	
	@Profile("test")
	@Configuration
	@PropertySource(value = "classpath:schedule/test/schedule.properties", ignoreResourceNotFound = true)
	static class ScheduleConfigTest {
		
	}
	
	@Profile("prod")
	@Configuration
	@PropertySource(value = "classpath:schedule/prod/schedule.properties", ignoreResourceNotFound = true)
	static class ScheduleConfigProd {
		
	}
}
