package step;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.firefox.FirefoxDriver;
import page.HomePage;
import page.ResultPage;


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
        this.initFluent(new FirefoxDriver()).withDefaultUrl("http://localhost:8080");
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
