package step;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;


/**
 * @author : Mathilde Lemee
 */
public class HomePageStep extends FluentPageInjector {

//    private FluentLeniumStep delegator;

//	@Page
//	HomePage homePage;
//
//	@Page
//	ResultPage resultPage;

    public HomePageStep(FluentLeniumStepInitilizer delegator) {
//        this.delegator = delegator;
        this.homePage = delegator.homePage;
        this.resultPage = delegator.resultPage;
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

//	@When("^je suis sur la page result$")
//	public void je_suis_sur_la_page_result() throws Throwable {
//		resultPage.isAt();
//	}

}
