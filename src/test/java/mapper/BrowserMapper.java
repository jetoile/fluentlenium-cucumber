/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package mapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * User: khanh
 * Date: 02/04/13
 * Time: 07:57
 */
public class BrowserMapper {
	private String name;

	private static Map<Browser, WebDriverLazyLoader> browserMapper = new HashMap<Browser, WebDriverLazyLoader>();
	private static Map<Browser, WebDriver> webDriverInstances = new HashMap<Browser, WebDriver>();


	static {
		browserMapper.put(Browser.CHROME, new WebDriverLazyLoader(ChromeDriver.class));
		browserMapper.put(Browser.FIREFOX, new WebDriverLazyLoader(FirefoxDriver.class));
		browserMapper.put(Browser.HTMLUNIT, new WebDriverLazyLoader(HtmlUnitDriver.class));
	}

	public static WebDriver getCachedDriver(Browser browser) {
		if (!webDriverInstances.containsKey(browser)) {
			webDriverInstances.put(browser, getDriver(browser));
		}
		return webDriverInstances.get(browser);
	}

	public static WebDriver getDriver(Browser browser) {
		WebDriverLazyLoader webDriverLazyLoader = browserMapper.get(browser);
		if (webDriverLazyLoader != null) {
			return webDriverLazyLoader.getWebDriverClass();
		}
		return browserMapper.get(Browser.HTMLUNIT).getWebDriverClass();
	}


}
