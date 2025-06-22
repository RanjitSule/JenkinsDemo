package qa.utils;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import qa.utils.classPackage.config.Config;
import qa.utils.constants.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
public class DriverUtils {
    private static final Config config;

    static {
        try {
            config = new Config();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static WebDriver driver=null;
    static Properties ObjectRepository=new Properties();

    //Method Name: getDriver
    //Description: This method calls the initialse Driver if driver is null
    public static WebDriver getDriver()
    {
        try{
            if(driver==null)
                initiliseDriver();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return driver;
    }

    //Method Name: initiliseDriver
    //Description: This method initialises the driver
    protected static void initiliseDriver()
    {
        try {

            System.setProperty("webdriver.http.factory", "jdk-http-client");
            System.setProperty("webdriver.chrome.driver", "driver//chromedriver.exe");
            driver = new ChromeDriver();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //Method Name: getConfig
    //Description: This method loads the Object Repository properties file
    public static Config getConfig() throws IOException {
        FileInputStream ObjectRepositoryInputStream = new FileInputStream(System.getProperty("user.dir") + Constants.ObjectRepositoryPath);
        ObjectRepository.load(ObjectRepositoryInputStream);
        return config;
    }

    //Method Name: quiteDriver
    //Description: This method is used for closing browser
    public static void quiteDriver()
    {
        try
        {
            if(driver!=null)driver.quit();
            driver=null;
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
    }

    //Method Name: getObjectRepositoryProperty
    //Description: This method returns the property from Object Repository properties file
    public static String getObjectRepositoryProperty(String propertyName) throws IOException {
        getConfig();
        return ObjectRepository.getProperty(propertyName);
    }
}
