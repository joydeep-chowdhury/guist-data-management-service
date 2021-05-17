package joydeep.poc.guistscrapper.schedulers;

import joydeep.poc.guistscrapper.services.ScrapperContract;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleScrapeSupport {

    private final ScrapperContract scrapperContract;
    public ScheduleScrapeSupport(@Qualifier("guist") final ScrapperContract scrapperContract){
           this.scrapperContract=scrapperContract;
    }

    @Scheduled(cron = "0 */3 * * * *")
    public void scrapeDetails(){
        scrapperContract.navigate();
    }
}
