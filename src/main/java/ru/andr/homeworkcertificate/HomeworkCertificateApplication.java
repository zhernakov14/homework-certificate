package ru.andr.homeworkcertificate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HomeworkCertificateApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkCertificateApplication.class, args);
    }

}
