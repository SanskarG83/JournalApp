package net.engineeringdigest.journalApp;


import net.engineeringdigest.journalApp.config.Envloader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {
        Envloader.loadEnv();
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean
    public PlatformTransactionManager func(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager((dbFactory));
    }


}