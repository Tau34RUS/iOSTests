package com.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.vars.consts.folder_name;

public class Screenshot extends com.methods.Common {

    protected Logger logger;

    public Screenshot(AppiumDriver<MobileElement> driver)
    {
        super(driver);
        logger = Logger.getLogger("Screenshot");
        PageFactory.initElements(new AppiumFieldDecorator(driver, com.vars.consts.Timeout, TimeUnit.SECONDS), this);
    }

    public void captureScreenShots(String device, String testName) throws IOException
    {

            File f= driver.getScreenshotAs(OutputType.FILE);
            String file_path = folder_name + device +"_"+ testName +"_"+ "fail.png";
            //create dir
            new File(folder_name).mkdir();
            FileUtils.copyFile(f, new File(file_path));

            logger.info(device + ": Screenshot saved as " + file_path);

    }

}

