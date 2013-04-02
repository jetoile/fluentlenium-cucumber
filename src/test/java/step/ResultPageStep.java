package step;

import cucumber.api.java.en.When;

public class ResultPageStep extends FluentPageInjector {

//    @Page
//    ResultPage resultPage;

    public ResultPageStep(FluentLeniumStepInitilizer delegator) {
        this.resultPage = delegator.resultPage;
    }

    @When("^je suis sur la page result$")
    public void je_suis_sur_la_page_result() throws Throwable {
        resultPage.isAt();
    }

}
