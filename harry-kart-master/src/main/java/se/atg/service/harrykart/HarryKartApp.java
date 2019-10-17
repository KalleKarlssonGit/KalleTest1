package se.atg.service.harrykart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"se.atg.service.harrykart"})
public class HarryKartApp {
    public static void main(String ... args) {
        SpringApplication.run(HarryKartApp.class, args);
    }
}
