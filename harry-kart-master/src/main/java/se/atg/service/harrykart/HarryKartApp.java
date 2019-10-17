package se.atg.service.harrykart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "se.atg.service.harrykart", "se.atg.service.harrykart.rest", "se.atg.service.harrykart.service", "se.atg.service.harrykart.generated" })
public class HarryKartApp {
    public static void main(String ... args) {
        SpringApplication.run(HarryKartApp.class, args);
    }
}
