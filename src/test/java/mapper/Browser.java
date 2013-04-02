package mapper;

public enum Browser {
    HTMLUNIT("default"),
    FIREFOX("firefox"),
    CHROME("chrome"),
    PHANTOMJS("phantomjs");

    private String name;

    Browser(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Browser getBrowser(String name) {
        for (Browser browser : values()) {
            if (browser.getName().equalsIgnoreCase(name)) {
                return browser;
            }
        }
        return HTMLUNIT;
    }
}
