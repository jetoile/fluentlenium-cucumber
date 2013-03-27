import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import page.HomePage;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.fest.assertions.api.Assertions.assertThat;


/**
 * @author : Mathilde Lemee
 */
public class BasicStep extends FluentAdapter {

  @Page
  HomePage page;

  @Before
  public void before() {
    this.initFluent(new HtmlUnitDriver());
    this.initTest();
  }

  @Given(value = "j accede a bing")
  public void step1() {
    goTo(page);
  }

  @When(value = "je recherche toto")
  public void step2() {
    fill("#sb_form_q").with("FluentLenium");
    submit("#sb_form_go");
  }

  @Then(value = "le titre est toto")
  public void step3() {
    assertThat(title()).contains("FluentLenium");
  }
}
