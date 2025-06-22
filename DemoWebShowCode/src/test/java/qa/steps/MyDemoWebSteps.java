package qa.steps;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.cookie.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import qa.common.pages.CommonPage;
import qa.utils.DemoWebShopUtils;
import qa.utils.DriverUtils;
import qa.utils.FileUtil;
import qa.utils.MyWebShopUtil;

import java.io.IOException;
import java.util.List;

import static qa.utils.DriverUtils.driver;
import static qa.utils.FileUtil.captureScreenShot;

public class MyDemoWebSteps {


    public static String scenarioName = null;
    public static String scenarioStatus = null;
    public static Scenario scenario;

    @Before(order = 2)
    public void initDriver(){
        System.out.println("Open browser");
        //driver = DriverUtils.getDriver();
        //System.out.println("Page title is: " + DriverUtils.driver.getTitle());
    }


    @Before(order = 1)
    public void before(Scenario scenario){
        MyDemoWebSteps.scenario = scenario;
        scenarioName = scenario.getName();
        System.out.println("Scenario - " + scenario.getName());
    }

    @After(order = 1)
    public void createResultsDocument() throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        scenarioStatus = String.valueOf(scenario.getStatus());
        FileUtil.updateDoc(scenarioStatus);
    }

    @Given("User can login {string} {string}")
    public void userWillRegisterAndLogin(String fileName, String scenario) throws InterruptedException {
        MyWebShopUtil.login(fileName, scenario);
    }

    @When("User clicked on books in top menu")
    public void userClickedOnBooksInTopMenu() throws InterruptedException {
        MyWebShopUtil.clickedOnTopMenu();
    }

    @And("Added items to the cart")
    public void addedItemsToTheCart() throws InterruptedException {
        MyWebShopUtil.addedItemsToCart();
    }


    @And("User can estimate the shipping cost")
    public void userCanEstimateTheShippingCost() throws InterruptedException {
        MyWebShopUtil.estimatingTheShipping();
    }

    @And("User can get into the checkout page")
    public void userCanGetIntoTheCheckoutPage() throws InterruptedException {
        MyWebShopUtil.gettingIntoTheCheckOutPage();
    }


    @Then("User can successfully placed the order and logged out")
    public void userCanSuccessfullyPlacedTheOrder() throws InterruptedException {
        MyWebShopUtil.successfullyPlacedAnOrder();
    }

    @When("User navigated to My Account")
    public void userNavigatedToMyAccount() {
        MyWebShopUtil.NavigatedToMyAccount();
    }
}

