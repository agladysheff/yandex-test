package com.agladysheff.test;

import com.agladysheff.WebTest;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import java.util.concurrent.TimeUnit;

public class TestYandex {
    private static WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance().setup();
    }


    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }


    @After

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test

    @DisplayName("Test 1")
    public void test1() {
        WebTest webTestNotebook = new WebTest(driver);
        webTestNotebook.maximize()
                .getYandex()
                .clickLinkTest("Маркет")
                .clickLinkTest("Компьютеры")
                .clickLinkTest("Ноутбуки")
                .fillPriceBounds("", "30000")
                .selectProducer("HP")
                .selectProducer("Lenovo")
               ;

        int count = webTestNotebook.countResults();
        webTestNotebook.equalls12(count,48);

        String text = webTestNotebook.getFirstResultName();
        webTestNotebook.search(text)
                .equallsName(text);
    }


    @Test

    @DisplayName("Test 2")
    public void test2() {

        WebTest webTestNotebook = new WebTest(driver);
        webTestNotebook.maximize()
                .getYandex()
                .clickLinkTest("Маркет")
                .clickLinkTest("Компьютеры")
                .clickLinkTest("Планшеты")
                .fillPriceBounds("20000", "25000")
                .selectProducer("Acer")
                .selectProducer("DELL");


       int count = webTestNotebook.countResults();
        webTestNotebook.equalls12(count,5);

       String text = webTestNotebook.getFirstResultName();
        webTestNotebook.search(text)
                .equallsName(text);


    }


}
