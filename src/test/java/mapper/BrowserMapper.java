package mapper;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.util.HashMap;
import java.util.Map;

public class BrowserMapper {
	private String name;

	private static Map<Browser, WebDriverLazyLoader> browserMapper = new HashMap<Browser, WebDriverLazyLoader>();
	private static Map<Browser, WebDriver> webDriverInstances = new HashMap<Browser, WebDriver>();


	static {
		browserMapper.put(Browser.CHROME, new WebDriverLazyLoader(ChromeDriver.class));
		browserMapper.put(Browser.FIREFOX, new WebDriverLazyLoader(FirefoxDriver.class));
		browserMapper.put(Browser.HTMLUNIT, new WebDriverLazyLoader(HtmlUnitDriver.class));
		browserMapper.put(Browser.PHANTOMJS, new WebDriverLazyLoader(PhantomJSDriver.class));
	}

	public static WebDriver getCachedDriver(Browser browser, Capabilities capabilities) {
		if (!webDriverInstances.containsKey(browser)) {
			webDriverInstances.put(browser, getDriver(browser, capabilities));
		}
		return webDriverInstances.get(browser);
	}

	public static WebDriver getDriver(Browser browser, Capabilities capabilities) {
		WebDriverLazyLoader webDriverLazyLoader = browserMapper.get(browser);
		if (webDriverLazyLoader != null) {
            if (browser == Browser.PHANTOMJS) {
                return webDriverLazyLoader.getWebDriverClass(capabilities);

            } else if (browser == Browser.CHROME) {
                System.setProperty("webdriver.chrome.driver", (String) capabilities.getCapability("webdriver.chrome.driver"));
                return webDriverLazyLoader.getWebDriverClass(capabilities);

            } else {
                return webDriverLazyLoader.getWebDriverClass();
            }
		}
		return browserMapper.get(Browser.HTMLUNIT).getWebDriverClass();
	}


}
