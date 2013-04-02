package step;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import page.BingPage;
import page.HomePage;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: khanh
 * Date: 02/04/13
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class SimpleStep extends FluentAdapter {

	@Page
	BingPage page;

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

	@After
	public void after(){
		this.quit();
	}

}
