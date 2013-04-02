package step;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import mapper.Browser;
import mapper.BrowserMapper;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.WebDriver;
import page.HomePage;
import page.ResultPage;

import java.util.List;


/**
 * @author : Mathilde Lemee
 */
public class HomePageStep extends FluentAdapter {

    @Page
    HomePage homePage;

    @Page
    ResultPage resultPage;

    @Before
    public void before() {
//        this.initFluent(new FirefoxDriver()).withDefaultUrl("http://localhost:8080");
//        this.initTest();
    }


    @Given("^I connect on url ([^ ]*) with different browsers:$")
    public void browser_connect(String host, DataTable dataTable) {

        String browserHost = "none";
        String browserName = "default";

        Fluent fluent = null;

        List<List<String>> raw = dataTable.raw();
        List<String> browserLine = raw.get(0);

        if (browserLine != null && !browserLine.isEmpty()) {
            browserName = browserLine.get(0);
//            browserHost = browserLine.get(1);
        }

        Browser browser = Browser.getBrowser(browserName);
        if (browser != null) {
            WebDriver driver = BrowserMapper.getDriver(browser);
            fluent = this.initFluent(driver);
        }

        fluent.withDefaultUrl(host);
        this.initTest();
    }

    @Given("^j accede a la homePage$")
    public void j_accede_a_homePage() {
        goTo(homePage);
    }

    @Given("^je suis sur homePage$")
    public void je_suis_sur_homePage() {
        homePage.isAt();
    }

    @When("^je submit$")
    public void je_submit() throws Throwable {
        homePage.submit();
    }

    @When("^je suis sur la page result$")
    public void je_suis_sur_la_page_result() throws Throwable {
        resultPage.isAt();
    }

    @After
    public void after() {
        this.quit();
    }
}
