package com.mps.sample.filediff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FileDiffApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileDiffApplication.class, args);
    }
}
