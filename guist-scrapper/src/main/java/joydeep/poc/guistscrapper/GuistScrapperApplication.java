package joydeep.poc.guistscrapper;

import joydeep.poc.guistscrapper.services.GuistScrapperSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class GuistScrapperApplication {
    @Autowired
    private GuistScrapperSupport test;

    public static void main(String[] args) {
        SpringApplication.run(GuistScrapperApplication.class, args);
    }

}
