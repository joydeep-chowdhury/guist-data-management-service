package joydeep.poc.guistscrapper.services;

import joydeep.poc.guistscrapper.configurations.GuistScrapperBusinessConfiguration;
import joydeep.poc.guistscrapper.configurations.SeleniumConfiguration;
import joydeep.poc.guistscrapper.domains.Department;
import joydeep.poc.guistscrapper.domains.Faculty;
import joydeep.poc.guistscrapper.producers.KafkaProducer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static joydeep.poc.guistscrapper.utils.ScrapperUtilities.findParent;
import static joydeep.poc.guistscrapper.utils.ScrapperUtilities.getAttributeValue;
import static joydeep.poc.guistscrapper.utils.ScrapperUtilities.getWindowIds;
import static joydeep.poc.guistscrapper.utils.ScrapperUtilities.scrapeDepartment;
import static joydeep.poc.guistscrapper.utils.ScrapperUtilities.closeBrowserTabs;
import static joydeep.poc.guistscrapper.utils.ScrapperUtilities.scrapeFaculty;

@Service
@Qualifier("guist")
public class GuistScrapperSupport implements ScrapperContract {

    private WebDriver webDriver;
    private final SeleniumConfiguration seleniumConfiguration;
    private final GuistScrapperBusinessConfiguration guistScrapperBusinessConfiguration;
    private final KafkaProducer kafkaproducer;


    public GuistScrapperSupport(final SeleniumConfiguration seleniumConfiguration, final GuistScrapperBusinessConfiguration guistScrapperBusinessConfiguration, final KafkaProducer kafkaproducer) {

        this.guistScrapperBusinessConfiguration = guistScrapperBusinessConfiguration;
        this.kafkaproducer = kafkaproducer;
        this.seleniumConfiguration = seleniumConfiguration;
    }



    @Override
    public void navigate() {
        webDriver=webDriver();
        webDriver.navigate()
                .to(guistScrapperBusinessConfiguration.getRootUrl());
        scrape();
        webDriver.quit();
        webDriver=null;
    }

    private void scrape() {
        collectDepartmentDetails();
        scrapeFacultyDetails();
    }

    private void collectDepartmentDetails() {
        WebDriverWait wait = new WebDriverWait(webDriver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Departments")));
        WebElement departmentLink = webDriver.findElement(By.linkText("Departments"));
        Actions actions = new Actions(webDriver);
        actions.moveToElement(departmentLink)
                .click()
                .perform();
        WebElement parentListDepartment = findParent(departmentLink, webDriver);
        List<WebElement> departmentLinks = parentListDepartment.findElement(By.cssSelector("ul"))
                .findElements(By.cssSelector("li"))
                .stream()
                .map(li -> li.findElement(By.cssSelector("a")))
                .collect(Collectors.toList());
        List<String> departmentLinkValues = departmentLinks.stream()
                .map(link -> getAttributeValue(link, "href"))
                .collect(Collectors.toList());

        ArrayList<String> windowIds = new ArrayList<String>(getWindowIds(webDriver, departmentLinkValues));

        List<Department> departmentList = windowIds.stream()
                .skip(1)
                .map(window -> scrapeDepartment(webDriver, window))
                .collect(Collectors.toList());
        closeBrowserTabs(windowIds.stream()
                        .skip(1)
                        .collect(Collectors.toList()),
                webDriver);
        webDriver.switchTo()
                .window(windowIds.get(0));
        publishDepartmentDetails(departmentList);
    }

    private void scrapeFacultyDetails() {
        WebElement falcultyLink = webDriver.findElement(By.linkText("Faculty"));
        String facultyLinkAddress = falcultyLink.getAttribute("href");
        ArrayList<String> windowIds = new ArrayList<String>(getWindowIds(webDriver, Arrays.asList(facultyLinkAddress)));
        String facultyWindowId = windowIds.get(1);
        webDriver.switchTo()
                .window(facultyWindowId);
        WebDriverWait wait = new WebDriverWait(webDriver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1[class=entry-title]")));
        List<String> facultyDepartmentLinks = webDriver.findElements(By.cssSelector("div[class=elementor-icon-box-icon]"))
                .stream()
                .map(elem -> elem.findElement(By.cssSelector("a"))
                        .getAttribute("href"))
                .collect(Collectors.toList());
        windowIds = new ArrayList<String>(getWindowIds(webDriver, facultyDepartmentLinks));
        List<List<Faculty>> facultyDetails = windowIds.stream()
                .skip(2)
                .map(window -> scrapeFaculty(webDriver, window))
                .collect(Collectors.toList());
        List<Faculty> facultyFinalList = new ArrayList<Faculty>();
        facultyDetails.forEach(facultyList -> {
            facultyList.forEach(faculty -> facultyFinalList.add(faculty));
        });
        publishFacultyDetails(facultyFinalList);
        closeBrowserTabs(windowIds.stream()
                        .skip(1)
                        .collect(Collectors.toList()),
                webDriver);
        webDriver.switchTo()
                .window(windowIds.get(0));
    }

    private void publishDepartmentDetails(List<Department> departmentList) {
        departmentList.forEach(department -> kafkaproducer.produceDepartment(department));
    }

    private void publishFacultyDetails(List<Faculty> facultyList) {
        facultyList.forEach(faculty -> kafkaproducer.produceFaculty(faculty));
    }

    private WebDriver webDriver() {
        System.setProperty(seleniumConfiguration.getWebDriverType(), seleniumConfiguration.getWebDriverPath());
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage()
                .deleteAllCookies();
        webDriver.manage()
                .window()
                .maximize();
        webDriver.manage()
                .timeouts()
                .pageLoadTimeout(seleniumConfiguration.getWebDriverPageLoadTimeout(), TimeUnit.SECONDS);
        return webDriver;
    }

}
