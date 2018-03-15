package com.agladysheff;


import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




public class WebTest {

    private static final String PRICEFROM_ID = "glpricefrom";
    private static final String PRICETO_ID = "glpriceto";


    private static final String PRODUCER_SHOWALL_XPATH = "//legend[text()='Производитель']/..//a[contains(.,'Показать')]";
    private static final String PRODUCER_HIDE_XPATH = "//legend[text()='Производитель']//..//a[text()='Свернуть']";
    private static final String PRODUCER_CONTROL_ELEMENT_XPATH = "//legend[text()='Производитель']/..//div[text()='A']";
    private static final String PRODUCER_INPUT_XPATH = "//legend[text()='Производитель']/..//input[@type='text']";
    private static final String RESULT_CONTROLELEMENT_XPATH = "//div[1]/div/div[starts-with(@class, 'n-snippet-card2 ')][1]";
    private static final String RESULT_ELEMENTS_XPATH = "//div[starts-with(@class, 'n-snippet-card2 ')]";
    private static final String FIRST_ELEMENT_XPATH = "//div[starts-with(@class, 'n-snippet-list ')][1]//div[starts-with(@class, 'n-snippet-card2 ')][1]//div[starts-with(@class, 'n-snippet-card2__title')]/a";
    private static final String SEARCH_XPATH = "//button[contains(.,'Найти')]";
    private static final String SEARCH_RESULT_NAME_XPATH = "//div[@class = 'n-product-summary__headline']//h1";

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
        WebDriverWait wait = new WebDriverWait(driver, 3);
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showAllProducers();


        searchProducer(name);
        selectCheckboxWithName(name);
        clearSearchProducer();
        hideProducers();
        return this;
    }



    @Step()
    public WebTest equalls12(int countResults, int num) {

        Assert.assertEquals(countResults, num);

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
    public WebTest equallsName(String text) {
        String serchResult = getSerchResultName(text);
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

        }

    }


    private void selectCheckboxWithName(String name) {
        WebElement checkbox = driver.findElement(By.xpath("//span[text()='" + name + "']/.."));


        WebElement elementForDie = this.driver.findElement(By.xpath(RESULT_CONTROLELEMENT_XPATH));
        checkbox.click();


        try {
            (new WebDriverWait(driver, 5)).until(ExpectedConditions.stalenessOf(elementForDie));
        } catch (TimeoutException te) {

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


    public String getSerchResultName(String text) {
        String name = text;
        try {
            name = driver.findElement(By.xpath(SEARCH_RESULT_NAME_XPATH)).getText();
        } catch (Exception e) {
            driver.findElement(By.xpath("//a[@title='" + name + "']")).click();
            return driver.findElement(By.xpath(SEARCH_RESULT_NAME_XPATH)).getText();

        }
        return name;
    }

}
