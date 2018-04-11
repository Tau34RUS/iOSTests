package com.methods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.vars.consts.Timeout;
import static com.vars.consts.phone_nexus_5;
import static com.vars.vars.devicename;
import static com.vars.vars.phonenumber;

public class Profile_screen extends Common {

    protected Logger logger;

    public Profile_screen(AppiumDriver<MobileElement> driver)  {
        super(driver);
        logger = Logger.getLogger("AndroidTestLogger");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Timeout, TimeUnit.SECONDS), this);
    }

    public void userProfileView(String device) {

        driver.findElementById("ru.averia.tracker:id/main_menu_action_profile").click();
        driver.findElementById("ru.averia.tracker:id/iv_ava").clear();
        driver.findElementById("ru.averia.tracker:id/tv_name").clear();
        driver.findElementById("ru.averia.tracker:id/tv_info").clear();

    }

    public void userProfileEdit(String device) {

        driver.findElementById("ru.averia.tracker:id/main_menu_action_profile").click();
        driver.findElementById("ru.averia.tracker:id/iv_ava").clear();
        driver.findElementById("ru.averia.tracker:id/tv_name").clear();
        driver.findElementById("ru.averia.tracker:id/tv_info").clear();

        switch (devicename) {
            case (phone_nexus_5): //damned cyanogen
                break;
            default:

                driver.findElementById("ru.averia.tracker:id/bt_edit_profile").click();
                driver.findElementById("ru.averia.tracker:id/et_last_name").sendKeys("Tester");
                logger.info("Swipe up");
                swipeUp();

                logger.info("Fill phone number");
                Random login = new Random();

                String alphabet = "1234567890";
                phonenumber = "";
                for (int i = 0; i < 11; i++) phonenumber += login.nextInt(alphabet.length());
                driver.findElementById("ru.averia.tracker:id/et_phone").sendKeys(phonenumber);

                logger.info("Swipe down");
                swipeDown();

                driver.findElementById("ru.averia.tracker:id/container_avatar").click();

                phonePhoto();

                driver.findElement(By.id("ru.averia.tracker:id/crop_image_menu_crop")).click();
                sleep(5);
                driver.findElementById("ru.averia.tracker:id/iv_save").click();
                sleep(5);
                logger.info("Saving user profile changes");
                try {driver.findElementById("ru.averia.tracker:id/iv_save").click();}
                catch (org.openqa.selenium.NoSuchElementException e) {logger.info("Already saved?");}
                break;

        }

    }
    
}