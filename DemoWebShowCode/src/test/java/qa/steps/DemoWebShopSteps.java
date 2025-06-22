package qa.steps;


import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import qa.common.pages.CommonPage;
import qa.utils.DemoWebShopUtils;

public class DemoWebShopSteps {
    @Given("User can register and login {string} {string}")
    public void userCanRegisterAndLogin(String fileName, String scenario) throws InterruptedException {
       // DemoWebShopUtils.lunchBrowser(fileName,scenario);
      //  DemoWebShopUtils.register(fileName, scenario);
        DemoWebShopUtils.login(fileName,scenario);
    }

    @When("User navigates to Computers and Desktop")
    public void userNavigatesToComputersAndDesktop() {
        DemoWebShopUtils.navigateToDesktop();
    }

    @And("Add Desktop to card")
    public void addDesktopToCard() {
        DemoWebShopUtils.addDesktop();
    }

    @Then("Validate added message")
    public void validateAddedMessage() {
      DemoWebShopUtils.validate_Added_Message();
        }

    @Given("user is login and go on product page {string} {string}")
    public void userIsLoginAndGoOnProductPage(String FileName, String sc_no) throws InterruptedException {
        DemoWebShopUtils.login(FileName,sc_no);
    }

    @When("user navigate to Computer and notebook")
    public void serNavigateToComputerAndNotebook() {
        DemoWebShopUtils.navigatetonotebook();
    }

    @And("Add Notebook to card")
    public void addNotebookToCard() {
        DemoWebShopUtils.AddNotebook();
    }

    @Then("Validate added message Notebooks")
    public void validateAddedMessageNotebooks() {
        DemoWebShopUtils.validate_Added_Message();
    }
    @When("user navigate to Computer and Accessories")
    public void userNavigateToComputerAndAccessories() {
        DemoWebShopUtils.navigatetoAccessories();
    }

    @And("Add Accessories to card")
    public void addAccessoriesToCard() {
     DemoWebShopUtils.AddAccessories();
    }

    @Then("Validate added message for Accessories")
    public void validateAddedMessageForAccessories() {
    DemoWebShopUtils.validateMessageForAccessories();
    }


}
