package org.tbe.watermark;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan
@EnableScheduling
public class Application {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class, args);
	}

	@Configuration
	@EnableWebSecurity
	public static class SecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("dayton").password("carmin365").roles("USER");
		}
	}

	@Scheduled(fixedRate = 60000)
	public void gc() {
		long start = System.currentTimeMillis();
		System.gc();
		LOGGER.info("Executed GC. Colelction Time : " + (System.currentTimeMillis() - start)+"ms");
	}
}
