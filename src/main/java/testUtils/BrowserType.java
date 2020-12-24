package testUtils;

public enum BrowserType {

	getFireFox("Firefox"), getChrome("Chrome"), getEdge("Edge"), getIE("IE");

	private String browser;

	BrowserType(String browser) {
		this.browser = browser;
	}

	public String getBrowser() {
		return browser;
	}
}