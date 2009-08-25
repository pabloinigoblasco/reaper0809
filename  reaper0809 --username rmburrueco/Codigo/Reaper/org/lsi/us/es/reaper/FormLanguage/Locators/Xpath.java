package org.lsi.us.es.reaper.FormLanguage.Locators;

import org.lsi.us.es.reaper.FormLanguage.Locator;

public class Xpath extends Locator{


	@Override
	public String getExpression() {
		return "xpath="+this.getValue();
	}

	@Override
	public Locator toXpathLocator() {
		return this;
	}

}
