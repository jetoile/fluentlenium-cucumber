package page;

import org.fluentlenium.core.FluentPage;

import static org.fest.assertions.api.Assertions.assertThat;


public class ResultPage extends FluentPage {
    @Override
    public String getUrl() {
        return "/result";
    }

    @Override
    public void isAt() {
        assertThat(title()).containsIgnoringCase("result");
    }
}
