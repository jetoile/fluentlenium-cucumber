package mapper;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

class WebDriverLazyLoader {
    private Class webDriverClass;


    public WebDriverLazyLoader(Class webDriverClass) {
        this.webDriverClass = webDriverClass;
    }

    public WebDriver getWebDriverClass() {
        try {
			return (WebDriver)this.webDriverClass.newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WebDriver getWebDriverClass(Capabilities capabilities) {
        try {
            return (WebDriver)this.webDriverClass.getConstructor(Capabilities.class).newInstance(capabilities);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
