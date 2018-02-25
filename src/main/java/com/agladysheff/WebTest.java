package com.agladysheff;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class WebTest {

    private static final String PRICEFROM_ID = "glf-pricefrom-var";
    private static final String PRICETO_ID = "glf-priceto-var";

    private static final String PRODUCER_TABLE_XPATH = "//h4[contains(.,'Производитель')]/parent::div/following-sibling::div/div";
    private static final String PRODUCER_SHOWALL_XPATH = PRODUCER_TABLE_XPATH + "/div/button[contains(.,'Показать всё')]";
    private static final String PRODUCER_HIDE_XPATH = PRODUCER_TABLE_XPATH + "/div/button[contains(.,'Свернуть')]";
    private static final String PRODUCER_CONTROL_ELEMENT_XPATH = PRODUCER_TABLE_XPATH + "/div[@class='n-filter-block__list-items-wrap']/descendant::div[text()='A']";
    private static final String PRODUCER_INPUT_XPATH = PRODUCER_TABLE_XPATH + "/span/descendant::input";
    private static final String APPLYFILTER_XPATH = "//span[text()='Применить']/parent::button";
    private static final String RESULT_CONTROLELEMENT_XPATH = "//div[1]/div/div[starts-with(@class, 'n-snippet-card2 ')][1]";

    private static final String RESULT_ELEMENTS_XPATH = "//div[starts-with(@class, 'n-snippet-card2 ')]";
    private static final String FIRST_ELEMENT_XPATH = "//div[starts-with(@class, 'n-snippet-list ')][1]/descendant::div[starts-with(@class, 'n-snippet-card2 ')][1]/descendant::div[starts-with(@class, 'n-snippet-card2__title')]/a";
    private static final String SEARCH_XPATH = "//button[contains(.,'Найти')]";
    private static final String SEARCH_RESULT_NAME_XPATH = "//div[@class = 'n-product-summary__headline']/descendant::h1";

    private WebDriver driver;

    public WebTest(WebDriver driver) {
        this.driver = driver;
    }

    public WebTest maximize() {
        driver.manage().window().maximize();
        return this;
    }

    public WebTest getYandex() {
        driver.get("http://yandex.ru/");
        return this;
    }

    @Step()
    public WebTest clickLinkTest(String name) {

        driver.findElement(By.linkText(name)).click();
        return this;
    }

    @Step()
    public WebTest fillPriceBounds(String from, String to) {
        driver.findElement(By.id(PRICEFROM_ID)).sendKeys(from);
        driver.findElement(By.id(PRICETO_ID)).sendKeys(to);
        return this;
    }

    @Step()
    public WebTest selectProducer(String name) {

        showAllProducers();


        searchProducer(name);
        selectCheckboxWithName(name);
        clearSearchProducer();
        hideProducers();
        return this;
    }

    @Step()
    public WebTest applyFilters() {
        driver.findElement(By.xpath(APPLYFILTER_XPATH)).click();
        return this;
    }

    @Step()
    public WebTest equels12(int countResults) {

        Assert.assertEquals (countResults, 12);

        return this;
    }

    @Step()
    public String getFirstResultName() {
        return this.driver.findElement(By.xpath(FIRST_ELEMENT_XPATH)).getText();
    }

    @Step()
    public WebTest search(String text) {

        driver.findElement(By.id("header-search")).sendKeys(text);
        driver.findElement(By.xpath(SEARCH_XPATH)).click();
        return this;
    }


    @Step()
    public WebTest equelsName(String text) {
        String serchResult = getSerchResultName();
        Assert.assertEquals(serchResult, text);
        return this;
    }


    private void showAllProducers() {
        driver.findElement(By.xpath(PRODUCER_SHOWALL_XPATH)).click();
    }

    private void searchProducer(String name) {
        WebElement controlElement = driver.findElement(By.xpath(PRODUCER_CONTROL_ELEMENT_XPATH));
        driver.findElement(By.xpath(PRODUCER_INPUT_XPATH)).sendKeys(name);
        try {
            (new WebDriverWait(this.driver, 25)).until(ExpectedConditions.stalenessOf(controlElement));
        } catch (TimeoutException te) {
            //not update
        }

    }


    private void selectCheckboxWithName(String name) {
        WebElement checkbox = driver.findElement(By.xpath("//label[@class='checkbox__label'][contains(.,'" + name + "')]"));
        if (!checkbox.isSelected() && checkbox.isDisplayed() && checkbox.isEnabled()) {
            WebElement elementForDie = this.driver.findElement(By.xpath(RESULT_CONTROLELEMENT_XPATH));
            checkbox.click();

            try {
                (new WebDriverWait(driver, 5)).until(ExpectedConditions.stalenessOf(elementForDie));
            } catch (TimeoutException te) {
                //not update
            }
        }

    }

    private void clearSearchProducer() {
        driver.findElement(By.xpath(PRODUCER_INPUT_XPATH)).clear();
    }

    private void hideProducers() {
        driver.findElement(By.xpath(PRODUCER_HIDE_XPATH)).click();
    }


    public int countResults() {
        return driver.findElements(By.xpath(RESULT_ELEMENTS_XPATH)).size();
    }


    public String getSerchResultName() {
        return (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(SEARCH_RESULT_NAME_XPATH))).getText();
    }

}
