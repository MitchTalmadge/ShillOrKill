package com.mitchtalmadge.shillorkill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ShillOrKillApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShillOrKillApplication.class, args);
  }
}
