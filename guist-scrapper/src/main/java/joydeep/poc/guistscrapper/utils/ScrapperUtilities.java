package joydeep.poc.guistscrapper.utils;

import joydeep.poc.guistscrapper.domains.Department;
import joydeep.poc.guistscrapper.domains.Faculty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ScrapperUtilities {

    public static WebElement findParent(WebElement child, WebDriver webDriver) {
        return (WebElement) ((JavascriptExecutor) webDriver).executeScript("return arguments[0].parentNode;", child);
    }

    public static String getAttributeValue(WebElement webElement, String attributeName) {
        return webElement.getAttribute(attributeName);
    }

    public static Set<String> getWindowIds(WebDriver webDriver, List<String> linkValues) {
        for (String link : linkValues) {
            ((JavascriptExecutor) webDriver).executeScript("window.open('" + link + "','_blank');");
        }
        Set<String> windowIds = webDriver.getWindowHandles();
        return windowIds;
    }

    public static Department scrapeDepartment(WebDriver webDriver, String windowId) {
        webDriver.switchTo()
                 .window(windowId);
        Document document = Jsoup.parse(webDriver.getPageSource());
        return new Department(document.select("h1.entry-title")
                                      .first()
                                      .text(),
                document.select("div.entry-content")
                        .first()
                        .text());
    }

    public static void closeBrowserTabs(List<String> tabs, WebDriver webDriver) {
        tabs.forEach(tab -> {
            webDriver.switchTo()
                     .window(tab)
                     .close();
        });
    }

    public static List<Faculty> scrapeFaculty(WebDriver webDriver, String windowId) {
        webDriver.switchTo()
                 .window(windowId);
        Document document = Jsoup.parse(webDriver.getPageSource());
        String departmentName = document.select("h1.entry-title")
                                        .first()
                                        .text();
        Elements h3Elements = document.select("h3");
        List<String> designationList = h3Elements.stream()
                                                 .limit(h3Elements.size() - 1)
                                                 .map(elem -> elem.text())
                                                 .collect(Collectors.toList());
        List<Element> facultyTableList = document.select("div.courses-list")
                                                 .stream()
                                                 .map(div -> div.select("table")
                                                                .first())
                                                 .collect(Collectors.toList());
        List<Faculty> facultyList = new ArrayList<Faculty>();
        for (int designationCounter = 1; designationCounter <= designationList.size(); designationCounter++) {
            String designationName = designationList.get(designationCounter - 1);
            Element designationTableElement = facultyTableList.get(designationCounter - 1);
            Elements facultyMemberElements = designationTableElement.select("tr");
            for (Element facultyElement : facultyMemberElements) {
                List<String> facultyDetails = facultyElement.select("span")
                                                            .stream()
                                                            .map(span -> span.text())
                                                            .collect(Collectors.toList());

                facultyList.add(new Faculty(facultyDetails, departmentName, designationName));
            }

        }

        return facultyList;
    }

}
