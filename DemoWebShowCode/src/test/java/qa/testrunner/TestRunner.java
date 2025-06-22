package qa.testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features=("src/test/resources/features/MyWebShop.feature"),glue={"qa.steps"},
        tags="@tag")
public class TestRunner {

}
