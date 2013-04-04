package step;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import mapper.Browser;
import mapper.BrowserMapper;
import org.fluentlenium.core.Fluent;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class FluentLeniumStepInitilizer extends FluentPageInjector {

    @Given("^I connect on url ([^ ]*) with different cached browsers:$")
    public void browser_connect_with_cached_webDriver(String host, DataTable dataTable) {

        List<List<String>> raw = dataTable.raw();
        List<String> browserLine = raw.get(0);

        init(host, browserLine, true);
    }

    @Given("^I connect on url ([^ ]*) with different browsers:$")
    public void browser_connect(String host, DataTable dataTable) {

        List<List<String>> raw = dataTable.raw();
        List<String> browserLine = raw.get(0);

        init(host, browserLine, false);
    }

    @Then("^drivers are closed")
    public void close() {
        this.quit();
    }

	@After
	public void afterClose() {
		this.quit();
	}

    private void init(String host, List<String> browserLine, boolean cached) {
        String browserHost = "none";

        Browser browser = null;

        DesiredCapabilities capabilities = new DesiredCapabilities();


        if (browserLine != null && !browserLine.isEmpty()) {
            switch (browserLine.size()) {
                case 5:
                    capabilities.setPlatform(Platform.extractFromSysProperty(browserLine.get(4), ""));
                case 4:
                    capabilities.setVersion(browserLine.get(3));
                case 3:
                    browserHost = browserLine.get(2);
                case 2:
                    String[] parameters = browserLine.get(1).split(";");
                    for (String parameter : parameters) {
                        if (!parameter.isEmpty()) {
                            String[] key_value = parameter.split(":");
                            capabilities.setCapability(key_value[0], key_value[1]);
                        }
                    }
                case 1:
                    browser = Browser.getBrowser(browserLine.get(0));
					capabilities.setBrowserName(browser.getName());
            }
        }

        if (cached) {
            initCachedWebDriver(host, browserHost, browser, capabilities);
        } else {
            initWebDriver(host, browserHost, browser, capabilities);
        }
    }

    private WebDriver createRemoteWebDriver(String browserHost, DesiredCapabilities capabilities) {
        try {
            return new RemoteWebDriver(new URL(browserHost), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initCachedWebDriver(String host, String browserHost, Browser browser, DesiredCapabilities capabilities) {
        Fluent fluent = null;
        WebDriver driver = null;
        if (browser != null) {
            if (!"none".equalsIgnoreCase(browserHost) && !browserHost.isEmpty()) {
                driver = createRemoteWebDriver(browserHost, capabilities);
            } else {
                driver = BrowserMapper.getCachedDriver(browser, capabilities);
            }
            fluent = this.initFluent(driver);
        }

        fluent.withDefaultUrl(host);
        this.initTest();
    }

    private void initWebDriver(String host, String browserHost, Browser browser, DesiredCapabilities capabilities) {
        Fluent fluent = null;
        WebDriver driver = null;
        if (browser != null) {
            if (!"none".equalsIgnoreCase(browserHost) && !browserHost.isEmpty()) {
                driver = createRemoteWebDriver(browserHost, capabilities);
            } else {
                driver = BrowserMapper.getDriver(browser, capabilities);
            }
            fluent = this.initFluent(driver);
        }

        fluent.withDefaultUrl(host);
        this.initTest();
    }
}
