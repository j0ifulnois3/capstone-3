package org.yearup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceApplication

{ public static void main(String[] args){
        // 🌟 Hardcode it right here to force the Grocery Store banner dynamically!
        System.setProperty("spring.banner.location", "classpath:banner-grocerystore.txt");

        String dbName = System.getenv("DB_NAME");
        if (dbName != null && !dbName.isBlank()) {
            String bannerResource = "classpath:banner-" + dbName.toLowerCase() + ".txt";
            System.setProperty("spring.banner.location", bannerResource);
        }
        SpringApplication.run(ECommerceApplication.class, args);
    }
}
