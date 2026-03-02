package org.gaborbalazs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
class CompactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompactionApplication.class, args);
    }
}
