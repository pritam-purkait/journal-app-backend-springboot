package com.pritam.journalApp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalAppApplication {


    public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()    // ← skip if no .env
				.load();


		System.setProperty("MONGODB_URI", dotenv.get("MONGODB_URI"));

		ConfigurableApplicationContext context = SpringApplication.run(JournalAppApplication.class, args);
		ConfigurableEnvironment environment =context.getEnvironment();
		System.out.println(environment.getActiveProfiles()[0]);

		//SpringApplication.run(JournalAppApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager transaction
			(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
}
