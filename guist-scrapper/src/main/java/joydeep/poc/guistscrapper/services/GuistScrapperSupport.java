package joydeep.poc.guistscrapper.services;

import joydeep.poc.guistscrapper.configurations.GuistScrapperBusinessConfiguration;
import joydeep.poc.guistscrapper.domains.Department;
import joydeep.poc.guistscrapper.domains.Faculty;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    private final WebDriver webDriver;
    private final GuistScrapperBusinessConfiguration guistScrapperBusinessConfiguration;

    public GuistScrapperSupport(final WebDriver webDriver, final GuistScrapperBusinessConfiguration guistScrapperBusinessConfiguration) {
        this.webDriver = webDriver;
        this.guistScrapperBusinessConfiguration = guistScrapperBusinessConfiguration;
    }

    @Override
    public void navigate() {
        webDriver.navigate()
                 .to(guistScrapperBusinessConfiguration.getRootUrl());
        scrape();
         webDriver.quit();
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
        facultyFinalList.forEach(faculty -> System.out.println(faculty));
        closeBrowserTabs(windowIds.stream()
                        .skip(1)
                        .collect(Collectors.toList()),
                webDriver);
        webDriver.switchTo()
                .window(windowIds.get(0));
    }

}
