package qa.common.pages;




import io.qameta.allure.Allure;


import io.qameta.allure.model.Status;
import junit.framework.Assert;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import qa.utils.DriverUtils;
import qa.utils.FileUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static java.sql.DriverManager.getDriver;

public class CommonPage extends DriverUtils {
    public static WebDriverWait ExplicitWait;
  //  public static WebDriver driver =getDriver();
    static WebElement Webelement = null;
    static String PropertyType = null;
    static String PropertyValue = null;
    public static JavascriptExecutor javaScriptDriver;
    static String userID;
    static String password;
    static String applicationURL;
    static List<WebElement> webElements = null;
   public static  String ExpectedMessage=null;
    public static String ActualdMessages=null;


    //Method Name: Launch
    //Description: This method launches the application url
    public static void Launch(String url){
        driver.get(url);
        driver.manage().window().maximize();

    }

    //Method Name : GetValue
    //Description : This method is used to get value from the property file
    public static String GetValue(String WebElement) throws IOException {
        String ObjectRepositoryValue=null;
        try {
            ObjectRepositoryValue = getObjectRepositoryProperty(WebElement);
        }
        catch(Exception e){
            System.out.println("unable to get the value from properties file");
        }
        return ObjectRepositoryValue;
    }

    //Method Name : GetWebElement
    //Description : This method is used to locate the web element in the application
    public static WebElement GetWebElement(String ObjectRepositoryValue ){
        try{
            String[] Property= GetValue(ObjectRepositoryValue).split(":=");
            PropertyType = Property[0];
            PropertyValue = Property[1];
            if (PropertyType.equalsIgnoreCase("id")) {
                Webelement = driver.findElement(By.id(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("name")) {
                Webelement = driver.findElement(By.name(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("xpath")) {
                Webelement = driver.findElement(By.xpath(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("linktext")) {
                Webelement = driver.findElement(By.linkText(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("cssselector")) {
                Webelement = driver.findElement(By.cssSelector(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("partiallinktext")) {
                Webelement = driver.findElement(By.partialLinkText(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("classname")) {
                Webelement = driver.findElement(By.className(PropertyValue));
            } else {
                System.out.println(" Invalid property type ");
            }
        }
        catch(Exception e){
            System.out.println("Unable to find the web element in object repository , "+ObjectRepositoryValue);
        }
        return Webelement;
    }

    //Method Name : TableCoulmns
    //Description : This method returns all columns of the table present in the webpage
    public static List<WebElement> TableCoulmns(String element) {
        List<WebElement> Columns = null;
        try {
            Columns = driver.findElements(GetElementForWait(element));
        } catch (Exception e) {
            System.out.println(" Invalid property type ");
        }
        return Columns;
    }

    //Method Name : TableRows
    //Description : This method returns all rows of the table present in the webpage
    public static List<WebElement> TableRows(String element) {
        List<WebElement> Rows = null;
        try {
            Rows = driver.findElements(GetElementForWait(element));
        } catch (Exception e) {
            System.out.println(" Invalid property type ");
        }
        return Rows;
    }

    //Method Name : WebClick
    //Description : This method is used to click on the specified web element
    public static void WebClick(String SpecificElement){
        try {
            WaitForPresenceOfElementLocated(SpecificElement);
            GetWebElement(SpecificElement).click();
        }
        catch(Exception e){
            System.out.println("Element Click Intercepted Exception");
        }
    }

    //Method Name : WebSend
    //Description : This is method is used for sending the text
    public static void WebSend(String Field,String TextTobeSend){
        try {
            GetWebElement(Field).click();
            Wait2();
            //Enters the Text
            GetWebElement(Field).sendKeys(TextTobeSend);
        }
        catch(Exception e){
            System.out.println("Failed to enter the data");
        }
    }

    //Method Name : WebGetText
    //Description : This method returns the text of the web element
    public static String WebGetText(String element){
        String WebText=null;
        WaitForPresenceOfElementLocated(element);
        try{
            WebText=GetWebElement(element).getText();
        }
        catch(Exception e){
            System.out.println("Failed to get the text");
        }
        return WebText;
    }

    public static By GetElementForWait(String ObjectRepositoryValue) {
        By WaitLocator = null;
        try {
            String[] Property = GetValue(ObjectRepositoryValue).split(":=");
            PropertyType = Property[0];
            PropertyValue = Property[1];
            if (PropertyType.equalsIgnoreCase("id")) {
                WaitLocator = By.id(PropertyValue);
            } else if (PropertyType.equalsIgnoreCase("name")) {
                WaitLocator = By.name(PropertyValue);
            } else if (PropertyType.equalsIgnoreCase("xpath")) {
                WaitLocator = By.xpath(PropertyValue);
            } else if (PropertyType.equalsIgnoreCase("linktext")) {
                WaitLocator = By.linkText(PropertyValue);
            } else if (PropertyType.equalsIgnoreCase("cssselector")) {
                WaitLocator = By.cssSelector(PropertyValue);
            } else if (PropertyType.equalsIgnoreCase("partiallinktext")) {
                WaitLocator = By.partialLinkText(PropertyValue);
            } else if (PropertyType.equalsIgnoreCase("classname")) {
                WaitLocator = By.className(PropertyValue);
            } else {
                System.out.println(" Invalid ");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return WaitLocator;
    }

    //Method Name :WaitForPresenceOfElementLocated
    //Description :This method is used to explicitly wait for presence of element located
    public static boolean WaitForPresenceOfElementLocated(String element){
        try {
            ExplicitWait = new WebDriverWait(driver, Duration.ofSeconds(100));
            ExplicitWait.until(ExpectedConditions.presenceOfElementLocated(GetElementForWait(element)));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //Method Name: SelectOptionFromDropDown
    //Description: This method is used to select the option drom drop down with select class
    public static void selectDropDownWithSelect(String element,String text)
    {
        WaitForElementTovisibile(element);
        Select selectdropdown= new Select(GetWebElement(element));
        selectdropdown.selectByVisibleText(text);
    }

    //Method Name: WaitForElementTovisibile
    //Description: This method is used to explicitly wait for element to be visible
    public static boolean WaitForElementTovisibile(String element){
        try{
            ExplicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            ExplicitWait.until(ExpectedConditions.visibilityOfElementLocated(GetElementForWait(element)));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //Method Name: Scroll
    //Description: This method scroll the web page until the web element is visible
    public static void Scroll(String scrollelement){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",GetWebElement(scrollelement));
    }

    //Method Name : MouseHover
    //Description : This method moves the mouse control to the specific element
    public static void MouseHover(String ActionElement){
        Actions action=new Actions(driver);
        action.moveToElement(GetWebElement(ActionElement)).build().perform();

    }

    //Method Name: GeneratedSourcingBundleID
    //Description: This methos returns the dynamically generated Sourcing Bundle ID
    public static String GeneratedSourcingBundleID(String element) {
        String[] alertMsg = null;
        try {
            WaitForElementTovisibile(element);
            alertMsg = WebGetText("IDAlertMsg").split(" ");
            //System.out.println("SB-ID:"+alertMsg[3]);
        } catch (Exception e) {
            System.out.println("Element Not present Exception");
        }
        return alertMsg[3];
    }

    //Method Name : Refresh
    //Description : This method refreshes the web page
    public static void Refresh(){
        driver.navigate().refresh();
    }

    //Method Name : CloseBrowser
    //Description : This method closes all the browsers
    public static void CloseBrowser(){
        driver.quit();
    }

    //Method Name : Sleep Wait
    //Description : This method is for Thread sleep
    public static void Wait1() throws InterruptedException {
        Thread.sleep(1000);
    }

    //Method Name : Sleep Wait
    //Description : This method is for Thread sleep
    public static void Wait2() throws InterruptedException {
        Thread.sleep(2000);
    }

    /* Application : Common Function
     * Method Name : highLightElement
     * Description : This method is used to highlight an element on screen
     * Parameters  : objName
     * Author      : Aastha Uppal
     * Date        : 10/18/2022
     */
    public static void highLightElement(String objName){

        WebElement lWebElement;
        lWebElement = GetWebElement(objName);
        javaScriptDriver = (JavascriptExecutor) driver;

        javaScriptDriver.executeScript("arguments[0].setAttribute('style','border: solid 2px red')", lWebElement);

        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {

            System.out.println(e.getMessage());
        }
        javaScriptDriver.executeScript("arguments[0].setAttribute('style','border: solid 2px white')", lWebElement);

    }

    /* Application : Common Function
     * Method Name : loginApplication
     * Description : This method is used to login to the application
     * Parameters  : fileName, scrNo
     * Author      : Aastha Uppal
     * Date        : 10/18/2022
     */
    public static void loginApplication(String fileName,String scrNo)
    {
        try{
            JSONObject data = FileUtil.getDataFromJsonFile(fileName,scrNo);
            applicationURL=(String) data.get("applicationURL");
            userID= (String) data.get("userID");
            password= (String) data.get("password");
            String applicationName = (String) data.get("applicationName");
            Allure.step("Navigating to the application URL : "+applicationURL);
            driver.get(applicationURL);
            driver.manage().window().maximize();
            System.out.println(applicationURL);
            Allure.step("Entering userid and password : "+userID);
            //enter user id and password
            ExplicitWait = new WebDriverWait(driver, Duration.ofSeconds(100));
            ExplicitWait.until(ExpectedConditions.presenceOfElementLocated(GetElementForWait("LoginID")));
            driver.findElement(By.xpath("//input[@id='userid']")).sendKeys(userID);
            driver.findElement(By.xpath("//button[@id='next-btn']")).click();
            System.out.println("Entered User ID");
            Allure.step("Entered User ID", Status.PASSED);
            ExplicitWait.until(ExpectedConditions.presenceOfElementLocated(GetElementForWait("LoginPassword")));
            driver.findElement(By.xpath("//input[@id='password']")).sendKeys(userID);
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            System.out.println("Entered Password");
            Allure.step("Entered Password", Status.PASSED);
            String pageTitle = driver.findElement(By.xpath("//input[@id='PageTitle']")).getText();
            if(pageTitle.equals(applicationName)){
                System.out.println("Logging in to application");
                Allure.step("Logging in to application successful", Status.PASSED);
            }else{
                System.out.println("Logging in to application");
                Allure.step("Logging in to application unsuccessful", Status.FAILED);
                Assert.fail();
            }
        }catch(Exception e){
            e.printStackTrace();
            Allure.step("Test Execution Failed", Status.FAILED);
            Assert.fail();
        }
    }

    /* Application : Common Function
     * Method Name : GetWebElements
     * Description : This method is used to find array of elements with obj repository locator
     * Parameters  : ObjectRepositoryValue
     * Author      : Aastha Uppal
     * Date        : 10/19/2022
     */
    public static WebElement GetWebElements(String ObjectRepositoryValue ){
        try{
            String[] Property= GetValue(ObjectRepositoryValue).split(":=");
            PropertyType = Property[0];
            PropertyValue = Property[1];
            if (PropertyType.equalsIgnoreCase("id")) {
                webElements = driver.findElements(By.id(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("name")) {
                webElements = driver.findElements(By.name(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("xpath")) {
                webElements = driver.findElements(By.xpath(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("linktext")) {
                webElements = driver.findElements(By.linkText(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("cssselector")) {
                webElements = driver.findElements(By.cssSelector(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("partiallinktext")) {
                webElements = driver.findElements(By.partialLinkText(PropertyValue));
            } else if (PropertyType.equalsIgnoreCase("classname")) {
                webElements = driver.findElements(By.className(PropertyValue));
            } else {
                System.out.println(" Invalid property type ");
            }
        }
        catch(Exception e){
            System.out.println("Unable to find the web element in object repository , "+ObjectRepositoryValue);
        }
        return (WebElement) webElements;
    }

    /* Application : Common Function
     * Method Name : setTextToClipBoard
     * Description : This method is used to set the values in the clipboard which can be pasted as per the requirement
     * Parameters  : aString
     * Author      : Aastha Uppal
     * Date        : 10/20/2022
     */
    public static void setTextToClipBoard(String aString){
        StringSelection stringSelection = new StringSelection(aString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    /* Application : Common Function
     * Method Name : setTextToClipBoard
     * Description : This method is used to set the values in the clipboard which can be pasted as per the requirement
     * Parameters  : aString
     * Author      : Aastha Uppal
     * Date        : 10/20/2022
     */
    public static void logOutApplication(){
        WebClick("logoutDD");
        WebClick("logOutBtn");
        driver.close();
    }

    public static SearchContext getShadowRoot(String querySelectorHost, boolean isOpen){
        JavascriptExecutor js=(JavascriptExecutor)driver;
        if(isOpen){
            return(SearchContext) (js.executeScript("return document.querySelector(\""+querySelectorHost+"\").shadowRoot"));
        }

        return (WebElement) webElements;
    }


    public static void validateElementExistence(String object_locator, String fieldName){

        String field_Text=fieldName;
        try{
            if(GetWebElement(object_locator).isDisplayed())
            {
                System.out.println(field_Text + " is displayed on the UI ");
                Allure.step(field_Text +"is displayed on the UI ", Status.PASSED);
            }else {
                System.out.println(field_Text + " is not displayed on the UI ");
                Allure.step(field_Text +"is displayed on the UI ", Status.FAILED);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
