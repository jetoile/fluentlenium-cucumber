package page;

import org.fluentlenium.core.FluentPage;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author : Mathilde Lemee
 */
public class HomePage extends FluentPage {
    @Override
    public String getUrl() {
        return "/home";
    }

    @Override
    public void isAt() {
        assertThat(title()).containsIgnoringCase("home");
    }

    public void submit() {
       this.submit("select").await().untilPage();
   }

}
