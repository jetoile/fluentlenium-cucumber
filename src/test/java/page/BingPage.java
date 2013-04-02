package page;

import org.fluentlenium.core.FluentPage;

/**
 * Created with IntelliJ IDEA.
 * User: khanh
 * Date: 02/04/13
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class BingPage extends FluentPage {

	@Override
	public String getUrl() {
		return "http://www.bing.com";
	}
}
