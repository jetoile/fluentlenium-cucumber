package step;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import mapper.Browser;
import mapper.BrowserMapper;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import page.HomePage;
import page.ResultPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * @author : Mathilde Lemee
 */
public class HomePageStep extends FluentAdapter {

	@Page
	HomePage homePage;

	@Page
	ResultPage resultPage;

	@Given("^I connect on url ([^ ]*) with different cached browsers:$")
	public void browser_connect_with_cached_webDriver(String host, DataTable dataTable) {

		String browserHost = "none";
		String browserName = "default";
		String browserOs = "";
		String browserVersion = "";

		DesiredCapabilities capabilities = new DesiredCapabilities();

		Fluent fluent = null;

		List<List<String>> raw = dataTable.raw();
		List<String> browserLine = raw.get(0);

		if (browserLine != null && !browserLine.isEmpty()) {
			switch (browserLine.size()) {
				case 5:
					browserOs = browserLine.get(4);
					capabilities.setPlatform(Platform.extractFromSysProperty(browserOs, ""));
				case 4:
					browserVersion = browserLine.get(3);
					capabilities.setVersion(browserVersion);
				case 3:
					browserHost = browserLine.get(2);
				case 2:
					String[] parameters = browserLine.get(1).split(";");
					if (parameters.length > 2) {
						for (String parameter : parameters) {
							String[] key_value = parameter.split(":");
							capabilities.setCapability(key_value[0], key_value[1]);
						}
					}
				case 1:
					browserName = browserLine.get(0);

			}
		}

		WebDriver driver = null;
		Browser browser = Browser.getBrowser(browserName);
		if (browser != null) {
			if (!"none".equalsIgnoreCase(browserHost) && !browserHost.isEmpty()) {
				try {
					driver = new RemoteWebDriver(new URL(browserHost + ":4444/wd/hub"), capabilities);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			} else {
				driver = BrowserMapper.getCachedDriver(browser);
			}
			fluent = this.initFluent(driver);
		}

		fluent.withDefaultUrl(host);
		this.initTest();
	}

	@Given("^I connect on url ([^ ]*) with different browsers:$")
	public void browser_connect(String host, DataTable dataTable) {

		String browserHost = "none";
		String browserName = "default";
		String browserOs = "";
		String browserVersion = "";

		DesiredCapabilities capabilities = new DesiredCapabilities();

		Fluent fluent = null;

		List<List<String>> raw = dataTable.raw();
		List<String> browserLine = raw.get(0);

		if (browserLine != null && !browserLine.isEmpty()) {
			switch (browserLine.size()) {
				case 5:
					browserOs = browserLine.get(4);
					capabilities.setPlatform(Platform.extractFromSysProperty(browserOs, ""));
				case 4:
					browserVersion = browserLine.get(3);
					capabilities.setVersion(browserVersion);
				case 3:
					browserHost = browserLine.get(2);
				case 2:
					String[] parameters = browserLine.get(1).split(";");
					if (parameters.length > 2) {
						for (String parameter : parameters) {
							String[] key_value = parameter.split(":");
							capabilities.setCapability(key_value[0], key_value[1]);
						}
					}
				case 1:
					browserName = browserLine.get(0);

			}
		}

		WebDriver driver = null;
		Browser browser = Browser.getBrowser(browserName);
		if (browser != null) {
			if (!"none".equalsIgnoreCase(browserHost) && !browserHost.isEmpty()) {
				try {
					driver = new RemoteWebDriver(new URL(browserHost + ":4444/wd/hub"), capabilities);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			} else {
				driver = BrowserMapper.getDriver(browser);
			}
			fluent = this.initFluent(driver);
		}

		fluent.withDefaultUrl(host);
		this.initTest();
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

	@When("^je suis sur la page result$")
	public void je_suis_sur_la_page_result() throws Throwable {
		resultPage.isAt();
	}

}
