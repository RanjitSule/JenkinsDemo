package qa.utils;


import org.json.simple.JSONObject;
import org.testng.Assert;
import qa.common.pages.CommonPage;

import java.sql.SQLOutput;

import static qa.utils.DriverUtils.driver;

public class DemoWebShopUtils {

    public static void   lunchBrowser(String FileName, String Scenario) throws InterruptedException {
        JSONObject Data = FileUtil.getDataFromJsonFile(FileName, Scenario);
        driver=null;
        DriverUtils.getDriver();
        String Applicationurl = (String) Data.get("Applicationurl");
        CommonPage.Launch(Applicationurl);
        Thread.sleep(2000);
    }

    public static void register(String FileName, String Scenario) {
        try {
            JSONObject Data = FileUtil.getDataFromJsonFile(FileName, Scenario);
            CommonPage.WebClick("button_register");

            Thread.sleep(4000);
            CommonPage.WebClick("checkbox_male_gender");

            String firstname = (String) Data.get("First name");
            CommonPage.WebSend("input_field_firstname", firstname);

            String lastname = (String) Data.get("Last name");
            CommonPage.WebSend("input_field_lastname", lastname);

            String Email = (String) Data.get("Email");
            CommonPage.WebSend("input_field_Email", Email);

            String Password = (String) Data.get("Password");
            CommonPage.WebSend("input_field_Password", Password);

            CommonPage.WebSend("input_field_ConfirmPassword", Password);
            CommonPage.WebClick("buttonRegister");
            CommonPage.WebClick("button_continue");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    public static void navigateToDesktop() {
        try {
            CommonPage.MouseHover("button_computer");
            CommonPage.WebClick("button_desktop");

            CommonPage.selectDropDownWithSelect("sortby_dropDown","Price: Low to High");
            CommonPage.selectDropDownWithSelect("Display_dropDown","12");
            CommonPage.selectDropDownWithSelect("Viewmode_dropDown","Grid");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    public static void validate_Added_Message() {
        try {
            String ExpectedMessage = "The product has been added to your shopping cart";
            String ActialMessage=CommonPage.WebGetText("add_card_message");
            Assert.assertEquals(ExpectedMessage,ActialMessage);
            CommonPage.CloseBrowser();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    public static void addDesktop() {
        try {
            CommonPage.WebClick("selectDesktop");
            CommonPage.WebClick("click_addTocard");
        }
        catch(Exception e)
        {
            org.testng.Assert.fail();
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
    }

    public static void navigatetonotebook() {
        CommonPage.MouseHover("button_computer");
        CommonPage.WebClick("button_homepage_Notebook");
    }
    public static void AddNotebook(){
        CommonPage.selectDropDownWithSelect("sortby_dropDown","Price: Low to High");
        CommonPage.selectDropDownWithSelect("Display_dropDown","12");
        CommonPage.selectDropDownWithSelect("Viewmode_dropDown","Grid");
        CommonPage.WebClick("button_notebbokspage_addcart");
    }

    public static void navigatetoAccessories() {
            CommonPage.MouseHover("button_computer");
            CommonPage.WebClick("button_homepage_Accessories");

    }
    public static void AddAccessories(){
     CommonPage.WebClick("button_AddAccessories");
     CommonPage.WebClick("button_AddToCartAccessories");
    }
    public static void validateMessageForAccessories() {
        String ExpectedMessage = "This product requires the following product is added to the cart: TCP Instructor Led Training";
        String ActialMessage=CommonPage.WebGetText("add_card_message");
        System.out.println("messageText : : "+ActialMessage);
        Assert.assertEquals(ExpectedMessage,ActialMessage);
        CommonPage.CloseBrowser();
    }


}