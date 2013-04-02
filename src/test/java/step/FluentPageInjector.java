package step;

import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.annotation.Page;
import page.HomePage;
import page.ResultPage;

public class FluentPageInjector extends FluentAdapter {
    @Page
    public HomePage homePage;

    @Page
    public ResultPage resultPage;
}
