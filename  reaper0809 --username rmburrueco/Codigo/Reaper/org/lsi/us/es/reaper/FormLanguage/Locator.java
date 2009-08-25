package org.lsi.us.es.reaper.FormLanguage;

public abstract class Locator {
	String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public abstract Locator toXpathLocator();
	
	public abstract String getExpression();

}
