package step;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.annotation.Page;
import page.ResultPage;

public class ResultPageStep extends FluentAdapter {

    @Page
    ResultPage resultPage;


//    @When("^je suis sur la page result$")
    public void je_suis_sur_la_page_result() throws Throwable {
        resultPage.isAt();
    }

}
