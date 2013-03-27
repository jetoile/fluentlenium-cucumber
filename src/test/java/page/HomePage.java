package page;

import org.fluentlenium.core.FluentPage;

/**
 * @author : Mathilde Lemee
 */
public class HomePage extends FluentPage {
  @Override
  public String getUrl() {
    return "http://www.bing.com";
  }
}
