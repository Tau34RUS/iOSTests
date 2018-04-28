package com.utils;

import com.methods.*;
import com.vars.*;
import io.appium.java_client.*;
import io.appium.java_client.pagefactory.*;
import org.apache.log4j.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

import java.util.concurrent.*;

public class GetDeviceInfo extends Common {

    protected Logger logger;

    public GetDeviceInfo(AppiumDriver<WebElement> driver)
    {
        super(driver);
        logger = Logger.getLogger("DeviceInfo");
        PageFactory.initElements(new AppiumFieldDecorator(driver, com.vars.consts.Timeout, TimeUnit.SECONDS), this);
    }

    public void getDeviceInfo(String device) {

        vars.devicename = driver.getCapabilities().getCapability("deviceName").toString();
        vars.screensize = driver.manage().window().getSize();
        logger.info(device+": "+"Screen size: " + vars.screensize);
        logger.info(device+": "+"Device name: " + vars.devicename);

    }


}
