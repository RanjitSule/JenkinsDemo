package qa.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import qa.common.pages.CommonPage;

import java.time.Duration;

import static qa.common.pages.CommonPage.*;
import static qa.utils.DriverUtils.driver;
import static qa.utils.FileUtil.captureScreenShot;

public class MyWebShopUtil {
    public static void WebClick(String SpecificElement){
        try {
            WaitForPresenceOfElementLocated(SpecificElement);
            GetWebElement(SpecificElement).click();
        }
        catch(Exception e){
            System.out.println("Element Click Intercepted Exception");
        }
    }

    public static void sortingAndFiltering() {
        try {
            CommonPage.selectDropDownWithSelect("sortby_dropDown","Price: Low to High");
            CommonPage.selectDropDownWithSelect("Display_dropDown","12");
            CommonPage.selectDropDownWithSelect("Viewmode_dropDown","Grid");
        } catch (Exception e) {
            Assert.fail();
        }
    }


    //Method Name :WaitForPresenceOfElementLocated
    //Description :This method is used to explicitly wait for presence of element located
    public static boolean WaitForAbsenceOfElementLocated(String element){
        try {
            ExplicitWait = new WebDriverWait(driver, Duration.ofSeconds(100));
            ExplicitWait.until(ExpectedConditions.invisibilityOfElementLocated(GetElementForWait(element)));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static String getDropDownText(String element)
    {
        WaitForElementTovisibile(element);
        Select selectdropdown= new Select(GetWebElement(element));
        return selectdropdown.getFirstSelectedOption().getText();
    }

    public static boolean isElementDisplayed(String el){
        try{
            System.out.println(GetWebElement(el).isDisplayed());
            return true;
        }
        catch(Exception e){
            return false;
        }
    }


    public static void login(String fileName, String scNo) {
        JSONObject data=FileUtil.getDataFromJsonFile(fileName,scNo);
        driver=null;
        DriverUtils.getDriver();
        String Application= (String) data.get("Applicationurl");
        CommonPage.Launch(Application);
        CommonPage.WebClick("button_home_page_login");
        String Email= (String) data.get("Email");
        CommonPage.WebSend("input_login_Email",Email);
        String Password=(String) data.get("Password");
        CommonPage.WebSend("input_login_password",Password);
        CommonPage.WebClick("button_loginpage_login");
        CommonPage.WaitForElementTovisibile("account");
        String ActualValue = CommonPage.WebGetText("account");
        if(Email.equalsIgnoreCase(ActualValue)){
            captureScreenShot("Successfully logged into the page", "Pass");
            System.out.println("Successfully logged into the page");
            Allure.step("Successfully logged into the page", Status.PASSED);
        }
        else{
            captureScreenShot("Login failed", "Fail");
            System.out.println("Login failed");
            Allure.step("Login failed", Status.FAILED);
            Assert.fail();
        }
    }



    public static void clickedOnTopMenu() throws InterruptedException {
        String ExpectedValue = CommonPage.WebGetText("books_on_top_menu");
        CommonPage.WebClick("books_on_top_menu");
        Thread.sleep(5000);
        String ActualValue = CommonPage.WebGetText("top_menu_books");
        if(ExpectedValue.equalsIgnoreCase(ActualValue)){
            captureScreenShot("Successfully Navigated to Books section", "Pass");
            System.out.println("Successfully Navigated to Books section");
            Allure.step("Successfully Navigated to Books section", Status.PASSED);
        }
        else{
            captureScreenShot("Navigation Failed", "Fail");
            System.out.println("Navigation Failed");
            Allure.step("Navigation Failed", Status.FAILED);
            Assert.fail();
        }
    }

    public static void addedItemsToCart() throws InterruptedException {
        sortingAndFiltering();
        Thread.sleep(2000);
        CommonPage.WebClick("computing_book");
        Thread.sleep(1000);
        CommonPage.WebClick("health_book");
        String ExpectedValue = "The product has been added to your shopping cart";
        String ActualValue = CommonPage.WebGetText("item_added_message");
        if(ExpectedValue.equalsIgnoreCase(ActualValue)){
            captureScreenShot("Successfully Added to cart", "Pass");
            System.out.println("Successfully Added to cart");
            Allure.step("Successfully Added to cart", Status.PASSED);
        }
        else{
            captureScreenShot("Failed to added to cart", "Fail");
            System.out.println("Failed to added to cart");
            Allure.step("Failed to added to cart", Status.FAILED);
            Assert.fail();
        }
//        MyWebShopUtil.WaitForAbsenceOfElementLocated("bar_notification");
//        CommonPage.WaitForElementTovisibile("shopping_cart");
//        CommonPage.Scroll("shopping_cart");
//        //((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
    }

    public static void estimatingTheShipping(){
        CommonPage.WebClick("message_bar_close");
        MyWebShopUtil.WaitForAbsenceOfElementLocated("message_bar_close");
        CommonPage.Scroll("shopping_cart");
        CommonPage.MouseHover("shoppingcart_btn");
        CommonPage.WaitForElementTovisibile("add_to_cart_btn");
        CommonPage.WebClick("add_to_cart_btn");
        CommonPage.WaitForPresenceOfElementLocated("select_country");
        CommonPage.selectDropDownWithSelect("select_country", "India");
        CommonPage.WebSend("ZipPostalCode", Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        CommonPage.WebSend("ZipPostalCode", "507843");
        CommonPage.WebClick("estimateshipping");
        CommonPage.WaitForElementTovisibile("shipping_results");
        CommonPage.WebClick("termsofservice");
        String ExpectedValue = "Compared to other shipping methods, like by flight or over seas, ground shipping is carried out closer to the earth";
        CommonPage.WaitForElementTovisibile("estimating_shipping_msg");
        String ActualValue = CommonPage.WebGetText("estimating_shipping_msg");
        if(ExpectedValue.equalsIgnoreCase(ActualValue)){
            captureScreenShot("Successfully estimated the shipping cost", "Pass");
            System.out.println("Successfully estimated the shipping cost");
            Allure.step("Successfully estimated the shipping cost", Status.PASSED);
        }
        else{
            captureScreenShot("Failed to estimate the shipping cost", "Fail");
            System.out.println("Failed to estimate the shipping cost");
            Allure.step("Failed to estimate the shipping cost", Status.FAILED);
            Assert.fail();
        }
    }


    public static void gettingIntoTheCheckOutPage() throws InterruptedException {
        CommonPage.WebClick("checkout");
        Thread.sleep(5000);
        String ExpectedValue = "Checkout";
        String ActualValue = CommonPage.WebGetText("checkout_page_msg");
        if(ExpectedValue.equalsIgnoreCase(ActualValue)){
            captureScreenShot("Successfully got into the checkout page", "Pass");
            System.out.println("Successfully got into the checkout page");
            Allure.step("Successfully got into the checkout page", Status.PASSED);
        }
        else{
            captureScreenShot("Failed to get into the checkout page", "Fail");
            System.out.println("Failed to get into the checkout page");
            Allure.step("Failed to get into the checkout page", Status.FAILED);
            Assert.fail();
        }

        if (MyWebShopUtil.isElementDisplayed("billing_address_select")) {
            handleExistingAddress();
        } else {
            enterNewAddressDetails();
        }

    }

    private static void handleExistingAddress() throws InterruptedException {
        if (!"New Address".equals(MyWebShopUtil.getDropDownText("billing_address_select"))) {
            proceedThroughCheckout();
        }
    }

    private static void enterNewAddressDetails() throws InterruptedException {
        CommonPage.WaitForElementTovisibile("NewAddress_Company");
        CommonPage.selectDropDownWithSelect("NewAddress_Company", "India");
        CommonPage.WebSend("BillingNewAddress_City", "Whitefield");
        CommonPage.WebSend("BillingNewAddress_Address1", "Capgemini A5 Block");
        CommonPage.WebSend("BillingNewAddress_ZipPostalCode", "560066");
        CommonPage.WebSend("BillingNewAddress_PhoneNumber", "9398293808");
        proceedThroughCheckout();
    }

    private static void proceedThroughCheckout() throws InterruptedException {
        CommonPage.WebClick("continue1");
        CommonPage.WaitForElementTovisibile("PickUpInStore");
        CommonPage.WebClick("continue2");
        CommonPage.WaitForElementTovisibile("shipping_method");
        CommonPage.WebClick("shipping_method");
        CommonPage.WebClick("continue3");
        CommonPage.WaitForElementTovisibile("paymentmethod_0");
        CommonPage.WebClick("paymentmethod_0");
        CommonPage.WebClick("continue4");
        CommonPage.WaitForElementTovisibile("payment_info");
        Assert.assertEquals(CommonPage.WebGetText("payment_info"), "You will pay by COD");
        CommonPage.WebClick("continue5");
        CommonPage.WaitForElementTovisibile("continue6");
        CommonPage.WebClick("continue6");
    }


    public static void successfullyPlacedAnOrder(){
        //Thread.sleep(5000);
        //CommonPage.WaitForElementTovisibile("successful_order_msg");
        CommonPage.WaitForPresenceOfElementLocated("successful_order_msg");
        String ExpectedMessage = "Your order has been successfully processed!";
        String ActualMessage = CommonPage.WebGetText("successful_order_msg");
        if(ExpectedMessage.equalsIgnoreCase(ActualMessage)){
            captureScreenShot("Successfully placed an order", "Pass");
            System.out.println("Successfully placed an order");
            Allure.step("Successfully placed an order", Status.PASSED);
        }
        else{
            captureScreenShot("Failed to placed an order", "Fail");
            System.out.println("Failed to placed an order");
            Allure.step("Failed to placed an order", Status.FAILED);
            Assert.fail();
        }
    }

    public static void NavigatedToMyAccount(){
        CommonPage.WaitForElementTovisibile("");
        CommonPage.WebClick("account");
    }
}
