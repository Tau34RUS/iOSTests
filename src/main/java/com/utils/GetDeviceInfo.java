package com.utils;

import com.methods.Common;
import com.vars.vars;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class GetDeviceInfo extends Common {

    protected Logger logger;

    public GetDeviceInfo(AppiumDriver<MobileElement> driver)
    {
        super(driver);
        logger = Logger.getLogger("Screenshot");
        PageFactory.initElements(new AppiumFieldDecorator(driver, com.vars.consts.Timeout, TimeUnit.SECONDS), this);
    }

    public void getDeviceInfo(String device) {

        vars.devicename = driver.getCapabilities().getCapability("deviceName").toString();
        vars.screensize = driver.manage().window().getSize();
        logger.info(device+": "+"Screen size: " + vars.screensize);
        logger.info(device+": "+"Device name: " + vars.devicename);

    }


}
