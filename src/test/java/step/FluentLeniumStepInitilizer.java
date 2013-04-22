package step;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import harstorage.HarStorage;
import mapper.Browser;
import mapper.BrowserMapper;
import org.browsermob.core.har.Har;
import org.browsermob.proxy.ProxyServer;
import org.fluentlenium.core.Fluent;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

public class FluentLeniumStepInitilizer extends FluentPageInjector {

	// BrowserMob Proxy API
	private static final String PROXY_API_HOST = "localhost";
	private static final String PROXY_API_PORT = "9090";

	// Temporary proxy for browser you create via BrowserMob Proxy.
	// PROXY_HOST must be equal to PROXY_API_HOST
	private static final String PROXY_HOST = PROXY_API_HOST;
	private static final String PROXY_PORT = "9091";

	private static boolean isCached = false;

	// HAR Storage
	private static String HARSTORAGE_HOST = "localhost";
	private static int HARSTORAGE_PORT = 5000;

	private static ProxyServer server;

    @Given("^I connect on url ([^ ]*) with different cached browsers:$")
    public void browser_connect_with_cached_webDriver(String host, DataTable dataTable) {

		isCached = true;

        List<List<String>> raw = dataTable.raw();
        List<String> browserLine = raw.get(0);

		DesiredCapabilities capabilities = new DesiredCapabilities();

		init(host, browserLine, capabilities, true);
    }

    @Given("^I connect on url ([^ ]*) with different browsers:$")
    public void browser_connect(String host, DataTable dataTable) {

        List<List<String>> raw = dataTable.raw();
        List<String> browserLine = raw.get(0);

		DesiredCapabilities capabilities = new DesiredCapabilities();

		init(host, browserLine, capabilities, false);
    }

	@Given("^I connect on url ([^ ]*) with different browsers and I register the HarStorage Server on ([^ ]*):(\\d+) with name ([^ ]*):$")
	public void registerHarStorageServer(String host, String hostHarStorage, int portHarStorage, String name, DataTable dataTable) throws Exception {

		HARSTORAGE_HOST = hostHarStorage;
		HARSTORAGE_PORT = portHarStorage;

		server = new ProxyServer(Integer.valueOf(PROXY_API_PORT));
		server.start();


		// Change browser settings
		Proxy proxy = server.seleniumProxy();
		String ip = getIp();
		proxy.setHttpProxy(ip + ":" + PROXY_API_PORT);
		proxy.setHttpProxy(ip + ":" + PROXY_API_PORT);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.PROXY, proxy);

		List<List<String>> raw = dataTable.raw();
		List<String> browserLine = raw.get(0);

		init(host, browserLine, capabilities, false);

		server.newHar(name);
	}

	@Given("^I send har files to the HarStorage Server")
	public void sendHarFileAndCloseHarProxy() throws Exception {
		// Read data from container
		Har har = server.getHar();
		String strFilePath = "target/selenium_report.har";
		File file = new File(strFilePath);
		if (file.exists()) {
			file.delete();
		}
		FileOutputStream fos = new FileOutputStream(file);
		har.writeTo(fos);
		server.stop();

		InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file) );
		LineNumberReader lineNumberReader = new LineNumberReader(inputStreamReader);
		String ligne;
		String res = "";
		while ((ligne = lineNumberReader.readLine()) != null)
		{
			res += ligne;
		}

		// Send results to HAR Storage
		try {
			HarStorage hs = new HarStorage(HARSTORAGE_HOST, Integer.toString(HARSTORAGE_PORT));

			String response = hs.save(res);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Then("^drivers are closed")
    public void close() throws Exception {
		isCached = false;
        afterClose();
    }

	@After
	public void afterClose() throws Exception {
		if (!isCached) {
			this.quit();
		}
		if (server != null) {
			server.stop();
		}
	}

	private String getIp() throws SocketException {
		String ip = "";
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface iface = interfaces.nextElement();
			// filters out 127.0.0.1 and inactive interfaces
			if (iface.isLoopback() || !iface.isUp())
				continue;

			Enumeration<InetAddress> addresses = iface.getInetAddresses();
			while(addresses.hasMoreElements()) {
				InetAddress addr = addresses.nextElement();
				ip = addr.getHostAddress();
//				System.out.println(iface.getDisplayName() + " " + ip);
			}
		}
		return ip;
	}


    private void init(String host, List<String> browserLine, DesiredCapabilities capabilities, boolean cached) {
        String browserHost = "none";

        Browser browser = null;

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
